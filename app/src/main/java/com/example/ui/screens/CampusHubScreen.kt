package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Campus
import com.example.data.model.ProductListing
import com.example.ui.components.ProductCard
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnDarkBase
import com.example.ui.theme.ExOwnDarkBorder
import com.example.ui.theme.ExOwnDarkSurface1
import com.example.ui.theme.ExOwnDarkSurface2
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnTextMuted
import com.example.ui.theme.ExOwnTextPrimary
import com.example.ui.theme.ExOwnTextSecondary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ServicesTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusHubScreen(
    campus: Campus,
    campusListings: List<ProductListing>,
    savedIds: Set<String>,
    onChangeCampusClick: () -> Unit,
    onBackClick: () -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onNavigateServicesTab: (ServicesTab) -> Unit,
    onProductClick: (ProductListing) -> Unit,
    onSaveToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("campus_hub_screen"),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${campus.code} Campus Hub",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = ExOwnTextPrimary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ExOwnTextPrimary
                        )
                    }
                },
                actions = {
                    Surface(
                        color = ExOwnDarkSurface2,
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, ExOwnDarkBorder),
                        modifier = Modifier
                            .clickable(onClick = onChangeCampusClick)
                            .padding(end = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Switch Campus",
                                tint = ExOwnBlue,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Switch",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ExOwnTextPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ExOwnDarkSurface1
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(ExOwnDarkBase)
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Campus Banner & Overview Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = ExOwnDarkSurface1),
                    border = BorderStroke(1.dp, ExOwnDarkBorder),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = campus.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ExOwnTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = ExOwnEmerald,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${campus.city}, ${campus.state}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = ExOwnTextSecondary
                                    )
                                }
                            }

                            Surface(
                                color = ExOwnBlue.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, ExOwnBlue.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = campus.code,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ExOwnBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Campus Network Statistics
                        Surface(
                            color = ExOwnDarkSurface2,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, ExOwnDarkBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${campus.studentCount / 1000}k+",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        color = ExOwnBlue
                                    )
                                    Text(
                                        text = "Students",
                                        fontSize = 10.sp,
                                        color = ExOwnTextMuted
                                    )
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${campus.activeListingsCount}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        color = ExOwnEmerald
                                    )
                                    Text(
                                        text = "Active Listings",
                                        fontSize = 10.sp,
                                        color = ExOwnTextMuted
                                    )
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${campus.verificationDomain}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ExOwnTextPrimary
                                    )
                                    Text(
                                        text = "Verified Domain",
                                        fontSize = 10.sp,
                                        color = ExOwnTextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. Campus Move-Out Season Special Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
                    border = BorderStroke(1.dp, Color(0xFF4338CA)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(Color(0xFF6366F1), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CAMPUS MOVE-OUT SPECIAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFA5B4FC),
                                letterSpacing = 0.8.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Moving out of ${campus.code} Hostels next semester?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sell your mattress, cooler, study table, cycle, or hand off room setup directly to incoming juniors.",
                            fontSize = 11.sp,
                            color = Color(0xFFC7D2FE),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { onNavigate(AppScreen.SELL) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Post Move-Out Item", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            // 3. Quick Campus Navigation Tiles
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "${campus.code} ECOSYSTEM SERVICES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = ExOwnTextMuted,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        CampusTileItem(
                            icon = Icons.Default.ShoppingBag,
                            title = "Market",
                            subtitle = "Buy & Sell",
                            color = ExOwnBlue,
                            onClick = { onNavigate(AppScreen.EXPLORE) },
                            modifier = Modifier.weight(1f)
                        )

                        CampusTileItem(
                            icon = Icons.Default.Apartment,
                            title = "Housing",
                            subtitle = "PGs & Flats",
                            color = Color(0xFFD97706),
                            onClick = {
                                onNavigateServicesTab(ServicesTab.HOUSING)
                                onNavigate(AppScreen.SERVICES)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        CampusTileItem(
                            icon = Icons.Default.Group,
                            title = "Roommates",
                            subtitle = "${campus.code} Peers",
                            color = ExOwnEmerald,
                            onClick = {
                                onNavigateServicesTab(ServicesTab.ROOMMATES)
                                onNavigate(AppScreen.SERVICES)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        CampusTileItem(
                            icon = Icons.Default.Handshake,
                            title = "Services",
                            subtitle = "Student Gigs",
                            color = Color(0xFFEC4899),
                            onClick = {
                                onNavigateServicesTab(ServicesTab.SERVICES)
                                onNavigate(AppScreen.SERVICES)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 4. Campus Zones & Safe Handover Points
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = ExOwnDarkSurface1),
                    border = BorderStroke(1.dp, ExOwnDarkBorder),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = ExOwnEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DESIGNATED SAFE HANDOVER ZONES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ExOwnEmerald,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        campus.zones.forEach { zone ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 3.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(ExOwnBlue, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = zone,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = ExOwnTextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // 5. Popular Collections at this Campus
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "POPULAR AT ${campus.code}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = ExOwnTextMuted,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(campus.popularCollections) { collection ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ExOwnDarkSurface2,
                                border = BorderStroke(1.dp, ExOwnDarkBorder),
                                modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                            ) {
                                Text(
                                    text = collection,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ExOwnTextPrimary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 6. Active Listings at this Campus
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recently Added at ${campus.code}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = ExOwnTextPrimary
                        )

                        Text(
                            text = "View All",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ExOwnBlue,
                            modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                        )
                    }
                }
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val filtered = campusListings.filter { it.campusId.equals(campus.id, ignoreCase = true) }
                    val displayList = if (filtered.isNotEmpty()) filtered else campusListings.take(4)
                    items(displayList) { listing ->
                        Box(modifier = Modifier.width(200.dp)) {
                            ProductCard(
                                listing = listing,
                                isSaved = savedIds.contains(listing.id),
                                onSaveToggle = { onSaveToggle(listing.id) },
                                onClick = { onProductClick(listing) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CampusTileItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = ExOwnDarkSurface1,
        border = BorderStroke(1.dp, ExOwnDarkBorder),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = ExOwnTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = ExOwnTextSecondary
            )
        }
    }
}
