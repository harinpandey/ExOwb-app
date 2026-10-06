package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ListingType
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.ElectricBlueBright
import com.example.ui.theme.ExOwnAmber
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnPurple
import com.example.ui.theme.ExOwnTeal
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ServicesTab

/**
 * Creation Action Sheet opened by the Sell FAB.
 * Structured cleanly:
 * - Title: "What do you want to create?"
 * - 4 Core Marketplace Actions (2x2 Grid): Sell Item, Exchange, Rent out, Post a Wanted request
 * - Horizontal Divider
 * - 2 Community Actions: List a Room / PG, Find a Roommate
 * - Cancel Button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellCreationActionSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    onSelectOption: (ListingType?, AppScreen, ServicesTab?) -> Unit
) {
    if (!show) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .testTag("sell_creation_action_sheet")
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Create on Campus",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Post peer listings, trades, or roommate requests",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2x2 Grid of Core Marketplace Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GridActionTile(
                    title = "Sell Item",
                    subtitle = "Textbooks, cycles, tech",
                    icon = Icons.Default.ShoppingCart,
                    accentColor = ExOwnEmerald,
                    testTag = "action_sell_item",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(ListingType.SELL, AppScreen.SELL, null)
                        onDismiss()
                    }
                )

                GridActionTile(
                    title = "Exchange",
                    subtitle = "Trade notes, gear or books",
                    icon = Icons.Default.Refresh,
                    accentColor = ElectricBlueBright,
                    testTag = "action_exchange",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(ListingType.EXCHANGE, AppScreen.SELL, null)
                        onDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GridActionTile(
                    title = "Rent out",
                    subtitle = "Coolers, monitors, console",
                    icon = Icons.Default.DateRange,
                    accentColor = ExOwnAmber,
                    testTag = "action_rent",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(ListingType.RENT, AppScreen.SELL, null)
                        onDismiss()
                    }
                )

                GridActionTile(
                    title = "Post a Want",
                    subtitle = "Ask campus for what you need",
                    icon = Icons.Default.Search,
                    accentColor = ExOwnPurple,
                    testTag = "action_wanted",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(ListingType.BUY, AppScreen.SELL, null)
                        onDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = DarkBorder)
            Spacer(modifier = Modifier.height(12.dp))

            // Community Section: Housing & Roommate
            Text(
                text = "CAMPUS COMMUNITY",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CommunityActionTile(
                    title = "List a Room / PG",
                    subtitle = "Hostel / Flat vacant room",
                    icon = Icons.Default.Home,
                    accentColor = ExOwnTeal,
                    testTag = "action_housing",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(ListingType.HOUSING, AppScreen.SERVICES, ServicesTab.HOUSING)
                        onDismiss()
                    }
                )

                CommunityActionTile(
                    title = "Find a Roommate",
                    subtitle = "Verified student flatmate",
                    icon = Icons.Default.AccountCircle,
                    accentColor = ExOwnPurple,
                    testTag = "action_roommate",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectOption(null, AppScreen.SERVICES, ServicesTab.ROOMMATES)
                        onDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cancel / Dismiss Button
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("action_sheet_cancel"),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, DarkBorder)
            ) {
                Text("Cancel", color = TextSecondary, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun GridActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        border = BorderStroke(1.dp, DarkBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = accentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun CommunityActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        border = BorderStroke(1.dp, DarkBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        }
    }
}
