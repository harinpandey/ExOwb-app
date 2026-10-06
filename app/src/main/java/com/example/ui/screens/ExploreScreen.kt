package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Campus
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductListing
import com.example.ui.components.ProductCard
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueBright
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    campus: Campus?,
    categories: List<ListingCategory>,
    selectedCategoryId: String,
    onSelectCategory: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filterOnlyUrgent: Boolean,
    onToggleUrgent: () -> Unit,
    filterOnlyExchange: Boolean,
    onToggleExchange: () -> Unit,
    sortBy: String,
    onSortChange: (String) -> Unit,
    listings: List<ProductListing>,
    onProductClick: (ProductListing) -> Unit,
    onSaveToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedTransactionType by remember { mutableStateOf("ALL") } // ALL, BUY, RENT, EXCHANGE

    // Filter listings based on transaction type locally if specified
    val displayedListings = remember(listings, selectedTransactionType) {
        when (selectedTransactionType) {
            "BUY" -> listings.filter { it.listingType == ListingType.SELL || it.listingType == ListingType.BUY }
            "RENT" -> listings.filter { it.listingType == ListingType.RENT }
            "EXCHANGE" -> listings.filter { it.listingType == ListingType.EXCHANGE || it.isExchangeEligible }
            else -> listings
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("explore_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // 1. Natural Search Input
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("explore_search_input"),
                    placeholder = {
                        Text(
                            "Try \"cycle under ₹5000 near BH-4\"...",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ElectricBlueBright
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = TextSecondary
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlueBright,
                        unfocusedBorderColor = DarkBorder,
                        focusedContainerColor = DarkSurfaceCard,
                        unfocusedContainerColor = DarkSurfaceCard,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }
        }

        // 2. Transaction Mode Row: All | Buy | Rent | Exchange
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransactionModeChip(
                    label = "All",
                    selected = selectedTransactionType == "ALL",
                    onClick = { selectedTransactionType = "ALL" }
                )
                TransactionModeChip(
                    label = "Buy",
                    selected = selectedTransactionType == "BUY",
                    onClick = { selectedTransactionType = "BUY" }
                )
                TransactionModeChip(
                    label = "Rent",
                    selected = selectedTransactionType == "RENT",
                    onClick = { selectedTransactionType = "RENT" }
                )
                TransactionModeChip(
                    label = "Exchange",
                    selected = selectedTransactionType == "EXCHANGE",
                    onClick = { selectedTransactionType = "EXCHANGE" }
                )
            }
        }

        // 3. Category Horizontal Row + Filter Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat.id == selectedCategoryId
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSelectCategory(cat.id) },
                            label = { Text(cat.name, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricBlue,
                                selectedLabelColor = PureWhite,
                                containerColor = DarkSurfaceCard,
                                labelColor = TextSecondary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) ElectricBlueBright else DarkBorder
                            )
                        )
                    }
                }

                // Filter button
                Card(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { showFilterSheet = true }
                        .testTag("open_filters_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (filterOnlyUrgent || filterOnlyExchange || sortBy != "newest") ElectricBlue.copy(alpha = 0.2f) else DarkSurfaceCard
                    ),
                    border = BorderStroke(1.dp, if (filterOnlyUrgent || filterOnlyExchange || sortBy != "newest") ElectricBlueBright else DarkBorder)
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (filterOnlyUrgent || filterOnlyExchange || sortBy != "newest") ElectricBlueBright else TextSecondary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                    )
                }
            }
        }

        // 4. Results Count Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${displayedListings.size} campus listings",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                if (searchQuery.isNotEmpty() || selectedCategoryId != "all" || selectedTransactionType != "ALL" || filterOnlyUrgent) {
                    Text(
                        text = "Filtered in ${campus?.code ?: "Campus"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricBlueBright
                    )
                }
            }
        }

        // 5. 2-Column Responsive Product Grid
        if (displayedListings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    border = BorderStroke(1.dp, DarkBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No matches on ${campus?.name ?: "this campus"}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try another category or post a Wanted request to ask peers for this item.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            val chunkedItems = displayedListings.chunked(2)
            items(chunkedItems) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (item in rowItems) {
                        Box(modifier = Modifier.weight(1f)) {
                            ProductCard(
                                product = item,
                                onClick = { onProductClick(item) },
                                onSaveToggle = onSaveToggle
                            )
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = DarkSurface,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Refine Filters",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    IconButton(onClick = { showFilterSheet = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Urgent Filter Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Urgent Moving-Out Deals", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Text("Items priced to sell quickly before semester break", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                    Switch(
                        checked = filterOnlyUrgent,
                        onCheckedChange = { onToggleUrgent() },
                        colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = ElectricBlue)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = DarkBorder)
                Spacer(modifier = Modifier.height(14.dp))

                // Exchange Eligible Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Exchange Welcome Only", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Text("Sellers open to peer trades or notes/gear swap", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                    Switch(
                        checked = filterOnlyExchange,
                        onCheckedChange = { onToggleExchange() },
                        colors = SwitchDefaults.colors(checkedThumbColor = PureWhite, checkedTrackColor = ElectricBlue)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = DarkBorder)
                Spacer(modifier = Modifier.height(14.dp))

                // Sort Options
                Text("SORT BY", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TextMuted)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = sortBy == "newest",
                        onClick = { onSortChange("newest") },
                        label = { Text("Newest") }
                    )
                    FilterChip(
                        selected = sortBy == "price_low",
                        onClick = { onSortChange("price_low") },
                        label = { Text("Price: Low to High") }
                    )
                    FilterChip(
                        selected = sortBy == "price_high",
                        onClick = { onSortChange("price_high") },
                        label = { Text("Price: High to Low") }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (filterOnlyUrgent) onToggleUrgent()
                            if (filterOnlyExchange) onToggleExchange()
                            onSortChange("newest")
                            showFilterSheet = false
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reset")
                    }

                    Button(
                        onClick = { showFilterSheet = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                    ) {
                        Text("Show Results")
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionModeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (selected) ElectricBlue else DarkSurfaceCard,
        border = BorderStroke(1.dp, if (selected) ElectricBlueBright else DarkBorder),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) PureWhite else TextSecondary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}
