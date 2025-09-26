package com.example.cw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cw3.ui.theme.Cw3Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cw3Theme {
                MainScaffold()
            }
        }
    }
}


data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

// using ImageVector allows passing material icons.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    val navItems = listOf(
        NavItem("Home", Icons.Default.Home, "home"),
        NavItem("Settings", Icons.Default.Settings, "settings"),
        NavItem("Profile", Icons.Default.Person, "profile")
    )

    // use to show snackbars from a coroutine.
    var selectedItem by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val currentScreen = when (selectedItem) {
        0 -> "Home Screen"
        1 -> "Settings Screen"
        2 -> "Profile Screen"
        else -> "Unknown Screen"
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My App") },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        bottomBar = {
            //navigation container.
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                // floatingActionButton triggers a Snackbar when clicked.
                // use coroutineScope to call suspend show Snackbar.
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "FAB click",
                            actionLabel = "OK"
                        )
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentScreen,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    Cw3Theme {
        MainScaffold()
    }
}
