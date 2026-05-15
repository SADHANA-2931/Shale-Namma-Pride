@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.shalenamma3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.IconButton

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShaleNammaApp()
        }
    }
}

@Composable
fun ShaleNammaApp() {


    var currentScreen by remember {
        mutableStateOf("splash")
    }

    var registeredMobile by remember {
        mutableStateOf("")
    }

    var registeredPassword by remember {
        mutableStateOf("")
    }

    when (currentScreen) {
        "splash" -> SplashScreen(
            onFinish = {
                currentScreen = "login"
            }
        )



        "login" -> LoginScreen(


            onLoginSuccess = {
                currentScreen = "dashboard"
            },

            onSignup = {
                currentScreen = "signup"
            },

            savedMobile = registeredMobile,
            savedPassword = registeredPassword
        )

        "signup" -> SignupScreen(

            onCreateAccount = { mobile, password ->

                registeredMobile = mobile
                registeredPassword = password

                currentScreen = "login"
            },

            onBack = {
                currentScreen = "login"
            }
        )

        "dashboard" -> DashboardScreen(

            onGallery = {
                currentScreen = "gallery"
            },

            onUpdates = {
                currentScreen = "updates"
            },

            onProfile = {
                currentScreen = "profile"
            },
            onFeedback = {
                currentScreen = "feedback"
            }
        )
        "profile" -> ProfileScreen(

            mobile = registeredMobile,

            onLogout = {

                registeredMobile = ""
                registeredPassword = ""

                currentScreen = "login"
            },

            onBack = {
                currentScreen = "dashboard"
            }
        )

        "gallery" -> GalleryScreen(
            onBack = {
                currentScreen = "dashboard"
            }
        )

        "updates" -> UpdatesScreen(
            onBack = {
                currentScreen = "dashboard"
            }
        )

        "feedback" -> FeedbackScreen(
            onBack = {
                currentScreen = "dashboard"
            }
        )
    }
}

