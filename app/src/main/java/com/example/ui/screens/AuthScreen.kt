package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Campus
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueBright
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class AuthStep {
    LANDING,
    COLLEGE_EMAIL,
    VERIFY_OTP,
    COMPLETE_PROFILE
}

@Composable
fun AuthScreen(
    campuses: List<Campus>,
    onCompleteAuth: (name: String, email: String, university: String, campus: Campus, hostel: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(AuthStep.LANDING) }
    var emailInput by remember { mutableStateOf("hari.pandey@lpu.in") }
    var otpInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("Hari Pandey") }
    var selectedCampus by remember { mutableStateOf(campuses.firstOrNull() ?: Campus("lpu", "LPU", "Lovely Professional University", "Phagwara", "Punjab")) }
    var hostelInput by remember { mutableStateOf("BH-4 Hostel") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = DarkBg
    ) {
        AnimatedContent(
            targetState = step,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "auth_step"
        ) { currentStep ->
            when (currentStep) {
                AuthStep.LANDING -> {
                    LandingAuthStep(
                        onContinueGoogle = {
                            // Pre-fill demo college email and go to campus selection / completion
                            emailInput = "hari.pandey@lpu.in"
                            step = AuthStep.COMPLETE_PROFILE
                        },
                        onContinueEmail = {
                            step = AuthStep.COLLEGE_EMAIL
                        }
                    )
                }

                AuthStep.COLLEGE_EMAIL -> {
                    EmailVerificationStep(
                        email = emailInput,
                        onEmailChange = {
                            emailInput = it
                            errorMessage = null
                        },
                        errorMessage = errorMessage,
                        onBack = { step = AuthStep.LANDING },
                        onSendOtp = {
                            if (emailInput.contains("@") && emailInput.contains(".")) {
                                errorMessage = null
                                step = AuthStep.VERIFY_OTP
                            } else {
                                errorMessage = "Please enter a valid college or institutional email."
                            }
                        }
                    )
                }

                AuthStep.VERIFY_OTP -> {
                    OtpVerificationStep(
                        email = emailInput,
                        otp = otpInput,
                        onOtpChange = { otpInput = it },
                        onBack = { step = AuthStep.COLLEGE_EMAIL },
                        onVerify = {
                            step = AuthStep.COMPLETE_PROFILE
                        }
                    )
                }

                AuthStep.COMPLETE_PROFILE -> {
                    CompleteProfileStep(
                        name = nameInput,
                        onNameChange = { nameInput = it },
                        email = emailInput,
                        campuses = campuses,
                        selectedCampus = selectedCampus,
                        onSelectCampus = { selectedCampus = it },
                        hostel = hostelInput,
                        onHostelChange = { hostelInput = it },
                        onFinish = {
                            onCompleteAuth(
                                nameInput.ifBlank { "Campus Student" },
                                emailInput,
                                selectedCampus.name,
                                selectedCampus,
                                hostelInput.ifBlank { "Main Campus" }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LandingAuthStep(
    onContinueGoogle: () -> Unit,
    onContinueEmail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Branding
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = ElectricBlueBright,
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "XO",
                        color = PureWhite,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ExOwn",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = TextPrimary
            )

            Text(
                text = "Exchange. Own. Repeat.",
                style = MaterialTheme.typography.titleMedium,
                color = ElectricBlueBright,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "The trusted peer marketplace for your university campus.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // Value Proposition Indicators
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            border = BorderStroke(1.dp, DarkBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TrustFeatureRow(
                    text = "Verified student community — no random outsiders"
                )
                TrustFeatureRow(
                    text = "Zero shipping fees — meet safely on campus"
                )
                TrustFeatureRow(
                    text = "Buy, sell, rent, exchange & post wanted items"
                )
            }
        }

        // Actions
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onContinueGoogle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("auth_google_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
                Text(
                    text = "Continue with Google",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }

            OutlinedButton(
                onClick = onContinueEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("auth_email_button"),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, DarkBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Continue with Student Email",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }

            Text(
                text = "By signing in, you agree to verified campus guidelines and safe trade protocols.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun EmailVerificationStep(
    email: String,
    onEmailChange: (String) -> Unit,
    errorMessage: String?,
    onBack: () -> Unit,
    onSendOtp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }

            Text(
                text = "Verify Student Identity",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Enter your university or college email to verify student status and unlock your campus marketplace.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("College Email Address") },
                placeholder = { Text("your.name@lpu.in") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = ElectricBlueBright)
                },
                singleLine = true,
                isError = errorMessage != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_email_input"),
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

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSurfaceCard,
                border = BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ExOwnEmerald,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Supported domains include @lpu.in, @thapar.edu, @iitb.ac.in, @cu.edu.in and all accredited universities.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        Button(
            onClick = onSendOtp,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("auth_send_otp_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
        ) {
            Text("Send Verification OTP", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun OtpVerificationStep(
    email: String,
    otp: String,
    onOtpChange: (String) -> Unit,
    onBack: () -> Unit,
    onVerify: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }

            Text(
                text = "Enter Verification Code",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "We sent a 4-digit code to $email",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = { if (it.length <= 4) onOtpChange(it) },
                label = { Text("4-Digit OTP") },
                placeholder = { Text("• • • •") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = ElectricBlueBright)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_otp_input"),
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tip: Enter any 4 digits (e.g. 1234) for student test sandbox verification.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }

        Button(
            onClick = onVerify,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("auth_verify_otp_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
        ) {
            Text("Verify & Continue", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CompleteProfileStep(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    campuses: List<Campus>,
    selectedCampus: Campus,
    onSelectCampus: (Campus) -> Unit,
    hostel: String,
    onHostelChange: (String) -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Complete Your Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Set your campus identity. Your student email is confirmed.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Student Name
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Full Name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = ElectricBlueBright)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_name_input"),
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

            Spacer(modifier = Modifier.height(14.dp))

            // University / Campus Choice
            Text(
                text = "SELECT YOUR CAMPUS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                campuses.take(3).forEach { campus ->
                    val isSelected = campus.id == selectedCampus.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectCampus(campus) }
                            .testTag("campus_option_${campus.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) ElectricBlue.copy(alpha = 0.15f) else DarkSurfaceCard
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) ElectricBlueBright else DarkBorder
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${campus.code} — ${campus.name}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isSelected) ElectricBlueBright else TextPrimary
                                )
                                Text(
                                    text = "${campus.city}, ${campus.state}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = ElectricBlueBright,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Hostel / Area (Optional)
            OutlinedTextField(
                value = hostel,
                onValueChange = onHostelChange,
                label = { Text("Hostel Block / PG Area (Optional)") },
                placeholder = { Text("e.g. BH-4, Law Gate, North Hostel") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = TextMuted)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_hostel_input"),
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

        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("auth_finish_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
        ) {
            Text("Enter Campus Marketplace", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun TrustFeatureRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = ExOwnEmerald,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )
    }
}
