package com.example.data.model

enum class ListingType(val label: String) {
    SELL("Buy"),
    RENT("Rent"),
    EXCHANGE("Exchange"),
    SERVICE("Service")
}

enum class ProductCondition(val label: String) {
    BRAND_NEW("Brand New"),
    LIKE_NEW("Like New"),
    GOOD("Good"),
    FAIR("Fair")
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
    val listingType: ListingType = ListingType.SELL,
    val condition: ProductCondition = ProductCondition.GOOD,
    val categoryId: String,
    val categoryName: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val isUrgent: Boolean = false,
    val isVerified: Boolean = true,
    val sellerId: String,
    val sellerName: String,
    val sellerAvatar: String? = null,
    val sellerRating: Double = 4.9,
    val sellerDepartment: String = "B.Tech Student",
    val isExchangeEligible: Boolean = false,
    val exchangePreferences: String = "",
    val rentalDurationUnit: String = "per day", // "per day", "per month"
    val isSaved: Boolean = false,
    val isSold: Boolean = false,
    val createdAt: String = "Today"
)

data class HousingListing(
    val id: String,
    val title: String,
    val locality: String,
    val campusProximity: String,
    val monthlyRent: Int,
    val roomType: String, // "Single Room", "2-Sharing PG", "Studio Apartment"
    val furnishedStatus: String, // "Fully Furnished", "Semi-Furnished"
    val amenities: List<String>,
    val imageUrl: String,
    val contactName: String,
    val contactPhone: String,
    val isVerified: Boolean = true
)

data class RoommateListing(
    val id: String,
    val name: String,
    val gender: String,
    val courseYear: String,
    val budget: String,
    val preferredLocation: String,
    val lifestyleTags: List<String>,
    val bio: String,
    val moveInDate: String
)

data class CampusServiceItem(
    val id: String,
    val title: String,
    val price: Double,
    val providerName: String,
    val providerHostel: String,
    val description: String,
    val rating: Double,
    val turnaroundTime: String,
    val imageUrl: String
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val isOffer: Boolean = false,
    val offerAmount: Double? = null
)

data class Conversation(
    val id: String,
    val listingId: String,
    val listingTitle: String,
    val listingPrice: Double,
    val listingImageUrl: String,
    val otherUserName: String,
    val otherUserLocation: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0
)

data class StudentUser(
    val id: String,
    val name: String,
    val email: String,
    val university: String,
    val campus: String,
    val hostel: String,
    val isVerified: Boolean,
    val itemsListed: Int,
    val itemsRehomed: Int,
    val moneySaved: Double,
    val co2SavedKg: Int
)
