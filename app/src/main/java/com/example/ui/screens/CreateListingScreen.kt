package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ListingCategory
import com.example.data.model.ListingType
import com.example.data.model.ProductCondition
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnNavy
import com.example.ui.theme.ExOwnSlate

@OptIn(ExperimentalMaterial3Api::class)
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
    onUrgentToggle: () -> Unit,
    isExchange: Boolean,
    onExchangeToggle: () -> Unit,
    exchangePref: String,
    onExchangePrefChange: (String) -> Unit,
    rentalUnit: String,
    onRentalUnitChange: (String) -> Unit,
    onSubmit: () -> Boolean,
    modifier: Modifier = Modifier
) {
    var categoryExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val selectedCategoryObj = categories.find { it.id == category } ?: categories.getOrNull(1) ?: categories[0]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("create_listing_screen")
    ) {
        Text(
            text = "Create a Campus Listing",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = "Give your items a second life. Exchange, sell, or rent with classmates.",
            fontSize = 13.sp,
            color = ExOwnSlate,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Photo Upload Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, ExOwnBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = ExOwnBlue.copy(alpha = 0.05f))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        color = ExOwnBlue.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "Add Photo",
                                tint = ExOwnBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Photo Added (Ready to publish)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ExOwnBlue
                    )
                    Text(
                        text = "Automatic high-resolution campus photo attached",
                        fontSize = 11.sp,
                        color = ExOwnSlate
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Listing Type Selector
        Text(
            text = "I WANT TO:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = ExOwnNavy
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ListingType.values().forEach { t ->
                FilterChip(
                    selected = type == t,
                    onClick = { onTypeChange(t) },
                    label = {
                        Text(
                            text = t.label,
                            fontWeight = if (type == t) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = when (t) {
                            ListingType.SELL -> ExOwnNavy
                            ListingType.RENT -> Color(0xFFD97706)
                            ListingType.EXCHANGE -> ExOwnEmerald
                            ListingType.SERVICE -> ExOwnBlue
                        },
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Title
        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
                errorMessage = null
            },
            label = { Text("Item Title *") },
            placeholder = { Text("e.g. Firefox Geared Cycle, Engineering Mechanics Book") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_listing_title"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category Selector Dropdown
        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedCategoryObj.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                categories.filter { it.id != "all" }.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat.name) },
                        onClick = {
                            onCategoryChange(cat.id)
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Price Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = price,
                onValueChange = {
                    onPriceChange(it)
                    errorMessage = null
                },
                label = { Text(if (type == ListingType.RENT) "Rent (₹) *" else "Price (₹) *") },
                placeholder = { Text("e.g. 450") },
                leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, color = ExOwnBlue) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_listing_price"),
                shape = RoundedCornerShape(12.dp)
            )

            if (type == ListingType.RENT) {
                OutlinedTextField(
                    value = rentalUnit,
                    onValueChange = onRentalUnitChange,
                    label = { Text("Unit") },
                    placeholder = { Text("per day") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                OutlinedTextField(
                    value = originalPrice,
                    onValueChange = onOriginalPriceChange,
                    label = { Text("Original MRP (₹)") },
                    placeholder = { Text("e.g. 1200") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Condition Selector
        Text(
            text = "CONDITION:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = ExOwnNavy
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ProductCondition.values().forEach { c ->
                FilterChip(
                    selected = condition == c,
                    onClick = { onConditionChange(c) },
                    label = { Text(c.label, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ExOwnNavy,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Location / Hostel Block
        OutlinedTextField(
            value = location,
            onValueChange = onLocationChange,
            label = { Text("Pickup Location / Hostel *") },
            placeholder = { Text("e.g. BH-4 Block A, Lawgate Gate 2") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_listing_location"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            placeholder = { Text("Provide details about age, accessories, why you're selling, test-ride info...") },
            minLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_listing_desc"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Urgent Moving Out Toggle
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🔥 Mark as Urgent Deal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFFEF4444)
                    )
                    Text(
                        text = "Moving out of hostel soon? Highlight your listing to get fast offers.",
                        fontSize = 11.sp,
                        color = ExOwnSlate
                    )
                }
                Switch(
                    checked = isUrgent,
                    onCheckedChange = { onUrgentToggle() },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFEF4444), checkedTrackColor = Color(0xFFFEE2E2))
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Exchange Eligible Toggle
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "🔄 Open to Barter / Exchange",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = ExOwnEmerald
                        )
                        Text(
                            text = "Allow peers to offer trades instead of cash",
                            fontSize = 11.sp,
                            color = ExOwnSlate
                        )
                    }
                    Switch(
                        checked = isExchange || type == ListingType.EXCHANGE,
                        onCheckedChange = { onExchangeToggle() },
                        colors = SwitchDefaults.colors(checkedThumbColor = ExOwnEmerald, checkedTrackColor = Color(0xFFD1FAE5))
                    )
                }

                if (isExchange || type == ListingType.EXCHANGE) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = exchangePref,
                        onValueChange = onExchangePrefChange,
                        label = { Text("What items are you looking for?") },
                        placeholder = { Text("e.g. Looking for acoustic guitar, scientific calculator, or monitor") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                color = Color(0xFFFEE2E2),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFDC2626),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        Button(
            onClick = {
                val success = onSubmit()
                if (!success) {
                    errorMessage = "Please enter both a title and a valid price."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = ExOwnBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("publish_listing_btn")
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Publish to ExOwn Campus",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
