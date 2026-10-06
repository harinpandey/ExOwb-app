package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Campus
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductCondition
import com.example.ui.components.CampusSelector
import com.example.ui.components.CampusSelectorStyle
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.ElectricBlueBright
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateListingScreen(
    categories: List<ListingCategory>,
    title: String,
    onTitleChange: (String) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    type: ListingType,
    onTypeChange: (ListingType) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    originalPrice: String,
    onOriginalPriceChange: (String) -> Unit,
    condition: ProductCondition,
    onConditionChange: (ProductCondition) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    isUrgent: Boolean,
    onUrgentChange: (Boolean) -> Unit,
    isExchange: Boolean,
    onExchangeChange: (Boolean) -> Unit,
    exchangePref: String,
    onExchangePrefChange: (String) -> Unit,
    rentalUnit: String,
    onRentalUnitChange: (String) -> Unit,
    onSubmit: () -> Boolean,
    onBack: () -> Unit,
    selectedCampus: Campus? = null,
    onCampusChange: (Campus) -> Unit = {},
    campuses: List<Campus> = emptyList(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Post to Campus Marketplace",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("create_listing_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Target University Campus Field
            if (selectedCampus != null && campuses.isNotEmpty()) {
                item {
                    CampusSelector(
                        selectedCampus = selectedCampus,
                        onCampusSelected = onCampusChange,
                        campuses = campuses,
                        style = CampusSelectorStyle.FIELD,
                        label = "Posting Target Campus"
                    )
                }
            }

            // Listing Type Selection (Sell, Exchange, Rent)
            item {
                Column {
                    Text(
                        text = "Listing Type",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(ListingType.SELL, ListingType.EXCHANGE, ListingType.RENT, ListingType.BUY).forEach { lType ->
                            val isSelected = type == lType
                            FilterChip(
                                selected = isSelected,
                                onClick = { onTypeChange(lType) },
                                label = { Text(if (lType == ListingType.BUY) "Wanted" else lType.label, fontWeight = FontWeight.SemiBold) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlueBright,
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
                }
            }

            // Title
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_title_input"),
                    label = { Text("Item Title") },
                    placeholder = { Text("e.g., Hero Sprint Cycle 21 Speed or Engineering Maths Book") },
                    shape = RoundedCornerShape(12.dp),
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

            // Pricing
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = price,
                        onValueChange = onPriceChange,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("form_price_input"),
                        label = { Text(if (type == ListingType.BUY) "Max Budget (₹)" else "Selling Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlueBright,
                            unfocusedBorderColor = DarkBorder,
                            focusedContainerColor = DarkSurfaceCard,
                            unfocusedContainerColor = DarkSurfaceCard,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    OutlinedTextField(
                        value = originalPrice,
                        onValueChange = onOriginalPriceChange,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("form_original_price_input"),
                        label = { Text("Original MRP (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
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

            // Condition Selector
            item {
                Column {
                    Text(
                        text = "Condition",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProductCondition.entries.forEach { cond ->
                            val isSelected = condition == cond
                            FilterChip(
                                selected = isSelected,
                                onClick = { onConditionChange(cond) },
                                label = { Text(cond.label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlueBright,
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
                }
            }

            // Campus Pickup Location / Hostel Block
            item {
                OutlinedTextField(
                    value = location,
                    onValueChange = onLocationChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_location_input"),
                    label = { Text("Campus Meetup / Hostel Location") },
                    placeholder = { Text("e.g. BH-4 Gate, Central Library Lawn, or Uni Mall") },
                    shape = RoundedCornerShape(12.dp),
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

            // Description
            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("form_description_input"),
                    label = { Text("Description & Item Details") },
                    placeholder = { Text("Mention purchase date, condition notes, accessories included...") },
                    shape = RoundedCornerShape(12.dp),
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

            // Urgent Moving-out Toggle
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    border = BorderStroke(1.dp, DarkBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Mark as Urgent / Moving Out",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Features your item in the campus urgent banner feed.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Switch(
                            checked = isUrgent,
                            onCheckedChange = onUrgentChange,
                            colors = SwitchDefaults.colors(checkedThumbColor = ElectricBlueBright)
                        )
                    }
                }
            }

            // Submit Button
            item {
                Button(
                    onClick = { onSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("submit_listing_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueBright)
                ) {
                    Text(
                        text = "Publish to Campus Feed",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = PureWhite
                    )
                }
            }
        }
    }
}
