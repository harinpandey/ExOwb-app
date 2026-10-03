package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.auth.AuthManager
import com.example.ui.auth.AuthScreen
import com.example.ui.components.ExOwnBottomBar
import com.example.ui.components.ExOwnTopBar
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CreateListingScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HousingDetailScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ExOwnViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ExOwnApp()
            }
        }
    }
}

@Composable
fun ExOwnApp(
    viewModel: ExOwnViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }

    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentCampus by viewModel.selectedCampus.collectAsState()
    val allListings by viewModel.allListings.collectAsState()
    val filteredListings by viewModel.filteredListings.collectAsState()
    val savedIds by viewModel.savedListingIds.collectAsState()
    val savedListings by viewModel.savedListings.collectAsState()
    val myListings by viewModel.myListings.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val activeConversation by viewModel.activeConversation.collectAsState()
    val activeMessages by viewModel.activeMessages.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val selectedHousing by viewModel.selectedHousing.collectAsState()
    val servicesTab by viewModel.servicesTab.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val creationSuccess by viewModel.listingCreationSuccess.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Listen to Firebase Auth state in accordance with Firebase lifecycle guidelines
    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                viewModel.signInStudent(
                    name = firebaseUser.displayName ?: "Student User",
                    email = firebaseUser.email ?: "student@campus.edu",
                    university = "Apex Institute of Technology",
                    campus = currentCampus,
                    hostel = "BH-4, Block A"
                )
            }
        }
        val authInstance = try { Firebase.auth } catch (e: Exception) { null }
        authInstance?.addAuthStateListener(listener)
        onDispose {
            authInstance?.removeAuthStateListener(listener)
        }
    }

    LaunchedEffect(creationSuccess) {
        creationSuccess?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearCreationSuccess()
        }
    }

    // Android System Back Navigation Handler
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        val handled = viewModel.navigateBack()
        if (!handled) {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    // Auth Gating: If user session is not authenticated, show AuthScreen
    if (!isAuthenticated) {
        AuthScreen(
            campuses = viewModel.campuses,
            selectedCampus = currentCampus,
            onCampusSelect = { viewModel.setCampus(it) },
            onAuthSuccess = { name, email, uni, campus, hostel ->
                viewModel.signInStudent(name, email, uni, campus, hostel)
            }
        )
        return
    }

    val showMainBars = when (currentScreen) {
        AppScreen.HOME, AppScreen.EXPLORE, AppScreen.SELL, AppScreen.SERVICES, AppScreen.PROFILE, AppScreen.SAVED -> true
        else -> false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (showMainBars) {
                ExOwnTopBar(
                    currentCampus = currentCampus,
                    campuses = viewModel.campuses,
                    onCampusSelect = { viewModel.setCampus(it) },
                    unreadChatCount = conversations.sumOf { it.unreadCount },
                    onSearchClick = { viewModel.navigateTo(AppScreen.EXPLORE) },
                    onChatClick = {
                        if (conversations.isNotEmpty()) {
                            viewModel.openConversation(conversations[0])
                        } else {
                            viewModel.navigateTo(AppScreen.PROFILE)
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showMainBars) {
                ExOwnBottomBar(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            AppScreen.HOME -> {
                HomeScreen(
                    listings = allListings,
                    savedIds = savedIds,
                    categories = viewModel.getCategories(),
                    currentCampus = currentCampus,
                    onCategoryClick = { catId ->
                        viewModel.setSelectedCategory(catId)
                        viewModel.navigateTo(AppScreen.EXPLORE)
                    },
                    onProductClick = { viewModel.viewProductDetails(it) },
                    onSaveToggle = { viewModel.toggleSave(it) },
                    onNavigate = { viewModel.navigateTo(it) },
                    onNavigateServicesTab = { viewModel.setServicesTab(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScreen.EXPLORE -> {
                val searchQuery by viewModel.searchQuery.collectAsState()
                val selectedCatId by viewModel.selectedCategoryId.collectAsState()
                val selectedType by viewModel.selectedListingType.collectAsState()
                val selectedCondition by viewModel.selectedCondition.collectAsState()
                val filterUrgent by viewModel.filterOnlyUrgent.collectAsState()
                val filterExchange by viewModel.filterOnlyExchange.collectAsState()
                val sortBy by viewModel.sortBy.collectAsState()

                ExploreScreen(
                    listings = filteredListings,
                    savedIds = savedIds,
                    categories = viewModel.getCategories(),
                    searchQuery = searchQuery,
                    selectedCategoryId = selectedCatId,
                    selectedType = selectedType,
                    selectedCondition = selectedCondition,
                    filterOnlyUrgent = filterUrgent,
                    filterOnlyExchange = filterExchange,
                    sortBy = sortBy,
                    onSearchChange = { viewModel.setSearchQuery(it) },
                    onCategorySelect = { viewModel.setSelectedCategory(it) },
                    onTypeSelect = { viewModel.setSelectedListingType(it) },
                    onConditionSelect = { viewModel.setSelectedCondition(it) },
                    onToggleUrgent = { viewModel.toggleFilterOnlyUrgent() },
                    onToggleExchange = { viewModel.toggleFilterOnlyExchange() },
                    onSortChange = { viewModel.setSortBy(it) },
                    onResetFilters = { viewModel.resetFilters() },
                    onProductClick = { viewModel.viewProductDetails(it) },
                    onSaveToggle = { viewModel.toggleSave(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScreen.SELL -> {
                val title by viewModel.formTitle.collectAsState()
                val category by viewModel.formCategory.collectAsState()
                val type by viewModel.formType.collectAsState()
                val price by viewModel.formPrice.collectAsState()
                val originalPrice by viewModel.formOriginalPrice.collectAsState()
                val condition by viewModel.formCondition.collectAsState()
                val location by viewModel.formLocation.collectAsState()
                val description by viewModel.formDescription.collectAsState()
                val isUrgent by viewModel.formIsUrgent.collectAsState()
                val isExchange by viewModel.formIsExchange.collectAsState()
                val exchangePref by viewModel.formExchangePref.collectAsState()
                val rentalUnit by viewModel.formRentalUnit.collectAsState()

                CreateListingScreen(
                    categories = viewModel.getCategories(),
                    title = title,
                    onTitleChange = { viewModel.formTitle.value = it },
                    category = category,
                    onCategoryChange = { viewModel.formCategory.value = it },
                    type = type,
                    onTypeChange = { viewModel.formType.value = it },
                    price = price,
                    onPriceChange = { viewModel.formPrice.value = it },
                    originalPrice = originalPrice,
                    onOriginalPriceChange = { viewModel.formOriginalPrice.value = it },
                    condition = condition,
                    onConditionChange = { viewModel.formCondition.value = it },
                    location = location,
                    onLocationChange = { viewModel.formLocation.value = it },
                    description = description,
                    onDescriptionChange = { viewModel.formDescription.value = it },
                    isUrgent = isUrgent,
                    onUrgentToggle = { viewModel.formIsUrgent.value = !isUrgent },
                    isExchange = isExchange,
                    onExchangeToggle = { viewModel.formIsExchange.value = !isExchange },
                    exchangePref = exchangePref,
                    onExchangePrefChange = { viewModel.formExchangePref.value = it },
                    rentalUnit = rentalUnit,
                    onRentalUnitChange = { viewModel.formRentalUnit.value = it },
                    onSubmit = { viewModel.submitNewListing() },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScreen.SERVICES -> {
                ServicesScreen(
                    currentTab = servicesTab,
                    onTabSelect = { viewModel.setServicesTab(it) },
                    housingListings = viewModel.getHousingListings(),
                    roommates = viewModel.getRoommates(),
                    campusServices = viewModel.getCampusServices(),
                    onHousingClick = { viewModel.viewHousingDetails(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScreen.PROFILE, AppScreen.SAVED -> {
                ProfileScreen(
                    user = user,
                    myListings = myListings,
                    savedListings = savedListings,
                    onProductClick = { viewModel.viewProductDetails(it) },
                    onSaveToggle = { viewModel.toggleSave(it) },
                    onMarkSold = { viewModel.markListingSold(it) },
                    onDeleteListing = { viewModel.deleteListing(it) },
                    onSignOut = {
                        AuthManager.signOut(
                            context = context,
                            credentialManager = credentialManager,
                            onSignOutComplete = { viewModel.signOutStudent() },
                            scope = coroutineScope
                        )
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppScreen.PRODUCT_DETAIL -> {
                selectedProduct?.let { product ->
                    ProductDetailScreen(
                        product = product,
                        isSaved = savedIds.contains(product.id),
                        onBackClick = { viewModel.navigateBack() },
                        onSaveToggle = { viewModel.toggleSave(product.id) },
                        onStartChat = { viewModel.startChatForProduct(product) }
                    )
                } ?: run {
                    viewModel.navigateTo(AppScreen.HOME)
                }
            }

            AppScreen.CHAT -> {
                activeConversation?.let { conversation ->
                    ChatScreen(
                        conversation = conversation,
                        messages = activeMessages,
                        onBackClick = { viewModel.navigateBack() },
                        onSendMessage = { text, isOffer, amount ->
                            viewModel.sendMessage(text, isOffer, amount)
                        }
                    )
                } ?: run {
                    viewModel.navigateTo(AppScreen.HOME)
                }
            }

            AppScreen.HOUSING_DETAIL -> {
                selectedHousing?.let { housing ->
                    HousingDetailScreen(
                        housing = housing,
                        onBackClick = { viewModel.navigateBack() },
                        onContactClick = {
                            viewModel.navigateBack()
                        }
                    )
                } ?: run {
                    viewModel.navigateTo(AppScreen.SERVICES)
                }
            }
        }
    }
}
