package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.UUID

enum class AppScreen {
    HOME,
    EXPLORE,
    SELL,
    SERVICES,
    SAVED,
    PROFILE,
    PRODUCT_DETAIL,
    CHAT,
    HOUSING_DETAIL
}

enum class ServicesTab {
    HOUSING,
    ROOMMATES,
    SERVICES
}

class ExOwnViewModel(
    private val repository: ExOwnRepository = ExOwnRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<AppScreen>()

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        return if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            true
        } else {
            false
        }
    }

    // Session Authentication Management
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    fun signInStudent(
        name: String,
        email: String,
        university: String,
        campus: String,
        hostel: String
    ) {
        val updatedUser = StudentUser(
            id = "student-${System.currentTimeMillis()}",
            name = name,
            email = email,
            university = university,
            campus = campus,
            hostel = hostel,
            isVerified = true,
            itemsListed = 3,
            itemsRehomed = 7,
            moneySaved = 14250.0,
            co2SavedKg = 38
        )
        repository.updateUserSession(updatedUser)
        repository.setCampus(campus)
        _isAuthenticated.value = true
    }

    fun signOutStudent() {
        _isAuthenticated.value = false
        _currentScreen.value = AppScreen.HOME
        screenStack.clear()
    }

    // Repository bindings
    val campuses: List<String> = repository.campuses
    val selectedCampus: StateFlow<String> = repository.selectedCampus
    fun setCampus(campus: String) = repository.setCampus(campus)

    val currentUser: StateFlow<StudentUser> = repository.currentUser
    val allListings: StateFlow<List<ProductListing>> = repository.listings
    val savedListingIds: StateFlow<Set<String>> = repository.savedListingIds
    val conversations: StateFlow<List<Conversation>> = repository.conversations

    fun getCategories(): List<ListingCategory> = repository.getCategories()
    fun getHousingListings(): List<HousingListing> = repository.getHousingListings()
    fun getRoommates(): List<RoommateListing> = repository.getRoommates()
    fun getCampusServices(): List<CampusServiceItem> = repository.getCampusServices()

    // Explore / Filtering State
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

    private val _sortBy = MutableStateFlow("NEWEST") // "NEWEST", "PRICE_ASC", "PRICE_DESC"
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
        _filterOnlyUrgent.update { !it }
    }

    fun toggleFilterOnlyExchange() {
        _filterOnlyExchange.update { !it }
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
        _sortBy.value = "NEWEST"
    }

    // Filtered Listings
    val filteredListings: StateFlow<List<ProductListing>> = combine(
        repository.listings,
        _searchQuery,
        _selectedCategoryId,
        _selectedListingType,
        _selectedCondition,
        _filterOnlyUrgent,
        _filterOnlyExchange,
        _sortBy
    ) { params ->
        val listings = params[0] as List<ProductListing>
        val query = params[1] as String
        val catId = params[2] as String
        val listingType = params[3] as ListingType?
        val condition = params[4] as ProductCondition?
        val onlyUrgent = params[5] as Boolean
        val onlyExchange = params[6] as Boolean
        val sort = params[7] as String

        var result = listings.filter { !it.isSold }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            result = result.filter {
                it.title.lowercase().contains(q) ||
                        it.description.lowercase().contains(q) ||
                        it.location.lowercase().contains(q) ||
                        it.categoryName.lowercase().contains(q)
            }
        }

        if (catId != "all") {
            result = result.filter { it.categoryId == catId }
        }

        if (listingType != null) {
            result = result.filter { it.listingType == listingType }
        }

        if (condition != null) {
            result = result.filter { it.condition == condition }
        }

        if (onlyUrgent) {
            result = result.filter { it.isUrgent }
        }

        if (onlyExchange) {
            result = result.filter { it.isExchangeEligible || it.listingType == ListingType.EXCHANGE }
        }

        when (sort) {
            "PRICE_ASC" -> result.sortedBy { it.price }
            "PRICE_DESC" -> result.sortedByDescending { it.price }
            else -> result
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Saved Listings
    val savedListings: StateFlow<List<ProductListing>> = combine(
        repository.listings,
        repository.savedListingIds
    ) { listings, savedIds ->
        listings.filter { savedIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // My Listings
    val myListings: StateFlow<List<ProductListing>> = repository.listings.combine(
        repository.currentUser
    ) { listings, user ->
        listings.filter { it.sellerId == user.id || it.sellerId == "current-user-1" }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleSave(id: String) {
        repository.toggleSave(id)
    }

    // Detail Screen Selection
    private val _selectedProduct = MutableStateFlow<ProductListing?>(null)
    val selectedProduct: StateFlow<ProductListing?> = _selectedProduct.asStateFlow()

    fun viewProductDetails(product: ProductListing) {
        _selectedProduct.value = product
        navigateTo(AppScreen.PRODUCT_DETAIL)
    }

    // Housing Detail Selection
    private val _selectedHousing = MutableStateFlow<HousingListing?>(null)
    val selectedHousing: StateFlow<HousingListing?> = _selectedHousing.asStateFlow()

    fun viewHousingDetails(housing: HousingListing) {
        _selectedHousing.value = housing
        navigateTo(AppScreen.HOUSING_DETAIL)
    }

    // Services Tab Selection
    private val _servicesTab = MutableStateFlow(ServicesTab.HOUSING)
    val servicesTab: StateFlow<ServicesTab> = _servicesTab.asStateFlow()

    fun setServicesTab(tab: ServicesTab) {
        _servicesTab.value = tab
    }

    // Chat / Conversation Selection
    private val _activeConversation = MutableStateFlow<Conversation?>(null)
    val activeConversation: StateFlow<Conversation?> = _activeConversation.asStateFlow()

    private val _activeMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val activeMessages: StateFlow<List<ChatMessage>> = _activeMessages.asStateFlow()

    fun openConversation(conversation: Conversation) {
        _activeConversation.value = conversation
        _activeMessages.value = repository.getMessages(conversation.id)
        navigateTo(AppScreen.CHAT)
    }

    fun startChatForProduct(product: ProductListing) {
        val conv = repository.startOrGetConversation(product)
        openConversation(conv)
    }

    fun sendMessage(text: String, isOffer: Boolean = false, offerAmount: Double? = null) {
        val conv = _activeConversation.value ?: return
        repository.sendMessage(conv.id, text, isOffer, offerAmount)
        _activeMessages.value = repository.getMessages(conv.id)
    }

    // Create / Publish Listing Form State
    var formTitle = MutableStateFlow("")
    var formCategory = MutableStateFlow("bikes-transport")
    var formType = MutableStateFlow(ListingType.SELL)
    var formPrice = MutableStateFlow("")
    var formOriginalPrice = MutableStateFlow("")
    var formCondition = MutableStateFlow(ProductCondition.GOOD)
    var formLocation = MutableStateFlow("BH-4, Block A")
    var formDescription = MutableStateFlow("")
    var formIsUrgent = MutableStateFlow(false)
    var formIsExchange = MutableStateFlow(false)
    var formExchangePref = MutableStateFlow("")
    var formRentalUnit = MutableStateFlow("per day")

    private val _listingCreationSuccess = MutableStateFlow<String?>(null)
    val listingCreationSuccess: StateFlow<String?> = _listingCreationSuccess.asStateFlow()

    fun clearCreationSuccess() {
        _listingCreationSuccess.value = null
    }

    fun submitNewListing(): Boolean {
        val title = formTitle.value.trim()
        val priceStr = formPrice.value.trim()
        if (title.isBlank() || priceStr.isBlank()) {
            return false
        }

        val price = priceStr.toDoubleOrNull() ?: 0.0
        val originalPrice = formOriginalPrice.value.trim().toDoubleOrNull()
        val cats = repository.getCategories()
        val cat = cats.find { it.id == formCategory.value } ?: cats[1]
        val user = currentUser.value

        // Sample attractive photos matching category
        val defaultImage = when (cat.id) {
            "bikes-transport" -> "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80"
            "computers-laptops" -> "https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?w=800&q=80"
            "mobiles-gadgets" -> "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&q=80"
            "books-sports-hobbies" -> "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=800&q=80"
            "furniture-hostel" -> "https://images.unsplash.com/photo-1580481077195-c3a821a5060f?w=800&q=80"
            "electronics-appliances" -> "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=800&q=80"
            else -> "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=800&q=80"
        }

        val newListing = ProductListing(
            id = "user-prod-${UUID.randomUUID()}",
            title = title,
            price = price,
            originalPrice = originalPrice,
            listingType = formType.value,
            condition = formCondition.value,
            categoryId = cat.id,
            categoryName = cat.name,
            description = formDescription.value.ifBlank { "Offered by ${user.name} on ${user.campus}." },
            imageUrl = defaultImage,
            location = formLocation.value.ifBlank { user.hostel },
            isUrgent = formIsUrgent.value,
            isVerified = true,
            sellerId = user.id,
            sellerName = user.name + " (You)",
            sellerDepartment = "Computer Science",
            sellerRating = 5.0,
            isExchangeEligible = formIsExchange.value || formType.value == ListingType.EXCHANGE,
            exchangePreferences = formExchangePref.value,
            rentalDurationUnit = formRentalUnit.value,
            createdAt = "Just now"
        )

        repository.addListing(newListing)

        // Reset form
        formTitle.value = ""
        formPrice.value = ""
        formOriginalPrice.value = ""
        formDescription.value = ""
        formExchangePref.value = ""
        formIsUrgent.value = false
        formIsExchange.value = false

        _listingCreationSuccess.value = "Your listing \"$title\" has been published to $selectedCampus!"
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
