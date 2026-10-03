package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductListing
import com.example.ui.components.ProductCard
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnNavy
import com.example.ui.theme.ExOwnSlate
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ServicesTab

@Composable
fun HomeScreen(
    listings: List<ProductListing>,
    savedIds: Set<String>,
    categories: List<ListingCategory>,
    currentCampus: String,
    onCategoryClick: (String) -> Unit,
    onProductClick: (ProductListing) -> Unit,
    onSaveToggle: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onNavigateServicesTab: (ServicesTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val urgentDeals = listings.filter { it.isUrgent && !it.isSold }
    val rentalItems = listings.filter { it.listingType == ListingType.RENT && !it.isSold }
    val exchangeItems = listings.filter { (it.isExchangeEligible || it.listingType == ListingType.EXCHANGE) && !it.isSold }
    val recentItems = listings.filter { !it.isSold }.take(6)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = ExOwnNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_campus_banner),
                        contentDescription = "Exchange Own Repeat Hero Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient Scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0x990F172A),
                                        Color(0xEE0F172A)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = ExOwnEmerald,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "EXCHANGE. OWN. REPEAT.",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "Campus Recommerce Ecosystem",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Buy, sell, rent & swap textbooks, gadgets, bicycles & essentials with classmates.",
                                color = Color(0xFFCBD5E1),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = ExOwnEmerald,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "100% Student Verified",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Surface(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricBolt,
                                        contentDescription = null,
                                        tint = Color(0xFFFBBF24),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Zero Commission",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Action Hub (Buy, Rent, Exchange, Housing, Services)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Quick Services",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickActionButton(
                        icon = Icons.Default.ShoppingBag,
                        label = "Buy",
                        bgColor = Color(0xFFEFF6FF),
                        iconColor = ExOwnBlue,
                        onClick = { onNavigate(AppScreen.EXPLORE) }
                    )

                    QuickActionButton(
                        icon = Icons.Default.Timer,
                        label = "Rent",
                        bgColor = Color(0xFFFEF3C7),
                        iconColor = Color(0xFFD97706),
                        onClick = {
                            onCategoryClick("all")
                            onNavigate(AppScreen.EXPLORE)
                        }
                    )

                    QuickActionButton(
                        icon = Icons.Default.SwapHoriz,
                        label = "Exchange",
                        bgColor = Color(0xFFECFDF5),
                        iconColor = ExOwnEmerald,
                        onClick = {
                            onNavigate(AppScreen.EXPLORE)
                        }
                    )

                    QuickActionButton(
                        icon = Icons.Default.Apartment,
                        label = "Housing",
                        bgColor = Color(0xFFF3E8FF),
                        iconColor = Color(0xFF7E22CE),
                        onClick = {
                            onNavigateServicesTab(ServicesTab.HOUSING)
                            onNavigate(AppScreen.SERVICES)
                        }
                    )

                    QuickActionButton(
                        icon = Icons.Default.Handshake,
                        label = "Roommates",
                        bgColor = Color(0xFFFFEDD5),
                        iconColor = Color(0xFFEA580C),
                        onClick = {
                            onNavigateServicesTab(ServicesTab.ROOMMATES)
                            onNavigate(AppScreen.SERVICES)
                        }
                    )
                }
            }
        }

        // Categories Carousel
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ExOwnBlue,
                        modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { category ->
                        CategoryCardItem(
                            category = category,
                            onClick = {
                                onCategoryClick(category.id)
                                onNavigate(AppScreen.EXPLORE)
                            }
                        )
                    }
                }
            }
        }

        // Section: Urgent Moving Out Deals
        if (urgentDeals.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFFEF4444),
                                shape = CircleShape,
                                modifier = Modifier.size(8.dp)
                            ) {}
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Urgent Moving Out Deals",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        Text(
                            text = "See More",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ExOwnBlue,
                            modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(urgentDeals) { listing ->
                            Box(modifier = Modifier.width(220.dp)) {
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

        // Section: Campus Rentals (Borrow for a day or month)
        if (rentalItems.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Campus Gear for Rent",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "Borrow cycles, gadgets & tools without buying",
                                fontSize = 12.sp,
                                color = ExOwnSlate
                            )
                        }

                        Text(
                            text = "All Rentals",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ExOwnBlue,
                            modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(rentalItems) { listing ->
                            Box(modifier = Modifier.width(220.dp)) {
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

        // Section: Exchange & Barter
        if (exchangeItems.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(ExOwnEmerald, Color(0xFF34D399))))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = null,
                                    tint = ExOwnEmerald,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Exchange & Trade Club",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF064E3B)
                                )
                            }

                            Surface(
                                color = ExOwnEmerald,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                            ) {
                                Text(
                                    text = "Explore Barter",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Swap textbooks, course materials, or electronics directly with peers for ₹0 cash.",
                            fontSize = 12.sp,
                            color = Color(0xFF065F46)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(exchangeItems) { listing ->
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

        // Section: Recent Drops on Campus
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Fresh Campus Drops",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = "Nearby: $currentCampus",
                            fontSize = 11.sp,
                            color = ExOwnSlate
                        )
                    }

                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ExOwnBlue,
                        modifier = Modifier.clickable { onNavigate(AppScreen.EXPLORE) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        // 2-column grid of Recent items
        items(recentItems.chunked(2)) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    ProductCard(
                        listing = pair[0],
                        isSaved = savedIds.contains(pair[0].id),
                        onSaveToggle = { onSaveToggle(pair[0].id) },
                        onClick = { onProductClick(pair[0]) }
                    )
                }

                if (pair.size > 1) {
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(
                            listing = pair[1],
                            isSaved = savedIds.contains(pair[1].id),
                            onSaveToggle = { onSaveToggle(pair[1].id) },
                            onClick = { onProductClick(pair[1]) }
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    bgColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Surface(
            color = bgColor,
            shape = CircleShape,
            modifier = Modifier.size(52.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CategoryCardItem(
    category: ListingCategory,
    onClick: () -> Unit
) {
    val icon = when (category.id) {
        "bikes-transport" -> Icons.Default.DirectionsBike
        "computers-laptops" -> Icons.Default.Laptop
        "mobiles-gadgets" -> Icons.Default.PhoneAndroid
        "furniture-hostel" -> Icons.Default.Chair
        "books-sports-hobbies" -> Icons.Default.MenuBook
        "electronics-appliances" -> Icons.Default.Kitchen
        "gaming-entertainment" -> Icons.Default.SportsEsports
        "services" -> Icons.Default.Build
        else -> Icons.Default.ShoppingBag
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag("category_${category.id}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = category.name,
                tint = ExOwnBlue,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = category.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
