package com.example.data.model

data class Campus(
    val id: String,
    val code: String,
    val name: String,
    val city: String,
    val state: String,
    val studentCount: Int = 35000,
    val activeListingsCount: Int = 2340,
    val verificationDomain: String = "@lpu.in",
    val zones: List<String> = emptyList(),
    val popularCollections: List<String> = emptyList(),
    val moveOutActive: Boolean = true
)

enum class ListingType(val label: String, val badgeColor: Long) {
    BUY("Wanted", 0xFF2563EB),
    SELL("For Sale", 0xFF059669),
    RENT("Rental", 0xFFD97706),
    EXCHANGE("Exchange", 0xFF7C3AED),
    HOUSING("Housing", 0xFF0284C7),
    GIG("Student Gig", 0xFFDB2777)
}

enum class ProductCondition(val label: String) {
    NEW("Brand New / Unused"),
    LIKE_NEW("Like New"),
    GOOD("Good Condition"),
    FAIR("Fair / Usable")
}

data class ListingCategory(
    val id: String,
    val name: String,
    val iconName: String,
    val count: Int
)

data class ProductListing(
    val id: String,
    val title: String,
    val price: Double,
    val originalPrice: Double? = null,
    val listingType: ListingType,
    val condition: ProductCondition,
    val categoryId: String,
    val categoryName: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val campusId: String,
    val campusCode: String,
    val campusCity: String,
    val isUrgent: Boolean = false,
    val isVerified: Boolean = true,
    val sellerId: String,
    val sellerName: String,
    val sellerAvatar: String? = null,
    val sellerRating: Double = 4.8,
    val sellerDepartment: String = "Computer Science",
    val isExchangeEligible: Boolean = false,
    val exchangePreferences: String = "",
    val rentalDurationUnit: String = "month",
    val isSaved: Boolean = false,
    val isSold: Boolean = false,
    val isNegotiable: Boolean = true,
    val createdAt: String = "Just now"
)

data class StudentUser(
    val id: String,
    val name: String,
    val email: String,
    val university: String,
    val campus: String,
    val campusId: String,
    val hostel: String,
    val isVerified: Boolean = true,
    val activeListings: Int = 3,
    val soldListings: Int = 7,
    val totalSavedInr: Double = 14250.0,
    val trustScore: Int = 98,
    val department: String = "Computer Science & Eng",
    val year: String = "3rd Year",
    val avatarUrl: String? = null
)

data class HousingListing(
    val id: String,
    val title: String,
    val rentPrice: Double,
    val deposit: Double,
    val type: String,
    val occupancy: String,
    val distanceToCampus: String,
    val address: String,
    val campusId: String,
    val amenities: List<String>,
    val imageUrl: String,
    val contactPhone: String = "+91 98765 43210",
    val isVerified: Boolean = true
)

data class RoommateListing(
    val id: String,
    val studentName: String,
    val gender: String,
    val course: String,
    val budgetPerMonth: Double,
    val preferredLocation: String,
    val campusId: String,
    val habits: List<String>,
    val bio: String
)

data class CampusServiceItem(
    val id: String,
    val title: String,
    val category: String,
    val providerName: String,
    val campusId: String,
    val rating: Double,
    val priceStarting: Double,
    val description: String,
    val deliveryTime: String,
    val tags: List<String>
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isMine: Boolean,
    val isOffer: Boolean = false,
    val offerAmount: Double? = null
)

data class Conversation(
    val id: String,
    val otherUserName: String,
    val otherUserAvatar: String? = null,
    val otherUserCampus: String = "LPU",
    val lastMessage: String,
    val lastTimestamp: String,
    val unreadCount: Int = 0,
    val listingId: String? = null,
    val listingTitle: String? = null,
    val listingPrice: Double? = null,
    val listingImage: String? = null
)
