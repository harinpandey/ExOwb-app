package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.Campus
import com.example.data.model.CampusServiceItem
import com.example.data.model.ChatMessage
import com.example.data.model.Conversation
import com.example.data.model.HousingListing
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductCondition
import com.example.data.model.ProductListing
import com.example.data.model.RoommateListing
import com.example.data.model.StudentUser
import com.example.data.repository.ExOwnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.util.UUID

enum class AppScreen {
    HOME,
    EXPLORE,
    SELL,
    SERVICES,
    INBOX,
    PROFILE,
    CAMPUS_HUB,
    PRODUCT_DETAIL,
    HOUSING_DETAIL,
    CHAT
}

enum class ServicesTab {
    SERVICES,
    HOUSING,
    ROOMMATES
}

class ExOwnViewModel(
    private val repository: ExOwnRepository = ExOwnRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf(AppScreen.HOME)

    fun navigateTo(screen: AppScreen) {
        if (screen == _currentScreen.value) return
        screenStack.add(screen)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = screenStack.last()
            return true
        }
        return false
    }

    // Authentication State
    private val _isAuthenticated = MutableStateFlow(true)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    fun signInStudent(
        name: String,
        email: String,
        university: String,
        campus: String,
        hostel: String
    ) {
        val campusObj = repository.campuses.find { it.name.contains(university, ignoreCase = true) }
            ?: repository.lpuCampus

        val user = StudentUser(
            id = "user-${UUID.randomUUID()}",
            name = name.ifBlank { "Campus Student" },
            email = email,
            university = university,
            campus = campus,
            campusId = campusObj.id,
            hostel = hostel.ifBlank { "Hostel Block A" },
            isVerified = true
        )
        repository.updateUserSession(user)
        repository.setCampus(campusObj)
        _isAuthenticated.value = true
    }

    fun signOutStudent() {
        _isAuthenticated.value = false
    }

    // Campus Management
    val campuses: List<Campus> = repository.campuses
    val selectedCampus: StateFlow<Campus> = repository.selectedCampus

    fun setCampus(campus: Campus) {
        repository.setCampus(campus)
    }

    fun selectCampus(campus: Campus) {
        repository.setCampus(campus)
    }

    fun setCampus(campusIdentifier: String) {
        repository.setCampusById(campusIdentifier)
    }

    fun setCampusById(campusId: String) {
        repository.setCampusById(campusId)
    }

    fun selectCampusById(campusId: String) {
        repository.setCampusById(campusId)
    }

    fun getCampusScopedListings(campusId: String): List<ProductListing> {
        return repository.getCampusScopedListings(campusId)
    }

    private val _campusScopeOnly = MutableStateFlow(true)
    val campusScopeOnly: StateFlow<Boolean> = _campusScopeOnly.asStateFlow()

    fun toggleCampusScope() {
        _campusScopeOnly.value = !_campusScopeOnly.value
    }

    fun setCampusScopeOnly(scopeOnly: Boolean) {
        _campusScopeOnly.value = scopeOnly
    }

    private val _showCampusSwitcher = MutableStateFlow(false)
    val showCampusSwitcher: StateFlow<Boolean> = _showCampusSwitcher.asStateFlow()

    fun openCampusSwitcher() {
        _showCampusSwitcher.value = true
    }

    fun closeCampusSwitcher() {
        _showCampusSwitcher.value = false
    }

    private val _showCampusOnboarding = MutableStateFlow(false)
    val showCampusOnboarding: StateFlow<Boolean> = _showCampusOnboarding.asStateFlow()

    fun openCampusOnboarding() {
        _showCampusOnboarding.value = true
    }

    fun closeCampusOnboarding() {
        _showCampusOnboarding.value = false
    }

    // Repository Data Access
    val currentUser: StateFlow<StudentUser> = repository.currentUser
    val allListings: StateFlow<List<ProductListing>> = repository.listings
    val savedListingIds: StateFlow<Set<String>> = repository.savedListingIds
    val conversations: StateFlow<List<Conversation>> = repository.conversations

    fun getCategories(): List<ListingCategory> = repository.categories
    fun getHousingListings(): List<HousingListing> = repository.getHousingListings()
    fun getRoommates(): List<RoommateListing> = repository.getRoommates()
    fun getCampusServices(): List<CampusServiceItem> = repository.getCampusServices()

    // Filters and Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow("all")
    val selectedCategoryId: StateFlow<String> = _selectedCategoryId.asStateFlow()

    private val _selectedListingType = MutableStateFlow<ListingType?>(null)
    val selectedListingType: StateFlow<ListingType?> = _selectedListingType.asStateFlow()

    private val _selectedCondition = MutableStateFlow<ProductCondition?>(null)
    val selectedCondition: StateFlow<ProductCondition?> = _selectedCondition.asStateFlow()

    private val _filterOnlyUrgent = MutableStateFlow(false)
    val filterOnlyUrgent: StateFlow<Boolean> = _filterOnlyUrgent.asStateFlow()

    private val _filterOnlyExchange = MutableStateFlow(false)
    val filterOnlyExchange: StateFlow<Boolean> = _filterOnlyExchange.asStateFlow()

    private val _sortBy = MutableStateFlow("newest") // newest, price_low, price_high
    val sortBy: StateFlow<String> = _sortBy.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(categoryId: String) {
        _selectedCategoryId.value = categoryId
    }

    fun setSelectedListingType(type: ListingType?) {
        _selectedListingType.value = type
    }

    fun setSelectedCondition(condition: ProductCondition?) {
        _selectedCondition.value = condition
    }

    fun toggleFilterOnlyUrgent() {
        _filterOnlyUrgent.value = !_filterOnlyUrgent.value
    }

    fun toggleFilterOnlyExchange() {
        _filterOnlyExchange.value = !_filterOnlyExchange.value
    }

    fun setSortBy(sort: String) {
        _sortBy.value = sort
    }

    fun resetFilters() {
        _searchQuery.value = ""
        _selectedCategoryId.value = "all"
        _selectedListingType.value = null
        _selectedCondition.value = null
        _filterOnlyUrgent.value = false
        _filterOnlyExchange.value = false
        _sortBy.value = "newest"
    }

    // Filtered Listings State Flow
    val filteredListings: StateFlow<List<ProductListing>> = combine(
        allListings,
        selectedCampus,
        campusScopeOnly,
        searchQuery,
        selectedCategoryId
    ) { listings, campus, scopeOnly, query, categoryId ->
        var list = listings

        // Campus filter
        if (scopeOnly) {
            list = list.filter { it.campusId.equals(campus.id, ignoreCase = true) }
        }

        // Category filter
        if (categoryId != "all") {
            list = list.filter { it.categoryId.equals(categoryId, ignoreCase = true) }
        }

        // Search query
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                it.categoryName.lowercase().contains(q) ||
                it.location.lowercase().contains(q)
            }
        }

        // Additional filters
        if (_selectedListingType.value != null) {
            list = list.filter { it.listingType == _selectedListingType.value }
        }
        if (_selectedCondition.value != null) {
            list = list.filter { it.condition == _selectedCondition.value }
        }
        if (_filterOnlyUrgent.value) {
            list = list.filter { it.isUrgent }
        }
        if (_filterOnlyExchange.value) {
            list = list.filter { it.isExchangeEligible }
        }

        // Sorting
        when (_sortBy.value) {
            "price_low" -> list.sortedBy { it.price }
            "price_high" -> list.sortedByDescending { it.price }
            else -> list
        }
    }.combine(savedListingIds) { list, saved ->
        list.map { it.copy(isSaved = saved.contains(it.id)) }
    }.let { flow ->
        val state = MutableStateFlow(flow)
        // Convert to state flow pattern
        val initial = allListings.value.filter { it.campusId == selectedCampus.value.id }
        MutableStateFlow(initial)
    }

    val savedListings: StateFlow<List<ProductListing>> = combine(allListings, savedListingIds) { listings, savedIds ->
        listings.filter { savedIds.contains(it.id) }.map { it.copy(isSaved = true) }
    }.let {
        MutableStateFlow(allListings.value.filter { savedListingIds.value.contains(it.id) })
    }

    val myListings: StateFlow<List<ProductListing>> = allListings.let {
        MutableStateFlow(it.value.filter { listing -> listing.sellerId == currentUser.value.id })
    }

    fun toggleSave(id: String) {
        repository.toggleSave(id)
    }

    // Product Detail
    private val _selectedProduct = MutableStateFlow<ProductListing?>(null)
    val selectedProduct: StateFlow<ProductListing?> = _selectedProduct.asStateFlow()

    fun viewProductDetails(product: ProductListing) {
        _selectedProduct.value = product
        navigateTo(AppScreen.PRODUCT_DETAIL)
    }

    // Housing Detail
    private val _selectedHousing = MutableStateFlow<HousingListing?>(null)
    val selectedHousing: StateFlow<HousingListing?> = _selectedHousing.asStateFlow()

    fun viewHousingDetails(housing: HousingListing) {
        _selectedHousing.value = housing
        navigateTo(AppScreen.HOUSING_DETAIL)
    }

    // Services Tab
    private val _servicesTab = MutableStateFlow(ServicesTab.SERVICES)
    val servicesTab: StateFlow<ServicesTab> = _servicesTab.asStateFlow()

    fun setServicesTab(tab: ServicesTab) {
        _servicesTab.value = tab
    }

    // Chat and Messaging
    private val _activeConversation = MutableStateFlow<Conversation?>(null)
    val activeConversation: StateFlow<Conversation?> = _activeConversation.asStateFlow()

    private val _activeMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val activeMessages: StateFlow<List<ChatMessage>> = _activeMessages.asStateFlow()

    fun openConversation(conversation: Conversation) {
        _activeConversation.value = conversation
        repository.getMessagesForConversation(conversation.id).let {
            _activeMessages.value = it.value
        }
        navigateTo(AppScreen.CHAT)
    }

    fun startChatForProduct(product: ProductListing) {
        val conversation = repository.startOrGetConversation(product)
        openConversation(conversation)
    }

    fun sendMessage(text: String, isOffer: Boolean = false, offerAmount: Double? = null) {
        val conv = _activeConversation.value ?: return
        repository.sendMessage(conv.id, text, isOffer, offerAmount)
        _activeMessages.value = repository.getMessagesForConversation(conv.id).value
    }

    // Listing Creation Form State
    var formTitle = MutableStateFlow("")
    var formCategory = MutableStateFlow("bikes-transport")
    var formType = MutableStateFlow(ListingType.SELL)
    var formPrice = MutableStateFlow("")
    var formOriginalPrice = MutableStateFlow("")
    var formCondition = MutableStateFlow(ProductCondition.GOOD)
    var formLocation = MutableStateFlow("")
    var formDescription = MutableStateFlow("")
    var formIsUrgent = MutableStateFlow(false)
    var formIsExchange = MutableStateFlow(false)
    var formExchangePref = MutableStateFlow("")
    var formRentalUnit = MutableStateFlow("month")

    private val _listingCreationSuccess = MutableStateFlow<String?>(null)
    val listingCreationSuccess: StateFlow<String?> = _listingCreationSuccess.asStateFlow()

    fun clearCreationSuccess() {
        _listingCreationSuccess.value = null
    }

    fun submitNewListing(): Boolean {
        val titleText = formTitle.value.trim()
        val priceVal = formPrice.value.toDoubleOrNull() ?: 0.0
        if (titleText.isBlank() || priceVal <= 0.0) return false

        val categoryItem = repository.categories.find { it.id == formCategory.value }
            ?: repository.categories[1]
        val campus = selectedCampus.value
        val user = currentUser.value

        val newListing = ProductListing(
            id = "listing-${UUID.randomUUID()}",
            title = titleText,
            price = priceVal,
            originalPrice = formOriginalPrice.value.toDoubleOrNull(),
            listingType = formType.value,
            condition = formCondition.value,
            categoryId = categoryItem.id,
            categoryName = categoryItem.name,
            description = formDescription.value.ifBlank { "Available on campus for verified students." },
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=800&q=80",
            location = formLocation.value.ifBlank { "${campus.code} Student Center" },
            campusId = campus.id,
            campusCode = campus.code,
            campusCity = campus.city,
            isUrgent = formIsUrgent.value,
            isVerified = true,
            sellerId = user.id,
            sellerName = user.name,
            sellerAvatar = user.avatarUrl,
            sellerRating = 5.0,
            sellerDepartment = user.department,
            isExchangeEligible = formIsExchange.value,
            exchangePreferences = formExchangePref.value,
            rentalDurationUnit = formRentalUnit.value,
            isSaved = false
        )

        repository.addListing(newListing)
        _listingCreationSuccess.value = "Listing published successfully to ${campus.name} marketplace!"

        // Reset form
        formTitle.value = ""
        formPrice.value = ""
        formOriginalPrice.value = ""
        formLocation.value = ""
        formDescription.value = ""
        formIsUrgent.value = false
        formIsExchange.value = false
        formExchangePref.value = ""

        navigateTo(AppScreen.HOME)
        return true
    }

    fun markListingSold(id: String) {
        repository.markAsSold(id)
    }

    fun deleteListing(id: String) {
        repository.deleteListing(id)
    }
}
