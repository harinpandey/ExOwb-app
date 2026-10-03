package com.example

import com.example.data.model.ListingType
import com.example.data.model.ProductCondition
import com.example.data.model.ProductListing
import com.example.data.model.StudentUser
import com.example.data.repository.ExOwnRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testInitialListingsLoaded() {
        val repo = ExOwnRepository()
        val listings = repo.listings.value
        assertTrue("Listings should not be empty", listings.isNotEmpty())
        val firefox = repo.getListingById("cmp02n48h005kxxf6tre4zcpc")
        assertNotNull("Firefox cycle listing should exist", firefox)
        assertEquals("Firefox Geared Cycle (21 Speed)", firefox?.title)
    }

    @Test
    fun testUserSessionUpdate() {
        val repo = ExOwnRepository()
        val customUser = StudentUser(
            id = "test-student-1",
            name = "Priya Sharma",
            email = "priya.s@campus.edu",
            university = "Apex Institute of Technology",
            campus = "North Campus",
            hostel = "Girls Hostel 2",
            isVerified = true,
            itemsListed = 2,
            itemsRehomed = 5,
            moneySaved = 8500.0,
            co2SavedKg = 20
        )
        repo.updateUserSession(customUser)
        assertEquals("Priya Sharma", repo.currentUser.value.name)
        assertEquals("Girls Hostel 2", repo.currentUser.value.hostel)
    }

    @Test
    fun testToggleSaveListing() {
        val repo = ExOwnRepository()
        val id = "cmp02n410005ixxf6q1sa6pku"
        assertFalse(repo.savedListingIds.value.contains(id))
        repo.toggleSave(id)
        assertTrue(repo.savedListingIds.value.contains(id))
        repo.toggleSave(id)
        assertFalse(repo.savedListingIds.value.contains(id))
    }

    @Test
    fun testAddNewListingAndMarkSold() {
        val repo = ExOwnRepository()
        val newListing = ProductListing(
            id = "test-item-99",
            title = "Scientific Calculator FX-991EX",
            price = 600.0,
            listingType = ListingType.SELL,
            condition = ProductCondition.LIKE_NEW,
            categoryId = "books-sports-hobbies",
            categoryName = "Books & Study",
            description = "Calculates matrix, statistics, integrals",
            imageUrl = "https://example.com/calc.jpg",
            location = "BH-1",
            sellerId = "test-student-1",
            sellerName = "Priya Sharma"
        )
        repo.addListing(newListing)
        assertNotNull(repo.getListingById("test-item-99"))

        repo.markAsSold("test-item-99")
        assertTrue(repo.getListingById("test-item-99")?.isSold == true)
    }
}