@Composable
fun SplashScreen(onFinish: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2500)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4338CA),
                        Color(0xFF6366F1)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {

                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "🏫",
                        fontSize = 70.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Shale Namma Pride",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Govt High School Portal",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp
            )
        }
    }
}
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignup: () -> Unit,
    savedMobile: String,
    savedPassword: String
) {

    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4C5CFF))
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(90.dp))

            Text(
                text = "\uD83C\uDFEB",
                fontSize = 70.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Shale Namma Pride",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Govt High School Portal",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                shape = RoundedCornerShape(
                    topStart = 34.dp,
                    topEnd = 34.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = mobile,
                        onValueChange = {
                            mobile = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Mobile Number")
                        }
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        modifier = Modifier.fillMaxWidth(),

                        visualTransformation =
                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null
                                )
                            }
                        },

                        label = {
                            Text("Password")
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Text(
                            text = "Forgot Password?",
                            color = Color(0xFF4C5CFF),
                            modifier = Modifier.clickable {

                                errorMessage =
                                    "Demo Password: $savedPassword"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (errorMessage.isNotEmpty()) {

                        Text(
                            text = errorMessage,
                            color = Color.Red
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Button(
                        onClick = {

                            if (
                                mobile.isNotEmpty() &&
                                password.isNotEmpty() &&
                                mobile == savedMobile &&
                                password == savedPassword
                            ){

                                errorMessage = ""
                                onLoginSuccess()

                            } else {

                                errorMessage =
                                    "Invalid mobile number or password"
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4C5CFF)
                        )

                    ) {

                        Text(
                            text = "Login",
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row {

                        Text("Don't have an account?")

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Sign Up",
                            color = Color(0xFF4C5CFF),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                onSignup()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))
                }
            }
        }
    }
}
@Composable
fun ProfileScreen(
    mobile: String,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Profile")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {

                        Text(
                            text = "←",
                            fontSize = 24.sp
                        )
                    }
                }
            )
        },

        containerColor = Color(0xFFF4F6FF)

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4C5CFF)
                )
            ) {

                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "👤",
                        fontSize = 60.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "School User",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = mobile,
                color = Color.Gray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Profile Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text("Role: Student")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("School: Govt High School")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("District: Chitradurga")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onLogout()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {

                Text(
                    text = "Logout",
                    fontSize = 18.sp
                )
            }
        }
    }
}
@Composable
fun SignupScreen(
    onCreateAccount: (String, String) -> Unit,
    onBack: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4C5CFF))
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),

                shape = RoundedCornerShape(30.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Create Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Full Name")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = mobile,
                        onValueChange = {
                            mobile = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Mobile Number")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },

                        visualTransformation =
                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null
                                )
                            }
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Password")
                        }
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {

                            if (
                                name.isNotEmpty() &&
                                mobile.isNotEmpty() &&
                                password.isNotEmpty()
                            ) {

                                onCreateAccount(
                                    mobile,
                                    password
                                )
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4C5CFF)
                        )
                    ) {

                        Text("Create Account")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Back to Login",
                        color = Color(0xFF4C5CFF),
                        modifier = Modifier.clickable {
                            onBack()
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun DashboardScreen(
    onGallery: () -> Unit,
    onUpdates: () -> Unit,
    onProfile: () -> Unit,
    onFeedback: () -> Unit
) {

    Scaffold(

        containerColor = Color(0xFFF4F6FF),

        bottomBar = {

            NavigationBar(
                tonalElevation = 10.dp,
                containerColor = Color.White
            ) {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Text("🏠", fontSize = 20.sp)
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onUpdates()
                    },
                    icon = {
                        Text("📢", fontSize = 20.sp)
                    },
                    label = {
                        Text("Updates")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onGallery()
                    },
                    icon = {
                        Text("🖼️", fontSize = 20.sp)
                    },
                    label = {
                        Text("Gallery")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onProfile()
                    },
                    icon = {
                        Text("👤", fontSize = 20.sp)
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF4C5CFF),
                                    Color(0xFF6C63FF)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {

                    Column {

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "🏫",
                            fontSize = 58.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Shale Namma Pride",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Government High School Portal",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.18f)
                            ),
                            shape = RoundedCornerShape(22.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column {

                                    Text(
                                        text = "Today's Attendance",
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "92%",
                                        color = Color.White,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    text = "📚",
                                    fontSize = 42.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    DashboardStatCard(
                        title = "Students",
                        value = "420+",
                        emoji = "👨‍🎓",
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    DashboardStatCard(
                        title = "Teachers",
                        value = "35+",
                        emoji = "👩‍🏫",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Today's Meal 🍱",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                MealUpdateCard()

                Spacer(modifier = Modifier.height(24.dp))


                QuickActionCard(
                    title = "Feedback",
                    emoji = "💬",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onFeedback
                )
                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Student Stars ⭐",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                StudentStarCard(
                    studentName = "Ramesh Kumar",
                    achievement = "Student of the Week",
                    emoji = "🏅"
                )

                StudentStarCard(
                    studentName = "Anjali",
                    achievement = "Sports Champion",
                    emoji = "🏆"
                )

                Spacer(modifier = Modifier.height(28.dp))


            }
        }
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    emoji: String,
    modifier: Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = emoji,
                fontSize = 34.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    emoji: String,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = emoji,
                fontSize = 36.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PremiumActivityCard(
    title: String,
    desc: String,
    emoji: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = desc,
                    color = Color.Gray
                )
            }
        }
    }
}
@Composable
fun UpdatesScreen(
    onBack: () -> Unit
) {

    Scaffold(

        containerColor = Color(0xFFF4F6FF),

        topBar = {

            TopAppBar(

                title = {
                    Text("Latest Updates")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {

                        Text(
                            text = "←",
                            fontSize = 24.sp
                        )
                    }
                }
            )
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            item {

                ModernUpdateCard(
                    title = "Science Exhibition",
                    desc = "Students showcased innovative science projects.",
                    emoji = "🧪"
                )

                ModernUpdateCard(
                    title = "Sports Day",
                    desc = "Annual sports competitions completed successfully.",
                    emoji = "🏆"
                )

                ModernUpdateCard(
                    title = "Tree Plantation",
                    desc = "Eco-friendly awareness activity conducted.",
                    emoji = "🌱"
                )

                ModernUpdateCard(
                    title = "Parent Meeting",
                    desc = "Parents discussed student academic progress.",
                    emoji = "👨‍👩‍👧"
                )
            }
        }
    }
}
@Composable
fun GalleryScreen(
    onBack: () -> Unit
) {

    val galleryItems = listOf(

        Pair("Science Fair", R.drawable.science),
        Pair("Sports Day", R.drawable.sports),
        Pair("Tree Plantation", R.drawable.tree),
        Pair("Parent Meeting", R.drawable.meeting),
        Pair("Cultural Event", R.drawable.cultural)
    )

    Scaffold(

        containerColor = Color(0xFFF4F6FF),

        topBar = {

            TopAppBar(

                title = {
                    Text("School Gallery")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {

                        Text(
                            text = "←",
                            fontSize = 24.sp
                        )
                    }
                }
            )
        }

    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {

            items(galleryItems) { item ->

                Card(
                    modifier = Modifier
                        .padding(8.dp),

                    shape = RoundedCornerShape(24.dp),

                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column {

                        Image(
                            painter = painterResource(id = item.second),
                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),

                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text = item.first,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "School Activity",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernUpdateCard(
    title: String,
    desc: String,
    emoji: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 42.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = desc,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StudentStarCard(
    studentName: String,
    achievement: String,
    emoji: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 42.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = studentName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = achievement,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun FeedbackScreen(
    onBack: () -> Unit
) {

    var feedback by remember {
        mutableStateOf("")
    }

    var anonymous by remember {
        mutableStateOf(false)
    }

    var submitted by remember {
        mutableStateOf(false)
    }

    val database = Firebase.database
    val feedbackRef = database.getReference("feedbacks")

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Feedback Box")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {

                        Text(
                            text = "←",
                            fontSize = 24.sp
                        )
                    }
                }
            )
        },

        containerColor = Color(0xFFF4F6FF)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Share your suggestions with the school committee.",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(

                value = feedback,

                onValueChange = {
                    feedback = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),

                label = {
                    Text("Write Feedback")
                },

                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = anonymous,

                    onCheckedChange = {
                        anonymous = it
                    }
                )

                Text("Submit as Anonymous")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(

                onClick = {

                    if (feedback.isNotEmpty()) {

                        val database = Firebase.database
                        val feedbackRef = database.getReference("feedbacks")

                        val feedbackData = mapOf(
                            "message" to feedback,
                            "anonymous" to anonymous
                        )

                        feedbackRef.push().setValue(feedbackData)

                        submitted = true
                        feedback = ""
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5B5FFF)
                )

            ) {

                Text(
                    text = "Submit Feedback",
                    fontSize = 18.sp
                )
            }

            if (submitted) {

                Spacer(modifier = Modifier.height(24.dp))

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDFF6DD)
                    ),

                    shape = RoundedCornerShape(18.dp)

                ) {

                    Text(

                        text =
                            if (anonymous)
                                "Anonymous feedback submitted successfully."
                            else
                                "Feedback submitted successfully.",

                        modifier = Modifier.padding(16.dp),

                        color = Color(0xFF2E7D32)
                    )
                }


            }
        }
    }
}


@Composable
fun MealUpdateCard() {

    var languageEnglish by remember {
        mutableStateOf(true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "🍛",
                    fontSize = 42.sp
                )

                Button(
                    onClick = {
                        languageEnglish = !languageEnglish
                    },
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Text(
                        text =
                            if (languageEnglish)
                                "ಕನ್ನಡ"
                            else
                                "English"
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text =
                    if (languageEnglish)
                        "Today's Mid-Day Meal"
                    else
                        "ಇಂದಿನ ಮಧ್ಯಾಹ್ನದ ಊಟ",

                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text =
                    if (languageEnglish)
                        "Rice, Sambar, Egg and Banana served today."
                    else
                        "ಇಂದು ಅನ್ನ, ಸಾಂಬಾರ್, ಮೊಟ್ಟೆ ಮತ್ತು ಬಾಳೆಹಣ್ಣು ನೀಡಲಾಗಿದೆ.",

                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text =
                    if (languageEnglish)
                        "Only one meal update allowed per day."
                    else
                        "ಒಂದು ದಿನಕ್ಕೆ ಒಂದೇ ಊಟದ ನವೀಕರಣ ಅನುಮತಿಸಲಾಗಿದೆ.",

                color = Color(0xFF4C5CFF),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}