package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ExOwnRepository {

    companion object {
        private const val PREFS_NAME = "exown_campus_preferences"
        private const val KEY_SAVED_CAMPUS_ID = "saved_campus_id"
        private const val KEY_SAVED_LISTINGS = "saved_listing_ids"
        private var sharedPreferences: SharedPreferences? = null

        fun initPersistence(context: Context) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }

        fun getSavedCampusId(): String? {
            return sharedPreferences?.getString(KEY_SAVED_CAMPUS_ID, null)
        }

        fun saveSelectedCampusId(campusId: String) {
            sharedPreferences?.edit()?.putString(KEY_SAVED_CAMPUS_ID, campusId)?.apply()
        }

        fun getStoredSavedListingIds(): Set<String> {
            return sharedPreferences?.getStringSet(KEY_SAVED_LISTINGS, emptySet()) ?: emptySet()
        }

        fun storeSavedListingIds(ids: Set<String>) {
            sharedPreferences?.edit()?.putStringSet(KEY_SAVED_LISTINGS, ids)?.apply()
        }
    }

    val lpuCampus = Campus(
        id = "lpu",
        code = "LPU",
        name = "Lovely Professional University",
        city = "Phagwara",
        state = "Punjab",
        studentCount = 35000,
        activeListingsCount = 2340,
        verificationDomain = "@lpu.in",
        zones = listOf("Uni Mall", "Block 34", "BH-1 to BH-8", "Law Gate", "Cheema PG Belt"),
        popularCollections = listOf("LPU Hostel Essentials", "Engineering Maths Books", "Campus Cycles"),
        moveOutActive = true
    )

    val srmCampus = Campus(
        id = "srm",
        code = "SRM",
        name = "SRM Institute of Science and Technology",
        city = "Kattankulathur",
        state = "Tamil Nadu",
        studentCount = 42000,
        activeListingsCount = 1890,
        verificationDomain = "@srmist.edu.in",
        zones = listOf("Tech Park", "UB Building", "Java Canteen", "Potheri Gate", "Estancia PG Belt"),
        popularCollections = listOf("SRM Tech Gadgets", "Bicycles & EV Scooters", "Estancia Furnishings"),
        moveOutActive = false
    )

    val vitCampus = Campus(
        id = "vit",
        code = "VIT",
        name = "Vellore Institute of Technology",
        city = "Vellore",
        state = "Tamil Nadu",
        studentCount = 38000,
        activeListingsCount = 2150,
        verificationDomain = "@vitstudent.ac.in",
        zones = listOf("SJT Complex", "Technology Tower", "Green Tower", "Foodys Hub", "All Mart", "MH-A to MH-Q"),
        popularCollections = listOf("VIT Coding Laptops", "Engineering Textbooks", "Lab Coats & Drafters"),
        moveOutActive = true
    )

    val cuCampus = Campus(
        id = "cu",
        code = "CU",
        name = "Chandigarh University",
        city = "Mohali",
        state = "Punjab",
        studentCount = 31000,
        activeListingsCount = 1420,
        verificationDomain = "@cumail.in",
        zones = listOf("Academic Block 1", "Fountain Park", "North Campus Hostel", "Kharar PG Belt"),
        popularCollections = listOf("CU Hostel Essentials", "Aeronautical & CS Notes", "Room Furniture"),
        moveOutActive = false
    )

    val bitsCampus = Campus(
        id = "bits",
        code = "BITS",
        name = "BITS Pilani",
        city = "Pilani",
        state = "Rajasthan",
        studentCount = 18000,
        activeListingsCount = 1250,
        verificationDomain = "@pilani.bits-pilani.ac.in",
        zones = listOf("Clock Tower", "Rotunda", "FD-II & FD-III", "ANC Canteen", "Vyas & Krishna Bhawan"),
        popularCollections = listOf("BITS Coding Gear", "Reference Textbooks", "Cycle Exchange"),
        moveOutActive = false
    )

    val thaparCampus = Campus(
        id = "thapar",
        code = "THAPAR",
        name = "Thapar Institute of Eng. & Tech.",
        city = "Patiala",
        state = "Punjab",
        studentCount = 16000,
        activeListingsCount = 980,
        verificationDomain = "@thapar.edu",
        zones = listOf("COS Complex", "Nirvana Canteen", "Library Lawn", "Hostel J & K", "Main Gate"),
        popularCollections = listOf("Thapar Mech Drafters", "Hostel Monitors", "Stationery & Notes"),
        moveOutActive = true
    )

    val manipalCampus = Campus(
        id = "manipal",
        code = "MANIPAL",
        name = "Manipal Academy of Higher Education",
        city = "Manipal",
        state = "Karnataka",
        studentCount = 28000,
        activeListingsCount = 1640,
        verificationDomain = "@learner.manipal.edu",
        zones = listOf("Tiger Circle", "Student Plaza", "MIT Quadrangle", "KMC Food Court", "Block 10 & 11"),
        popularCollections = listOf("Manipal Scooters & Cycles", "Medical Textbooks", "Hostel Appliances"),
        moveOutActive = false
    )

    val amityCampus = Campus(
        id = "amity",
        code = "AMITY",
        name = "Amity University",
        city = "Noida",
        state = "Uttar Pradesh",
        studentCount = 32000,
        activeListingsCount = 1390,
        verificationDomain = "@amity.edu",
        zones = listOf("H-Block Arc", "Central Plaza", "Gate 2 Canteen", "E-Block Amphitheatre"),
        popularCollections = listOf("Amity Design Gear", "Laptops & Monitors", "Fashion & Study"),
        moveOutActive = false
    )

    private val _campuses = listOf(
        lpuCampus, srmCampus, vitCampus, cuCampus, bitsCampus, thaparCampus, manipalCampus, amityCampus
    )
    val campuses: List<Campus> = _campuses

    private fun loadInitialCampus(): Campus {
        val savedId = getSavedCampusId()
        return _campuses.find { it.id.equals(savedId, ignoreCase = true) } ?: lpuCampus
    }

    private val _selectedCampus = MutableStateFlow(loadInitialCampus())
    val selectedCampus: StateFlow<Campus> = _selectedCampus.asStateFlow()

    fun setCampus(campus: Campus) {
        _selectedCampus.value = campus
        saveSelectedCampusId(campus.id)
    }

    fun setCampusById(campusId: String) {
        val found = _campuses.find { it.id.equals(campusId, ignoreCase = true) }
        if (found != null) {
            setCampus(found)
        }
    }

    fun getCampusScopedListings(campusId: String): List<ProductListing> {
        return _listings.value.filter { it.campusId.equals(campusId, ignoreCase = true) }
    }

    private val _currentUser = MutableStateFlow(
        StudentUser(
            id = "current-user-1",
            name = "Hari Pandey",
            email = "hari.pandey@lpu.in",
            university = "Lovely Professional University",
            campus = "LPU • Phagwara",
            campusId = "lpu",
            hostel = "BH-4, Room 312",
            isVerified = true,
            activeListings = 3,
            soldListings = 7,
            totalSavedInr = 14250.0,
            trustScore = 98,
            department = "Computer Science & Engineering",
            year = "3rd Year"
        )
    )
    val currentUser: StateFlow<StudentUser> = _currentUser.asStateFlow()

    fun updateUserSession(user: StudentUser) {
        _currentUser.value = user
    }

    val categories: List<ListingCategory> = listOf(
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

    private val initialListings = listOf(
        ProductListing(
            id = "item-1",
            title = "Hero Sprint Pro 21-Speed Mountain Cycle",
            price = 3800.0,
            originalPrice = 9500.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "bikes-transport",
            categoryName = "Bicycles",
            description = "Dual disc brakes, front suspension, tuned gear shifter. Serviced last month at Uni Mall cycle shop. Free cable lock included.",
            imageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80",
            location = "BH-4 Bicycle Stand",
            campusId = "lpu",
            campusCode = "LPU",
            campusCity = "Phagwara",
            isUrgent = true,
            sellerId = "user-2",
            sellerName = "Aman Sharma",
            sellerRating = 4.9,
            sellerDepartment = "Mechanical Engineering",
            isExchangeEligible = true,
            exchangePreferences = "Looking for electric kettle or study lamp"
        ),
        ProductListing(
            id = "item-2",
            title = "Higher Engineering Mathematics (B.S. Grewal, 44th Edition)",
            price = 420.0,
            originalPrice = 999.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "books-sports-hobbies",
            categoryName = "Books & Study",
            description = "Clean copy without markings or torn pages. Essential for B.Tech Semester 1 to 4 syllabus. Includes handwritten formula cheatsheet.",
            imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=800&q=80",
            location = "Block 34 Central Library",
            campusId = "lpu",
            campusCode = "LPU",
            campusCity = "Phagwara",
            sellerId = "user-3",
            sellerName = "Priya Nair",
            sellerRating = 5.0,
            sellerDepartment = "Electronics & Comm."
        ),
        ProductListing(
            id = "item-3",
            title = "Kaff Electric Kettle & Induction Stove Combo",
            price = 1100.0,
            originalPrice = 2400.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "electronics-appliances",
            categoryName = "Appliances",
            description = "1.8L quick boiling stainless kettle and 1200W single burner induction plate. Perfect for midnight Maggi and hot coffee during exam week.",
            imageUrl = "https://images.unsplash.com/photo-1585515320310-259814833e62?w=800&q=80",
            location = "GH-3 Gate 2",
            campusId = "lpu",
            campusCode = "LPU",
            campusCity = "Phagwara",
            sellerId = "user-4",
            sellerName = "Simran Kaur",
            sellerRating = 4.7
        ),
        ProductListing(
            id = "item-4",
            title = "Ergonomic Mesh Study Chair with Lumbar Support",
            price = 1450.0,
            originalPrice = 3800.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "furniture-hostel",
            categoryName = "Hostel Furniture",
            description = "Breathable mesh back, hydraulic height adjustment, smooth nylon casters. Bought in Jan 2024, selling due to hostel room change.",
            imageUrl = "https://images.unsplash.com/photo-1580481077195-c3a821a58875?w=800&q=80",
            location = "Law Gate Cheema PG",
            campusId = "lpu",
            campusCode = "LPU",
            campusCity = "Phagwara",
            isUrgent = true,
            sellerId = "current-user-1",
            sellerName = "Hari Pandey",
            sellerRating = 4.9
        ),
        ProductListing(
            id = "item-5",
            title = "Casio fx-991EX ClassWiz Scientific Calculator",
            price = 850.0,
            originalPrice = 1495.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "books-sports-hobbies",
            categoryName = "Books & Study",
            description = "High-resolution natural textbook display, 552 functions. Solar + battery dual power. Permitted in university end-term exams.",
            imageUrl = "https://images.unsplash.com/photo-1611125832047-1d7ad1e8e48f?w=800&q=80",
            location = "SJT Complex Floor 3",
            campusId = "vit",
            campusCode = "VIT",
            campusCity = "Vellore",
            sellerId = "user-5",
            sellerName = "Rahul Verma",
            sellerRating = 4.8
        ),
        ProductListing(
            id = "item-6",
            title = "Hercules Roadeo A50 26T Alloy Bicycle",
            price = 4200.0,
            originalPrice = 11000.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.GOOD,
            categoryId = "bikes-transport",
            categoryName = "Bicycles",
            description = "Lightweight alloy frame, front & rear disc brakes, smooth Shimano shifters. Ideal for daily commute from Potheri to Tech Park.",
            imageUrl = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80",
            location = "UB Tech Park Stand",
            campusId = "srm",
            campusCode = "SRM",
            campusCity = "Kattankulathur",
            sellerId = "user-6",
            sellerName = "Karthik R",
            sellerRating = 4.9
        ),
        ProductListing(
            id = "item-7",
            title = "Logitech MX Master 3S Wireless Mouse (Graphite)",
            price = 4500.0,
            originalPrice = 8995.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "computers-laptops",
            categoryName = "Laptops & PCs",
            description = "Quiet clicks, 8K DPI any-surface sensor, MagSpeed electromagnetic wheel. Bluetooth and Logi Bolt receiver with box.",
            imageUrl = "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=800&q=80",
            location = "Academic Block 1",
            campusId = "cu",
            campusCode = "CU",
            campusCity = "Mohali",
            sellerId = "user-7",
            sellerName = "Jaspreet Singh",
            sellerRating = 5.0
        ),
        ProductListing(
            id = "item-8",
            title = "Dell UltraSharp 24-inch IPS Monitor (USB-C)",
            price = 8500.0,
            originalPrice = 18000.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "computers-laptops",
            categoryName = "Laptops & PCs",
            description = "FHD InfinityEdge display with 65W USB-C power delivery, HDMI, DP. Height and pivot adjustable stand. Bill and box available.",
            imageUrl = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=800&q=80",
            location = "Rotunda Clock Tower",
            campusId = "bits",
            campusCode = "BITS",
            campusCity = "Pilani",
            sellerId = "user-8",
            sellerName = "Devansh Gupta",
            sellerRating = 4.9
        )
    )

    private val _listings = MutableStateFlow<List<ProductListing>>(initialListings)
    val listings: StateFlow<List<ProductListing>> = _listings.asStateFlow()

    private val _savedListingIds = MutableStateFlow<Set<String>>(getStoredSavedListingIds())
    val savedListingIds: StateFlow<Set<String>> = _savedListingIds.asStateFlow()

    fun toggleSave(id: String) {
        val current = _savedListingIds.value.toMutableSet()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        _savedListingIds.value = current
        storeSavedListingIds(current)
    }

    fun addListing(newListing: ProductListing) {
        val updated = listOf(newListing) + _listings.value
        _listings.value = updated
    }

    fun markAsSold(id: String) {
        val updated = _listings.value.map {
            if (it.id == id) it.copy(isSold = true) else it
        }
        _listings.value = updated
    }

    fun deleteListing(id: String) {
        _listings.value = _listings.value.filter { it.id != id }
    }

    fun getListingById(id: String): ProductListing? {
        return _listings.value.find { it.id == id }
    }

    private val initialHousingListings = listOf(
        HousingListing(
            id = "house-1",
            title = "Single Room in 3BHK Flat (Attached Balcony & Washroom)",
            rentPrice = 6500.0,
            deposit = 10000.0,
            type = "Flatshare",
            occupancy = "Single",
            distanceToCampus = "450m from Law Gate",
            address = "Green Valley Apartments, Near Cheema PG, Law Gate",
            campusId = "lpu",
            amenities = listOf("WiFi 100Mbps", "AC", "Power Backup", "Geyser", "RO Water", "Parking"),
            imageUrl = "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80"
        ),
        HousingListing(
            id = "house-2",
            title = "Premium Twin Sharing PG with 3-Time Meals",
            rentPrice = 7500.0,
            deposit = 7500.0,
            type = "Student PG",
            occupancy = "Double",
            distanceToCampus = "200m from Main Gate",
            address = "Royal Residency, GT Road",
            campusId = "lpu",
            amenities = listOf("All Meals Included", "Housekeeping", "Laundry", "Gym Access", "Study Tables"),
            imageUrl = "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?w=800&q=80"
        )
    )

    fun getHousingListings(): List<HousingListing> = initialHousingListings

    val roommateListings = listOf(
        RoommateListing(
            id = "roommate-1",
            studentName = "Rohan Mehta",
            gender = "Male",
            course = "B.Tech CSE 2nd Year",
            budgetPerMonth = 5500.0,
            preferredLocation = "Law Gate / Cheema PG Belt",
            campusId = "lpu",
            habits = listOf("Non-Smoker", "Night Owl coder", "Keeps room tidy", "Music lover (uses headphones)"),
            bio = "Looking for a chilled flatmate to split rent in a 2BHK flat. I cook decent North Indian food and study mostly in the evenings."
        ),
        RoommateListing(
            id = "roommate-2",
            studentName = "Ananya Desai",
            gender = "Female",
            course = "B.Des Fashion 3rd Year",
            budgetPerMonth = 7000.0,
            preferredLocation = "Near Block 34 / Main Gate",
            campusId = "lpu",
            habits = listOf("Early riser", "Quiet environment", "Vegetarian", "Studious"),
            bio = "Searching for a female roommate for an AC single/shared room with good security and daylight."
        )
    )

    fun getRoommates(): List<RoommateListing> = roommateListings

    private val initialCampusServices = listOf(
        CampusServiceItem(
            id = "serv-1",
            title = "Bike & Cycle Repair & Puncture at Hostel Gate",
            category = "Repair & Maintenance",
            providerName = "Vikram Cycle Works",
            campusId = "lpu",
            rating = 4.8,
            priceStarting = 50.0,
            description = "On-campus pickup or doorstep repair at BH-1 to BH-8 gates. Tube change, chain lubrication, brake adjustments.",
            deliveryTime = "Within 30 mins",
            tags = listOf("Doorstep", "Fast", "Cash/UPI")
        ),
        CampusServiceItem(
            id = "serv-2",
            title = "Engineering Assignment & Report Printing & Spiral Binding",
            category = "Printing & Stationery",
            providerName = "UniPrint Hub",
            campusId = "lpu",
            rating = 4.9,
            priceStarting = 2.0,
            description = "High quality color or B&W printouts on 75 GSM paper. Spiral or hard bound project reports delivered to your hostel desk.",
            deliveryTime = "1-2 Hours",
            tags = listOf("Per page Rs 2", "Spiral Bound", "Doorstep")
        ),
        CampusServiceItem(
            id = "serv-3",
            title = "Python, DSA & Web Dev Peer Tutoring",
            category = "Academic Peer Tutoring",
            providerName = "Kunal (Final Year CSE)",
            campusId = "lpu",
            rating = 5.0,
            priceStarting = 350.0,
            description = "1-on-1 exam prep sessions covering LeetCode basics, Python lab exams, and semester project debug support.",
            deliveryTime = "Hourly sessions",
            tags = listOf("Peer Expert", "1-on-1", "Exam Prep")
        )
    )

    fun getCampusServices(): List<CampusServiceItem> = initialCampusServices

    private val initialConversations = listOf(
        Conversation(
            id = "conv-1",
            otherUserName = "Aman Sharma",
            otherUserCampus = "LPU",
            lastMessage = "Hey! Is the Hero cycle available for a test ride today?",
            lastTimestamp = "10:45 AM",
            unreadCount = 1,
            listingId = "item-1",
            listingTitle = "Hero Sprint Pro 21-Speed Mountain Cycle",
            listingPrice = 3800.0,
            listingImage = "https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80"
        ),
        Conversation(
            id = "conv-2",
            otherUserName = "Simran Kaur",
            otherUserCampus = "LPU",
            lastMessage = "Can you do Rs 3,500? I can pick it up from BH-4 this evening.",
            lastTimestamp = "Yesterday",
            unreadCount = 0,
            listingId = "item-4",
            listingTitle = "Ergonomic Mesh Study Chair with Lumbar Support",
            listingPrice = 1450.0,
            listingImage = "https://images.unsplash.com/photo-1580481077195-c3a821a58875?w=800&q=80"
        )
    )

    private val _conversations = MutableStateFlow(initialConversations)
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _chatMessages = mutableMapOf<String, MutableStateFlow<List<ChatMessage>>>(
        "conv-1" to MutableStateFlow(
            listOf(
                ChatMessage("m-1", "conv-1", "user-2", "Hi Hari, saw your cycle post!", "10:30 AM", isMine = false),
                ChatMessage("m-2", "conv-1", "current-user-1", "Hey Aman! Yes it is in prime condition.", "10:32 AM", isMine = true),
                ChatMessage("m-3", "conv-1", "user-2", "Hey! Is the Hero cycle available for a test ride today?", "10:45 AM", isMine = false)
            )
        )
    )

    fun getMessagesForConversation(convId: String): StateFlow<List<ChatMessage>> {
        return _chatMessages.getOrPut(convId) {
            MutableStateFlow(emptyList())
        }.asStateFlow()
    }

    fun startOrGetConversation(product: ProductListing): Conversation {
        val existing = _conversations.value.find { it.listingId == product.id }
        if (existing != null) return existing

        val newConv = Conversation(
            id = "conv-${UUID.randomUUID()}",
            otherUserName = product.sellerName,
            otherUserCampus = product.campusCode,
            lastMessage = "Started chat about ${product.title}",
            lastTimestamp = "Just now",
            listingId = product.id,
            listingTitle = product.title,
            listingPrice = product.price,
            listingImage = product.imageUrl
        )
        _conversations.value = listOf(newConv) + _conversations.value
        _chatMessages[newConv.id] = MutableStateFlow(
            listOf(
                ChatMessage(
                    id = "init-${UUID.randomUUID()}",
                    conversationId = newConv.id,
                    senderId = "current-user-1",
                    text = "Hi ${product.sellerName}, is this still available?",
                    timestamp = "Just now",
                    isMine = true
                )
            )
        )
        return newConv
    }

    fun sendMessage(convId: String, text: String, isOffer: Boolean = false, offerAmount: Double? = null) {
        val flow = _chatMessages.getOrPut(convId) { MutableStateFlow(emptyList()) }
        val newMsg = ChatMessage(
            id = "m-${UUID.randomUUID()}",
            conversationId = convId,
            senderId = "current-user-1",
            text = text,
            timestamp = "Just now",
            isMine = true,
            isOffer = isOffer,
            offerAmount = offerAmount
        )
        flow.value = flow.value + newMsg

        // Update last message in conversation
        _conversations.value = _conversations.value.map {
            if (it.id == convId) {
                it.copy(lastMessage = text, lastTimestamp = "Just now", unreadCount = 0)
            } else it
        }
    }
}
