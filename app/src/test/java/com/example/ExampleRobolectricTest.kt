package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.ExOwnRepository
import com.example.ui.viewmodel.ExOwnViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ExOwn", appName)
  }

  @Test
  fun `campus persistence saves and restores selected university`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    ExOwnRepository.initPersistence(context)
    val repo = ExOwnRepository()

    // Switch campus to VIT
    val vit = repo.campuses.find { it.code == "VIT" }
    assertNotNull("VIT campus should exist", vit)
    repo.setCampus(vit!!)

    assertEquals("VIT", repo.selectedCampus.value.code)

    // Re-instantiate repository to verify persistence from SharedPreferences
    val freshRepo = ExOwnRepository()
    assertEquals("VIT", freshRepo.selectedCampus.value.code)
    assertEquals("Vellore", freshRepo.selectedCampus.value.city)
  }

  @Test
  fun `campus scoped queries filter marketplace data appropriately`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    ExOwnRepository.initPersistence(context)
    val repo = ExOwnRepository()
    val viewModel = ExOwnViewModel(repo)

    // Test default LPU campus scoping
    val lpu = repo.campuses.find { it.code == "LPU" }!!
    viewModel.selectCampus(lpu)
    assertEquals("LPU", viewModel.selectedCampus.value.code)

    val lpuListings = viewModel.getCampusScopedListings("lpu")
    assertTrue(lpuListings.isNotEmpty())
    assertTrue(lpuListings.all { it.campusId.equals("lpu", ignoreCase = true) })

    // Test SRM campus scoping
    val srm = repo.campuses.find { it.code == "SRM" }!!
    viewModel.selectCampus(srm)
    assertEquals("SRM", viewModel.selectedCampus.value.code)

    val srmListings = viewModel.getCampusScopedListings("srm")
    assertTrue(srmListings.isNotEmpty())
    assertTrue(srmListings.all { it.campusId.equals("srm", ignoreCase = true) })
  }

  @Test
  fun `all required major private universities are present`() {
    val repo = ExOwnRepository()
    val codes = repo.campuses.map { it.code }
    assertTrue(codes.contains("LPU"))
    assertTrue(codes.contains("SRM"))
    assertTrue(codes.contains("VIT"))
    assertTrue(codes.contains("CU"))
    assertTrue(codes.contains("BITS"))
    assertTrue(codes.contains("MANIPAL"))
  }
}

