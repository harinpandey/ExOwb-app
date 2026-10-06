package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.ListingType
import com.example.data.repository.ExOwnRepository
import com.example.ui.components.CampusSwitcherModal
import com.example.ui.components.ExOwnBottomBar
import com.example.ui.components.ExOwnTopBar
import com.example.ui.components.SellCreationActionSheet
import com.example.ui.components.SellFloatingActionButton
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CreateListingScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HousingDetailScreen
import com.example.ui.screens.InboxScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.DarkBg
import com.example.ui.theme.ExOwnTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ExOwnViewModel
import com.example.ui.viewmodel.ServicesTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ExOwnRepository.initPersistence(this)

        setContent {
            val viewModel: ExOwnViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            ExOwnTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExOwnApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun ExOwnApp(
    viewModel: ExOwnViewModel = viewModel()
) {
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedCampus by viewModel.selectedCampus.collectAsState()
    val campuses = viewModel.campuses
    val categories = viewModel.getCategories()
    val currentUser by viewModel.currentUser.collectAsState()

    if (!isAuthenticated) {
        AuthScreen(
            campuses = campuses,
            onCompleteAuth = { name, email, university, campus, hostel ->
                viewModel.signInStudent(name, email, university, campus.name, hostel)
            }
        )
        return
    }

    val listings by viewModel.allListings.collectAsState()
    val filteredListings by viewModel.filteredListings.collectAsState()
    val savedListings by viewModel.savedListings.collectAsState()
    val myListings by viewModel.myListings.collectAsState()
    val conversations by viewModel.conversations.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val filterOnlyUrgent by viewModel.filterOnlyUrgent.collectAsState()
    val filterOnlyExchange by viewModel.filterOnlyExchange.collectAsState()
    val sortBy by viewModel.sortBy.collectAsState()

    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val selectedHousing by viewModel.selectedHousing.collectAsState()
    val servicesTab by viewModel.servicesTab.collectAsState()
    val activeConversation by viewModel.activeConversation.collectAsState()
    val activeMessages by viewModel.activeMessages.collectAsState()

    val creationSuccess by viewModel.listingCreationSuccess.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showSellActionSheet by remember { mutableStateOf(false) }
    var showCampusSwitcherModal by remember { mutableStateOf(false) }

    LaunchedEffect(creationSuccess) {
        creationSuccess?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearCreationSuccess()
        }
    }

    // Hardware Back Button Handling
    val isRootScreen = currentScreen in listOf(
        AppScreen.HOME,
        AppScreen.EXPLORE,
        AppScreen.INBOX,
        AppScreen.PROFILE
    )

    BackHandler(enabled = !isRootScreen) {
        viewModel.navigateBack()
    }

    val unreadCount = remember(conversations) {
        conversations.sumOf { it.unreadCount }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (isRootScreen) {
                ExOwnTopBar(
                    selectedCampus = selectedCampus,
                    campuses = campuses,
                    onCampusSelected = { viewModel.setCampus(it) },
                    onSearchClick = { viewModel.navigateTo(AppScreen.EXPLORE) },
                    onChatClick = { viewModel.navigateTo(AppScreen.INBOX) },
                    unreadCount = unreadCount
                )
            }
        },
        bottomBar = {
            if (isRootScreen) {
                ExOwnBottomBar(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) },
                    unreadCount = unreadCount
                )
            }
        },
        floatingActionButton = {
            if (isRootScreen) {
                SellFloatingActionButton(
                    onClick = { showSellActionSheet = true },
                    modifier = Modifier.offset(y = 12.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        campus = selectedCampus,
                        campuses = campuses,
                        onSelectCampus = { viewModel.setCampus(it) },
                        categories = categories,
                        selectedCategoryId = selectedCategoryId,
                        onSelectCategory = {
                            viewModel.setSelectedCategory(it)
                            viewModel.navigateTo(AppScreen.EXPLORE)
                        },
                        listings = filteredListings,
                        onProductClick = { viewModel.viewProductDetails(it) },
                        onSaveToggle = { viewModel.toggleSave(it) },
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                AppScreen.EXPLORE -> {
                    ExploreScreen(
                        campus = selectedCampus,
                        categories = categories,
                        selectedCategoryId = selectedCategoryId,
                        onSelectCategory = { viewModel.setSelectedCategory(it) },
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        filterOnlyUrgent = filterOnlyUrgent,
                        onToggleUrgent = { viewModel.toggleFilterOnlyUrgent() },
                        filterOnlyExchange = filterOnlyExchange,
                        onToggleExchange = { viewModel.toggleFilterOnlyExchange() },
                        sortBy = sortBy,
                        onSortChange = { viewModel.setSortBy(it) },
                        listings = filteredListings,
                        onProductClick = { viewModel.viewProductDetails(it) },
                        onSaveToggle = { viewModel.toggleSave(it) }
                    )
                }

                AppScreen.INBOX -> {
                    InboxScreen(
                        conversations = conversations,
                        onConversationClick = { viewModel.openConversation(it) }
                    )
                }

                AppScreen.PROFILE -> {
                    ProfileScreen(
                        user = currentUser,
                        myListings = myListings,
                        savedListings = savedListings,
                        onProductClick = { viewModel.viewProductDetails(it) },
                        onSaveToggle = { viewModel.toggleSave(it) },
                        onMarkSold = { viewModel.markListingSold(it) },
                        onDeleteListing = { viewModel.deleteListing(it) },
                        onSignOut = { viewModel.signOutStudent() },
                        currentCampus = selectedCampus,
                        campuses = campuses,
                        onSwitchCampus = { viewModel.setCampus(it) },
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { viewModel.setDarkTheme(it) }
                    )
                }

                AppScreen.SERVICES, AppScreen.CAMPUS_HUB -> {
                    ServicesScreen(
                        campus = selectedCampus,
                        selectedTab = servicesTab,
                        onTabSelect = { viewModel.setServicesTab(it) },
                        services = viewModel.getCampusServices(),
                        housing = viewModel.getHousingListings(),
                        roommates = viewModel.getRoommates(),
                        onHousingClick = { viewModel.viewHousingDetails(it) }
                    )
                }

                AppScreen.SELL -> {
                    CreateListingScreen(
                        categories = categories,
                        title = viewModel.formTitle.value,
                        onTitleChange = { viewModel.formTitle.value = it },
                        category = viewModel.formCategory.value,
                        onCategoryChange = { viewModel.formCategory.value = it },
                        type = viewModel.formType.value,
                        onTypeChange = { viewModel.formType.value = it },
                        price = viewModel.formPrice.value,
                        onPriceChange = { viewModel.formPrice.value = it },
                        originalPrice = viewModel.formOriginalPrice.value,
                        onOriginalPriceChange = { viewModel.formOriginalPrice.value = it },
                        condition = viewModel.formCondition.value,
                        onConditionChange = { viewModel.formCondition.value = it },
                        location = viewModel.formLocation.value,
                        onLocationChange = { viewModel.formLocation.value = it },
                        description = viewModel.formDescription.value,
                        onDescriptionChange = { viewModel.formDescription.value = it },
                        isUrgent = viewModel.formIsUrgent.value,
                        onUrgentChange = { viewModel.formIsUrgent.value = it },
                        isExchange = viewModel.formIsExchange.value,
                        onExchangeChange = { viewModel.formIsExchange.value = it },
                        exchangePref = viewModel.formExchangePref.value,
                        onExchangePrefChange = { viewModel.formExchangePref.value = it },
                        rentalUnit = viewModel.formRentalUnit.value,
                        onRentalUnitChange = { viewModel.formRentalUnit.value = it },
                        onSubmit = { viewModel.submitNewListing() },
                        onBack = { viewModel.navigateBack() },
                        selectedCampus = selectedCampus,
                        onCampusChange = { viewModel.setCampus(it) },
                        campuses = campuses
                    )
                }

                AppScreen.PRODUCT_DETAIL -> {
                    selectedProduct?.let { product ->
                        ProductDetailScreen(
                            product = product,
                            onBack = { viewModel.navigateBack() },
                            onChatClick = { viewModel.startChatForProduct(product) },
                            onSaveToggle = { viewModel.toggleSave(product.id) }
                        )
                    } ?: run {
                        viewModel.navigateTo(AppScreen.HOME)
                    }
                }

                AppScreen.HOUSING_DETAIL -> {
                    selectedHousing?.let { housing ->
                        HousingDetailScreen(
                            housing = housing,
                            onBack = { viewModel.navigateBack() }
                        )
                    } ?: run {
                        viewModel.navigateTo(AppScreen.SERVICES)
                    }
                }

                AppScreen.CHAT -> {
                    ChatScreen(
                        conversation = activeConversation,
                        messages = activeMessages,
                        onSendMessage = { text, isOffer, amount ->
                            viewModel.sendMessage(text, isOffer, amount)
                        },
                        onBack = { viewModel.navigateBack() }
                    )
                }
            }
        }
    }

    // Sell Creation Action Sheet (Triggered by the Sell FAB)
    SellCreationActionSheet(
        show = showSellActionSheet,
        onDismiss = { showSellActionSheet = false },
        onSelectOption = { listingType, screen, subTab ->
            if (listingType != null) {
                viewModel.formType.value = listingType
            }
            if (subTab != null) {
                viewModel.setServicesTab(subTab)
            }
            viewModel.navigateTo(screen)
        }
    )

    // Campus Switcher Modal
    CampusSwitcherModal(
        show = showCampusSwitcherModal,
        currentCampus = selectedCampus,
        campuses = campuses,
        onSelect = {
            viewModel.setCampus(it)
            showCampusSwitcherModal = false
        },
        onDismiss = { showCampusSwitcherModal = false }
    )
}
