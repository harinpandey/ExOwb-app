package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricBlueBright
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AppScreen

/**
 * Premium 4-Destination Bottom Navigation Bar:
 * Home | Explore | Inbox | Me
 *
 * Sits at the bottom with safe area window insets and equal horizontal spacing.
 */
@Composable
fun ExOwnBottomBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    unreadCount: Int = 1,
    modifier: Modifier = Modifier
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 16.dp, ambientColor = Color.Black, spotColor = Color.Black)
            .testTag("exown_bottom_bar"),
        color = DarkSurface,
        tonalElevation = 6.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Subtle separating divider from content
            HorizontalDivider(
                color = DarkBorder.copy(alpha = 0.8f),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1. HOME Tab
                BottomNavItem(
                    label = "Home",
                    selected = currentScreen == AppScreen.HOME,
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    testTag = "nav_home",
                    onClick = { onNavigate(AppScreen.HOME) }
                )

                // 2. EXPLORE Tab
                BottomNavItem(
                    label = "Explore",
                    selected = currentScreen == AppScreen.EXPLORE,
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search,
                    testTag = "nav_explore",
                    onClick = { onNavigate(AppScreen.EXPLORE) }
                )

                // 3. INBOX Tab
                BottomNavItem(
                    label = "Inbox",
                    selected = currentScreen == AppScreen.INBOX,
                    selectedIcon = Icons.Filled.Email,
                    unselectedIcon = Icons.Outlined.Email,
                    badgeCount = unreadCount,
                    testTag = "nav_inbox",
                    onClick = { onNavigate(AppScreen.INBOX) }
                )

                // 4. ME Tab
                BottomNavItem(
                    label = "Me",
                    selected = currentScreen == AppScreen.PROFILE,
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person,
                    testTag = "nav_profile",
                    onClick = { onNavigate(AppScreen.PROFILE) }
                )
            }

            // Safe Area Inset Spacer
            if (bottomInset > 0.dp) {
                Spacer(modifier = Modifier.height(bottomInset))
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    label: String,
    selected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
    badgeCount: Int = 0
) {
    val activeColor = ElectricBlueBright
    val inactiveColor = TextSecondary

    val iconColor by animateColorAsState(
        targetValue = if (selected) activeColor else inactiveColor,
        animationSpec = tween(durationMillis = 200),
        label = "iconColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) activeColor else inactiveColor,
        animationSpec = tween(durationMillis = 200),
        label = "textColor"
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .height(58.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 28.dp),
                role = Role.Tab,
                onClick = onClick
            )
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BadgedBox(
                badge = {
                    if (badgeCount > 0) {
                        Badge(
                            containerColor = ElectricBlueBright,
                            contentColor = PureWhite,
                            modifier = Modifier.offset(x = 4.dp, y = (-2).dp)
                        ) {
                            Text(
                                text = if (badgeCount > 9) "9+" else badgeCount.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = if (selected) selectedIcon else unselectedIcon,
                    contentDescription = label,
                    modifier = Modifier.size(23.dp),
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = label,
                fontSize = 11.5.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = textColor,
                letterSpacing = 0.2.sp
            )
        }
    }
}

/**
 * Premium circular Floating Action Button for Sell action (~58dp).
 * Positioned separately from the 4 navigation tabs, elevated with subtle shadow.
 */
@Composable
fun SellFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .size(58.dp)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                ambientColor = ElectricBlueBright.copy(alpha = 0.4f),
                spotColor = ElectricBlueBright
            )
            .testTag("sell_fab_button"),
        shape = CircleShape,
        containerColor = ElectricBlueBright,
        contentColor = PureWhite,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Post to Campus Marketplace",
            modifier = Modifier.size(28.dp)
        )
    }
}
