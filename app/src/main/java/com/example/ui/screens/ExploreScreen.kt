package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductCondition
import com.example.data.model.ProductListing
import com.example.ui.components.ProductCard
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnNavy
import com.example.ui.theme.ExOwnSlate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    listings: List<ProductListing>,
    savedIds: Set<String>,
    categories: List<ListingCategory>,
    searchQuery: String,
    selectedCategoryId: String,
    selectedType: ListingType?,
    selectedCondition: ProductCondition?,
    filterOnlyUrgent: Boolean,
    filterOnlyExchange: Boolean,
    sortBy: String,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onTypeSelect: (ListingType?) -> Unit,
    onConditionSelect: (ProductCondition?) -> Unit,
    onToggleUrgent: () -> Unit,
    onToggleExchange: () -> Unit,
    onSortChange: (String) -> Unit,
    onResetFilters: () -> Unit,
    onProductClick: (ProductListing) -> Unit,
    onSaveToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("explore_screen")
    ) {
        // Search Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = {
                    Text(
                        text = "Search bicycles, textbooks, laptops, PGs...",
                        fontSize = 13.sp,
                        color = ExOwnSlate
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = ExOwnBlue
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = ExOwnSlate
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ExOwnBlue,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_text_field")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Primary Type Filters (All, Buy, Rent, Exchange, Services)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedType == null && !filterOnlyExchange,
                        onClick = { onTypeSelect(null) },
                        label = { Text("All", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ExOwnBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                }

                item {
                    FilterChip(
                        selected = selectedType == ListingType.SELL,
                        onClick = { onTypeSelect(if (selectedType == ListingType.SELL) null else ListingType.SELL) },
                        label = { Text("Buy", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ExOwnNavy,
                            selectedLabelColor = Color.White
                        )
                    )
                }

                item {
                    FilterChip(
                        selected = selectedType == ListingType.RENT,
                        onClick = { onTypeSelect(if (selectedType == ListingType.RENT) null else ListingType.RENT) },
                        label = { Text("Rent", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFD97706),
                            selectedLabelColor = Color.White
                        )
                    )
                }

                item {
                    FilterChip(
                        selected = filterOnlyExchange || selectedType == ListingType.EXCHANGE,
                        onClick = onToggleExchange,
                        label = { Text("Exchange", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ExOwnEmerald,
                            selectedLabelColor = Color.White
                        )
                    )
                }

                item {
                    FilterChip(
                        selected = filterOnlyUrgent,
                        onClick = onToggleUrgent,
                        label = { Text("🔥 Urgent Deals", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFEF4444),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Categories Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategoryId == category.id
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) ExOwnBlue.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ExOwnBlue)) else null,
                        modifier = Modifier
                            .clickable { onCategorySelect(category.id) }
                            .padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = category.name,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ExOwnBlue else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Subheader: Results count & Sort toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${listings.size} listings found",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ExOwnSlate
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = when (sortBy) {
                        "PRICE_ASC" -> "Price: Low to High"
                        "PRICE_DESC" -> "Price: High to Low"
                        else -> "Newest First"
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ExOwnBlue,
                    modifier = Modifier.clickable {
                        val next = when (sortBy) {
                            "NEWEST" -> "PRICE_ASC"
                            "PRICE_ASC" -> "PRICE_DESC"
                            else -> "NEWEST"
                        }
                        onSortChange(next)
                    }
                )
            }
        }

        // Results Grid or Empty State
        if (listings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                tint = ExOwnSlate,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No listings found",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Try adjusting your search query, clearing filters, or browsing other categories.",
                        fontSize = 13.sp,
                        color = ExOwnSlate,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onResetFilters,
                        colors = ButtonDefaults.buttonColors(containerColor = ExOwnBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Reset All Filters")
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(listings, key = { it.id }) { listing ->
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
