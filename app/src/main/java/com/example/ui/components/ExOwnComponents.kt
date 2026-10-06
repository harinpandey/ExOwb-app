package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnDarkBorder
import com.example.ui.theme.ExOwnDarkSurface1
import com.example.ui.theme.ExOwnDarkSurface2
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnTextMuted
import com.example.ui.theme.ExOwnTextPrimary
import com.example.ui.theme.ExOwnTextSecondary
import com.example.ui.theme.ExOwnUrgent

enum class ExOwnButtonVariant {
    PRIMARY,
    SECONDARY,
    OUTLINE,
    DANGER
}

@Composable
fun ExOwnButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    variant: ExOwnButtonVariant = ExOwnButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null
) {
    val shape = RoundedCornerShape(10.dp)

    when (variant) {
        ExOwnButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExOwnBlue,
                    disabledContainerColor = ExOwnBlue.copy(alpha = 0.5f)
                ),
                shape = shape,
                modifier = modifier.height(48.dp)
            ) {
                ButtonContent(text, isLoading, Color.White, leadingIcon)
            }
        }

        ExOwnButtonVariant.SECONDARY -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExOwnDarkSurface2,
                    contentColor = ExOwnTextPrimary
                ),
                shape = shape,
                border = BorderStroke(1.dp, ExOwnDarkBorder),
                modifier = modifier.height(48.dp)
            ) {
                ButtonContent(text, isLoading, ExOwnTextPrimary, leadingIcon)
            }
        }

        ExOwnButtonVariant.OUTLINE -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = shape,
                border = BorderStroke(1.dp, ExOwnBlue.copy(alpha = 0.8f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ExOwnBlue
                ),
                modifier = modifier.height(48.dp)
            ) {
                ButtonContent(text, isLoading, ExOwnBlue, leadingIcon)
            }
        }

        ExOwnButtonVariant.DANGER -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExOwnUrgent,
                    contentColor = Color.White
                ),
                shape = shape,
                modifier = modifier.height(48.dp)
            ) {
                ButtonContent(text, isLoading, Color.White, leadingIcon)
            }
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    isLoading: Boolean,
    textColor: Color,
    leadingIcon: ImageVector?
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = textColor,
            strokeWidth = 2.dp
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun VerificationBadge(
    modifier: Modifier = Modifier,
    label: String = "Verified"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = "Verified student",
            tint = ExOwnEmerald,
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ExOwnEmerald
        )
    }
}

@Composable
fun PriceDisplay(
    price: Double,
    modifier: Modifier = Modifier,
    originalPrice: Double? = null,
    rentalUnit: String? = null,
    fontSize: TextUnit = 18.sp
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        Text(
            text = "₹${price.toInt()}",
            fontSize = fontSize,
            fontWeight = FontWeight.Black,
            color = ExOwnBlue
        )
        if (!rentalUnit.isNullOrBlank()) {
            Text(
                text = " /$rentalUnit",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = ExOwnTextSecondary,
                modifier = Modifier.padding(start = 2.dp, bottom = 1.dp)
            )
        } else if (originalPrice != null && originalPrice > price) {
            Text(
                text = "₹${originalPrice.toInt()}",
                fontSize = 11.sp,
                color = ExOwnTextMuted,
                textDecoration = TextDecoration.LineThrough,
                modifier = Modifier.padding(start = 6.dp, bottom = 1.dp)
            )
        }
    }
}

@Composable
fun ExOwnTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { if (placeholder.isNotBlank()) Text(placeholder, color = ExOwnTextMuted) },
            singleLine = singleLine,
            isError = isError,
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (isError) ExOwnUrgent else ExOwnTextSecondary
                    )
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = ExOwnDarkSurface2,
                unfocusedContainerColor = ExOwnDarkSurface2,
                focusedBorderColor = ExOwnBlue,
                unfocusedBorderColor = ExOwnDarkBorder,
                errorBorderColor = ExOwnUrgent,
                focusedTextColor = ExOwnTextPrimary,
                unfocusedTextColor = ExOwnTextPrimary,
                focusedLabelColor = ExOwnBlue,
                unfocusedLabelColor = ExOwnTextSecondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        if (isError && !errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                fontSize = 11.sp,
                color = ExOwnUrgent,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Info,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = ExOwnDarkSurface2,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, ExOwnDarkBorder),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ExOwnTextMuted,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ExOwnTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = message,
            fontSize = 12.sp,
            color = ExOwnTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )

        if (actionText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(16.dp))
            ExOwnButton(
                text = actionText,
                onClick = onActionClick,
                variant = ExOwnButtonVariant.OUTLINE
            )
        }
    }
}

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) ExOwnDarkSurface2 else ExOwnDarkSurface1,
        border = BorderStroke(1.dp, if (isSelected) ExOwnBlue else ExOwnDarkBorder),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    tint = if (isSelected) ExOwnBlue else ExOwnTextSecondary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ExOwnBlue else ExOwnTextPrimary
            )
        }
    }
}
