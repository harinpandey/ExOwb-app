package com.example.data.repository

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class ExOwnRepository {

    private val _campuses = listOf(
        "Main Campus (Lawgate)",
        "North Campus (BH-1 to BH-4)",
        "South Campus (Hostel Block C & D)",
        "East Campus (Girls Hostels 1-3)",
        "Tech Park Campus"
    )
    val campuses: List<String> get() = _campuses

    private val _selectedCampus = MutableStateFlow("North Campus (BH-1 to BH-4)")
    val selectedCampus: StateFlow<String> = _selectedCampus.asStateFlow()

    fun setCampus(campus: String) {
        _selectedCampus.value = campus
    }

    private val _currentUser = MutableStateFlow(
        StudentUser(
            id = "current-user-1",
            name = "Aarav Patel",
            email = "aarav.p@campus.edu",
            university = "Apex Institute of Technology",
            campus = "North Campus",
            hostel = "BH-4, Room 312",
            isVerified = true,
            itemsListed = 3,
            itemsRehomed = 7,
            moneySaved = 14250.0,
            co2SavedKg = 38
        )
    )
    val currentUser: StateFlow<StudentUser> = _currentUser.asStateFlow()

    fun updateUserSession(user: StudentUser) {
        _currentUser.value = user
    }

    private val categories = listOf(
        ListingCategory("all", "All", "apps", 28),
        ListingCategory("bikes-transport", "Bicycles", "directions_bike", 8),
        ListingCategory("computers-laptops", "Laptops & PCs", "laptop", 6),
        ListingCategory("mobiles-gadgets", "Mobiles", "smartphone", 5),
        ListingCategory("furniture-hostel", "Hostel Furniture", "chair", 9),
        ListingCategory("books-sports-hobbies", "Books & Study", "menu_book", 12),
        ListingCategory("electronics-appliances", "Appliances", "kitchen", 4),
        ListingCategory("gaming-entertainment", "Gaming", "sports_esports", 3),
        ListingCategory("fashion", "Fashion", "checkroom", 7),
        ListingCategory("services", "Campus Services", "build", 6)
    )

    fun getCategories(): List<ListingCategory> = categories

    private val initialListings = listOf(
        ProductListing(
            id = "cmp02n48h005kxxf6tre4zcpc",
            title = "Firefox Geared Cycle (21 Speed)",
            price = 4500.0,
            originalPrice = 11999.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "bikes-transport",
            categoryName = "Bicycles",
            description = "Well maintained Firefox geared cycle. 21-speed Shimano gears, dual disc brakes, brand new mudguards and bell. Perfect for campus commuting between hostels and lecture halls.",
            imageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80",
            location = "BH-1 Cycle Stand",
            isUrgent = true,
            isVerified = true,
            sellerId = "seed-user-1",
            sellerName = "Rahul Sharma",
            sellerDepartment = "Mechanical Eng, 4th Year",
            sellerRating = 4.9,
            isExchangeEligible = true,
            exchangePreferences = "Looking for an acoustic guitar or monitor",
            createdAt = "2 hours ago"
        ),
        ProductListing(
            id = "cmp1fdsaj0001ef3xs50y8ce5",
            title = "Geared Bicycle for Rent",
            price = 50.0,
            originalPrice = 80.0,
            listingType = ListingType.RENT,
            condition = ProductCondition.GOOD,
            categoryId = "bikes-transport",
            categoryName = "Bicycles",
            description = "Available for daily or weekly rent. ₹50/day or ₹250/week. Clean chain, smooth suspension, helmet included if needed.",
            imageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80",
            location = "BH-1 Cycle Stand",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-1",
            sellerName = "Rahul Sharma",
            sellerDepartment = "Mechanical Eng, 4th Year",
            sellerRating = 4.9,
            rentalDurationUnit = "per day",
            createdAt = "5 hours ago"
        ),
        ProductListing(
            id = "cmp1fdvo20004ef3xafw6c9bh",
            title = "Gaming Laptop (RTX 3060) - Hourly / Daily",
            price = 200.0,
            originalPrice = 350.0,
            listingType = ListingType.RENT,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "computers-laptops",
            categoryName = "Laptops & PCs",
            description = "Rent high-spec laptop for CAD projects, machine learning model training, or gaming tournaments over the weekend. 16GB RAM, RTX 3060 6GB.",
            imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=800&q=80",
            location = "BH-4, Block A",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-1",
            sellerName = "Rahul Sharma",
            sellerDepartment = "Computer Science, 4th Year",
            sellerRating = 4.9,
            rentalDurationUnit = "per day",
            createdAt = "Yesterday"
        ),
        ProductListing(
            id = "cmp02n410005ixxf6q1sa6pku",
            title = "MacBook Air M1 - 16GB RAM (Space Grey)",
            price = 62000.0,
            originalPrice = 89900.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "computers-laptops",
            categoryName = "Laptops & PCs",
            description = "Apple M1 chip with upgraded 16GB unified RAM and 256GB SSD. Battery health at 91%. Comes with original MagSafe charger and protective laptop sleeve.",
            imageUrl = "https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?w=800&q=80",
            location = "BH-4, Block A",
            isUrgent = true,
            isVerified = true,
            sellerId = "seed-user-1",
            sellerName = "Rahul Sharma",
            sellerDepartment = "Computer Science, 4th Year",
            sellerRating = 4.9,
            isExchangeEligible = false,
            createdAt = "1 day ago"
        ),
        ProductListing(
            id = "cmp02n3m1005gxxf6xlrsdd7q",
            title = "iPhone 13 - 128GB - Midnight Blue",
            price = 32000.0,
            originalPrice = 59900.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "mobiles-gadgets",
            categoryName = "Mobiles",
            description = "Flawless condition, always used with Spigen case and tempered glass. 88% battery health, bill and box available. Moving abroad for masters.",
            imageUrl = "https://images.unsplash.com/photo-1632661674596-df8be070a5c5?w=800&q=80",
            location = "BH-4, Block A",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-2",
            sellerName = "Ananya Sen",
            sellerDepartment = "BioTech, 3rd Year",
            sellerRating = 5.0,
            isExchangeEligible = true,
            exchangePreferences = "iPad Air with pencil support",
            createdAt = "2 days ago"
        ),
        ProductListing(
            id = "cmp03d6nt000bv3nnnswutvgc",
            title = "Ergonomic Mesh Study Chair with Lumbar Support",
            price = 399.0,
            originalPrice = 1800.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "furniture-hostel",
            categoryName = "Hostel Furniture",
            description = "Super comfortable ergonomic mesh chair for late-night exam prep and coding sessions. Height adjustable with smooth rolling wheels.",
            imageUrl = "https://images.unsplash.com/photo-1580481077195-c3a821a5060f?w=800&q=80",
            location = "Lawgate Hostels",
            isUrgent = false,
            isVerified = false,
            sellerId = "seed-user-3",
            sellerName = "Rishikesh",
            sellerDepartment = "Civil Eng, 2nd Year",
            sellerRating = 4.7,
            createdAt = "3 days ago"
        ),
        ProductListing(
            id = "prod-book-halliday",
            title = "Halliday, Resnick & Walker - Fundamentals of Physics (Extended)",
            price = 450.0,
            originalPrice = 1450.0,
            listingType = ListingType.EXCHANGE,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "books-sports-hobbies",
            categoryName = "Books & Study",
            description = "10th edition textbook with zero pen markings. Essential for 1st & 2nd year engineering physics. Willing to sell or exchange for Cormen Algorithms book.",
            imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=800&q=80",
            location = "Girls Hostel 2 Gate",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-4",
            sellerName = "Priya Deshmukh",
            sellerDepartment = "Electrical Eng, 2nd Year",
            sellerRating = 5.0,
            isExchangeEligible = true,
            exchangePreferences = "CLRS Introduction to Algorithms or Discrete Math textbook",
            createdAt = "4 hours ago"
        ),
        ProductListing(
            id = "prod-hostel-induction",
            title = "Hostel Induction Cooker (2000W) + Non-Stick Kadhai",
            price = 850.0,
            originalPrice = 2400.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "electronics-appliances",
            categoryName = "Appliances",
            description = "Lifesaver for hostel midnight Maggi, chai, and quick meals. Preset Indian cooking menus, auto-off timer, non-stick pan included.",
            imageUrl = "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=800&q=80",
            location = "BH-2, Block C",
            isUrgent = true,
            isVerified = true,
            sellerId = "current-user-1",
            sellerName = "Aarav Patel (You)",
            sellerDepartment = "Computer Science",
            sellerRating = 4.8,
            createdAt = "Today"
        ),
        ProductListing(
            id = "prod-badminton-set",
            title = "Yonex Nanoray 18i Badminton Racket Set + Mavis Shuttles",
            price = 700.0,
            originalPrice = 1950.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "books-sports-hobbies",
            categoryName = "Books & Study",
            description = "Graphite lightweight racket with BG65 titanium stringing done at 24lbs. Includes 3 unopened Yonex Mavis 350 nylon shuttles.",
            imageUrl = "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?w=800&q=80",
            location = "Campus Sports Complex",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-5",
            sellerName = "Vikram Reddy",
            sellerDepartment = "B.Arch, 3rd Year",
            sellerRating = 4.9,
            createdAt = "Yesterday"
        ),
        ProductListing(
            id = "prod-sony-headphones",
            title = "Sony WH-1000XM4 Active Noise Cancelling Headphones",
            price = 11900.0,
            originalPrice = 24990.0,
            listingType = ListingType.EXCHANGE,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "mobiles-gadgets",
            categoryName = "Mobiles",
            description = "Industry-leading noise cancellation, perfect for studying in noisy hostel dorms and library. Includes hard travel case, aux cable, airline adapter.",
            imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&q=80",
            location = "Library Block Study Area",
            isUrgent = false,
            isVerified = true,
            sellerId = "seed-user-6",
            sellerName = "Meera Nair",
            sellerDepartment = "Design & Media, 4th Year",
            sellerRating = 5.0,
            isExchangeEligible = true,
            exchangePreferences = "Mirrorless camera lens (Sony E-mount) or DJI gimbal",
            createdAt = "3 hours ago"
        )
    )

    private val _listings = MutableStateFlow<List<ProductListing>>(initialListings)
    val listings: StateFlow<List<ProductListing>> = _listings.asStateFlow()

    private val _savedListingIds = MutableStateFlow<Set<String>>(
        setOf("cmp02n48h005kxxf6tre4zcpc", "prod-book-halliday")
    )
    val savedListingIds: StateFlow<Set<String>> = _savedListingIds.asStateFlow()

    fun toggleSave(id: String) {
        _savedListingIds.update { set ->
            if (set.contains(id)) set - id else set + id
        }
        _listings.update { current ->
            current.map { if (it.id == id) it.copy(isSaved = !it.isSaved) else it }
        }
    }

    fun addListing(newListing: ProductListing) {
        _listings.update { listOf(newListing) + it }
        _currentUser.update { it.copy(itemsListed = it.itemsListed + 1) }
    }

    fun markAsSold(id: String) {
        _listings.update { current ->
            current.map { if (it.id == id) it.copy(isSold = true) else it }
        }
        _currentUser.update {
            it.copy(
                itemsRehomed = it.itemsRehomed + 1,
                moneySaved = it.moneySaved + 1200.0,
                co2SavedKg = it.co2SavedKg + 5
            )
        }
    }

    fun deleteListing(id: String) {
        _listings.update { current -> current.filterNot { it.id == id } }
    }

    fun getListingById(id: String): ProductListing? {
        return _listings.value.find { it.id == id }
    }

    // Housing Listings
    private val housingListings = listOf(
        HousingListing(
            id = "house-1",
            title = "Cozy 1 BHK Flat near Lawgate Campus",
            locality = "Lawgate Colony, Behind Tech Park",
            campusProximity = "350m from Campus Gate 2",
            monthlyRent = 7500,
            roomType = "1 BHK Private",
            furnishedStatus = "Fully Furnished",
            amenities = listOf("High-Speed WiFi", "Air Conditioning", "RO Drinking Water", "Power Backup", "Geyser"),
            imageUrl = "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80",
            contactName = "Suresh Verma (Owner)",
            contactPhone = "+91 98765 43210",
            isVerified = true
        ),
        HousingListing(
            id = "house-2",
            title = "Premium 2-Sharing Student PG with Food",
            locality = "Adarsh Nagar, North Campus",
            campusProximity = "500m walk to Central Library",
            monthlyRent = 6200,
            roomType = "2-Sharing PG",
            furnishedStatus = "Fully Furnished",
            amenities = listOf("3 Meals Included", "Laundry Service", "Attached Balcony", "Study Table", "CCTV Security"),
            imageUrl = "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?w=800&q=80",
            contactName = "Green Leaf PG Management",
            contactPhone = "+91 98123 45678",
            isVerified = true
        ),
        HousingListing(
            id = "house-3",
            title = "Spacious Single Room in Shared Apartment",
            locality = "Green View Enclave",
            campusProximity = "800m with shuttle service",
            monthlyRent = 5500,
            roomType = "Single Private Room",
            furnishedStatus = "Semi-Furnished",
            amenities = listOf("Modular Kitchen", "Refrigerator", "Balcony View", "Gym Access", "Bike Parking"),
            imageUrl = "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800&q=80",
            contactName = "Kunal Sharma (Final Year Student)",
            contactPhone = "+91 97654 32109",
            isVerified = true
        )
    )

    fun getHousingListings(): List<HousingListing> = housingListings

    // Roommate Listings
    private val roommateListings = listOf(
        RoommateListing(
            id = "roommate-1",
            name = "Rohan Malhotra",
            gender = "Male",
            courseYear = "B.Tech CSE, 3rd Year",
            budget = "₹5,000 - ₹7,000 / month",
            preferredLocation = "Lawgate or North Campus",
            lifestyleTags = listOf("Studious & Quiet", "Night Owl", "Non-Smoker", "Tech Enthusiast"),
            bio = "Looking for a chilled-out roommate to share a 2BHK flat. I code late at night with headphones, respect quiet hours, and like keeping the place clean.",
            moveInDate = "Next Month"
        ),
        RoommateListing(
            id = "roommate-2",
            name = "Sanya Kapoor",
            gender = "Female",
            courseYear = "M.Sc Data Science, 1st Year",
            budget = "₹6,000 - ₹8,500 / month",
            preferredLocation = "Near Gate 1 / East Campus",
            lifestyleTags = listOf("Early Riser", "Vegetarian", "Clean & Tidy", "Studious"),
            bio = "Need a flatmate for a furnished apartment near college. Love cooking together and maintaining a quiet environment for studies.",
            moveInDate = "Immediate"
        ),
        RoommateListing(
            id = "roommate-3",
            name = "Tanmay Joshi",
            gender = "Male",
            courseYear = "Mechanical Engineering, 2nd Year",
            budget = "₹4,500 - ₹6,000 / month",
            preferredLocation = "BH Block Colony",
            lifestyleTags = listOf("Fitness & Gym", "Sports Enthusiast", "Friendly", "Non-Drinker"),
            bio = "Gym enthusiast and football player. Looking to share rent for a flat within walking distance from campus sports complex.",
            moveInDate = "15th of this month"
        )
    )

    fun getRoommates(): List<RoommateListing> = roommateListings

    // Campus Services
    private val campusServices = listOf(
        CampusServiceItem(
            id = "serv-1",
            title = "Laptop Repair & OS Dual-Boot Installation",
            price = 299.0,
            providerName = "Rahul Sharma",
            providerHostel = "BH-4, Block B",
            description = "Linux Ubuntu / Windows 11 installation, thermal paste repasting, slow PC cleaning, SSD upgrades on campus.",
            rating = 4.9,
            turnaroundTime = "Same Day (2-3 hrs)",
            imageUrl = "https://images.unsplash.com/photo-1597733336794-12d05021d510?w=800&q=80"
        ),
        CampusServiceItem(
            id = "serv-2",
            title = "Graphic Design & UI/UX for Club Events & Projects",
            price = 499.0,
            providerName = "Aditi Roy",
            providerHostel = "Girls Hostel 3",
            description = "Figma prototypes, poster design for campus fests, hackathons, and pitch decks. High resolution files delivered.",
            rating = 5.0,
            turnaroundTime = "24-48 Hours",
            imageUrl = "https://images.unsplash.com/photo-1561070791-2526d30994b5?w=800&q=80"
        ),
        CampusServiceItem(
            id = "serv-3",
            title = "Bicycle Tune-Up, Puncture Repair & Chain Lubrication",
            price = 149.0,
            providerName = "Mohit Tanwar",
            providerHostel = "BH-1 Stand",
            description = "Get your bicycle ready for the semester. Brake tightening, gear indexing, tube puncture fix right at your hostel gate.",
            rating = 4.8,
            turnaroundTime = "1 Hour",
            imageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80"
        )
    )

    fun getCampusServices(): List<CampusServiceItem> = campusServices

    // Chat and Conversations
    private val _conversations = MutableStateFlow<List<Conversation>>(
        listOf(
            Conversation(
                id = "conv-1",
                listingId = "cmp02n48h005kxxf6tre4zcpc",
                listingTitle = "Firefox Geared Cycle (21 Speed)",
                listingPrice = 4500.0,
                listingImageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80",
                otherUserName = "Rahul Sharma",
                otherUserLocation = "BH-1 Cycle Stand",
                lastMessage = "Yes, you can test ride it today around 5 PM at BH-1 gate!",
                lastMessageTime = "10:45 AM",
                unreadCount = 1
            ),
            Conversation(
                id = "conv-2",
                listingId = "prod-book-halliday",
                listingTitle = "Halliday, Resnick & Walker - Physics",
                listingPrice = 450.0,
                listingImageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=800&q=80",
                otherUserName = "Priya Deshmukh",
                otherUserLocation = "Girls Hostel 2 Gate",
                lastMessage = "Hey! I'm happy to trade for the Cormen book.",
                lastMessageTime = "Yesterday",
                unreadCount = 0
            )
        )
    )
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow<Map<String, List<ChatMessage>>>(
        mapOf(
            "conv-1" to listOf(
                ChatMessage(
                    id = "m1",
                    conversationId = "conv-1",
                    senderName = "Aarav Patel",
                    text = "Hi Rahul! Is the Firefox cycle still available?",
                    timestamp = "10:30 AM",
                    isFromMe = true
                ),
                ChatMessage(
                    id = "m2",
                    conversationId = "conv-1",
                    senderName = "Rahul Sharma",
                    text = "Hey Aarav! Yes it is. Everything is in top condition, just serviced last week.",
                    timestamp = "10:33 AM",
                    isFromMe = false
                ),
                ChatMessage(
                    id = "m3",
                    conversationId = "conv-1",
                    senderName = "Aarav Patel",
                    text = "Would you consider ₹4,000 for quick pickup?",
                    timestamp = "10:36 AM",
                    isFromMe = true,
                    isOffer = true,
                    offerAmount = 4000.0
                ),
                ChatMessage(
                    id = "m4",
                    conversationId = "conv-1",
                    senderName = "Rahul Sharma",
                    text = "Yes, you can test ride it today around 5 PM at BH-1 gate!",
                    timestamp = "10:45 AM",
                    isFromMe = false
                )
            ),
            "conv-2" to listOf(
                ChatMessage(
                    id = "m201",
                    conversationId = "conv-2",
                    senderName = "Aarav Patel",
                    text = "Hi Priya, I have the Cormen Algorithms book in good condition. Would you like to exchange for Halliday Physics?",
                    timestamp = "Yesterday 4:15 PM",
                    isFromMe = true
                ),
                ChatMessage(
                    id = "m202",
                    conversationId = "conv-2",
                    senderName = "Priya Deshmukh",
                    text = "Hey! I'm happy to trade for the Cormen book.",
                    timestamp = "Yesterday 5:20 PM",
                    isFromMe = false
                )
            )
        )
    )

    fun getMessages(conversationId: String): List<ChatMessage> {
        return _messages.value[conversationId] ?: emptyList()
    }

    fun sendMessage(conversationId: String, text: String, isOffer: Boolean = false, offerAmount: Double? = null) {
        val newMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            conversationId = conversationId,
            senderName = _currentUser.value.name,
            text = text,
            timestamp = "Just now",
            isFromMe = true,
            isOffer = isOffer,
            offerAmount = offerAmount
        )

        _messages.update { current ->
            val list = current[conversationId].orEmpty() + newMsg
            current + (conversationId to list)
        }

        _conversations.update { list ->
            list.map {
                if (it.id == conversationId) {
                    it.copy(
                        lastMessage = if (isOffer) "Offer: ₹${offerAmount?.toInt()}" else text,
                        lastMessageTime = "Just now",
                        unreadCount = 0
                    )
                } else it
            }
        }
    }

    fun startOrGetConversation(listing: ProductListing): Conversation {
        val existing = _conversations.value.find { it.listingId == listing.id }
        if (existing != null) return existing

        val newConv = Conversation(
            id = "conv-${System.currentTimeMillis()}",
            listingId = listing.id,
            listingTitle = listing.title,
            listingPrice = listing.price,
            listingImageUrl = listing.imageUrl,
            otherUserName = listing.sellerName,
            otherUserLocation = listing.location,
            lastMessage = "Started chat about ${listing.title}",
            lastMessageTime = "Just now",
            unreadCount = 0
        )

        val firstMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            conversationId = newConv.id,
            senderName = _currentUser.value.name,
            text = "Hi ${listing.sellerName}, I am interested in your listing: \"${listing.title}\". Is it still available?",
            timestamp = "Just now",
            isFromMe = true
        )

        _conversations.update { listOf(newConv) + it }
        _messages.update { it + (newConv.id to listOf(firstMsg)) }

        return newConv
    }
}
