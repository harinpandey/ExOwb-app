package com.example.ui.auth

import android.app.Activity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import com.example.R
import com.example.ui.theme.ExOwnBlue
import com.example.ui.theme.ExOwnBlueContainer
import com.example.ui.theme.ExOwnEmerald
import com.example.ui.theme.ExOwnNavy
import com.example.ui.theme.ExOwnSlate
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    campuses: List<String>,
    selectedCampus: String,
    onCampusSelect: (String) -> Unit,
    onAuthSuccess: (String, String, String, String, String) -> Unit, // name, email, university, campus, hostel
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showGuestDialog by remember { mutableStateOf(false) }
    var expandedCampusDropdown by remember { mutableStateOf(false) }

    // Student form fields for onboarding / quick login
    var studentName by remember { mutableStateOf("Aarav Patel") }
    var studentEmail by remember { mutableStateOf("aarav.p@campus.edu") }
    var hostelBlock by remember { mutableStateOf("BH-4, Room 312") }

    // Attempt silent auto-sign in on screen mount
    LaunchedEffect(Unit) {
        AuthManager.attemptAutoSignIn(
            context = context,
            credentialManager = credentialManager,
            onAuthSuccess = { firebaseUser ->
                onAuthSuccess(
                    firebaseUser.displayName ?: "Student User",
                    firebaseUser.email ?: "student@campus.edu",
                    "Apex Institute of Technology",
                    selectedCampus,
                    hostelBlock
                )
            },
            onUnauthenticated = { /* stay on auth screen */ },
            scope = coroutineScope
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // ExOwn Brand Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Ex",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = ExOwnBlue
            )
            Text(
                text = "Own",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
                color = ExOwnEmerald.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        tint = ExOwnEmerald,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Surface(
            color = ExOwnEmerald,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "EXCHANGE. OWN. REPEAT.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Hero illustration
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = ExOwnNavy),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.hero_campus_banner),
                    contentDescription = "ExOwn Campus",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(
                            text = "Campus Student Marketplace",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Connect with students to buy, sell, rent, and swap campus goods.",
                            color = Color(0xFFE2E8F0),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Campus Selector Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "SELECT YOUR CAMPUS:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ExOwnSlate
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedCampusDropdown = true }
                            .border(1.dp, ExOwnBlue.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = ExOwnBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = selectedCampus,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select",
                            tint = ExOwnSlate
                        )
                    }

                    DropdownMenu(
                        expanded = expandedCampusDropdown,
                        onDismissRequest = { expandedCampusDropdown = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        campuses.forEach { campus ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = campus,
                                        fontWeight = if (campus == selectedCampus) FontWeight.Bold else FontWeight.Normal,
                                        color = if (campus == selectedCampus) ExOwnBlue else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    onCampusSelect(campus)
                                    expandedCampusDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (errorMessage != null) {
            Surface(
                color = Color(0xFFFEE2E2),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        fontSize = 12.sp,
                        color = Color(0xFFDC2626)
                    )
                }
            }
        }

        // Primary Action: Google Sign-In with Credential Manager
        Button(
            onClick = {
                isLoading = true
                errorMessage = null
                AuthManager.onGoogleSignInClicked(
                    context = context,
                    credentialManager = credentialManager,
                    onAuthSuccess = { firebaseUser ->
                        isLoading = false
                        onAuthSuccess(
                            firebaseUser.displayName ?: studentName,
                            firebaseUser.email ?: studentEmail,
                            "Apex Institute of Technology",
                            selectedCampus,
                            hostelBlock
                        )
                    },
                    onAuthError = { errorMsg ->
                        isLoading = false
                        errorMessage = errorMsg
                    },
                    scope = coroutineScope,
                    onAuthCancelled = {
                        isLoading = false
                    }
                )
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = ExOwnNavy),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("google_sign_in_btn")
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = Color.White,
                    strokeWidth = 2.5.dp
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Google stylized G or Icon
                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(22.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "G",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = ExOwnBlue
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sign in with Google",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Secondary Action: Continue with Campus Student Profile
        OutlinedButton(
            onClick = { showGuestDialog = true },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("student_profile_login_btn")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = ExOwnBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign in with Student Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ExOwnBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Trust features
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TrustPill(icon = Icons.Default.CheckCircle, text = "Verified Student ID")
            TrustPill(icon = Icons.Default.Security, text = "Safe Campus Handover")
            TrustPill(icon = Icons.Default.SwapHoriz, text = "Zero Brokerage")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "By signing in, you agree to the ExOwn Student Marketplace Honor Code and Campus Community Guidelines.",
            fontSize = 11.sp,
            color = ExOwnSlate,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
        )
    }

    // Student Profile Dialog for Quick or Local Campus Access
    if (showGuestDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showGuestDialog = false },
            title = {
                Text(
                    text = "Student Profile Verification",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Set up your student profile to browse, chat, sell, and rent on $selectedCampus.",
                        fontSize = 12.sp,
                        color = ExOwnSlate
                    )

                    OutlinedTextField(
                        value = studentName,
                        onValueChange = { studentName = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = studentEmail,
                        onValueChange = { studentEmail = it },
                        label = { Text("College Email (@campus.edu)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = hostelBlock,
                        onValueChange = { hostelBlock = it },
                        label = { Text("Hostel / PG Location") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showGuestDialog = false
                        onAuthSuccess(
                            studentName.ifBlank { "Aarav Patel" },
                            studentEmail.ifBlank { "aarav.p@campus.edu" },
                            "Apex Institute of Technology",
                            selectedCampus,
                            hostelBlock.ifBlank { "BH-4, Block A" }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ExOwnBlue)
                ) {
                    Text("Start ExOwn Session")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGuestDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TrustPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Surface(
            color = Color(0xFFEFF6FF),
            shape = CircleShape,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ExOwnBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ExOwnNavy,
            textAlign = TextAlign.Center
        )
    }
}
