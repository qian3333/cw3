package com.example.cw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    ContactListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactListScreen(modifier: Modifier = Modifier) {
    // generate contacts information and sort by their first name
    val contacts = remember { generateContacts(50) }
    val groupedContacts by remember(contacts) {
        mutableStateOf(
            contacts.groupBy { it.name.firstOrNull()?.uppercaseChar() ?: '#' }
                .toSortedMap()
        )
    }
    // Coroutine scope for scroll animations
    val listState = rememberLazyListState()
    var showScrollToTop by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // show the scroll back button under some situations
    LaunchedEffect(listState.firstVisibleItemIndex) {
        showScrollToTop = listState.firstVisibleItemIndex > 10
    }

    fun scrollToTop() = scope.launch {
        listState.animateScrollToItem(0)
    }

    Box(modifier = modifier.fillMaxSize()) {
        ContactsList(
            groups = groupedContacts,
            listState = listState
        )

        if (showScrollToTop) {
            FloatingActionButton(
                onClick = { scrollToTop() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
// listen and control the scrolable state
fun ContactsList(groups: Map<Char, List<Contact>>, listState: androidx.compose.foundation.lazy.LazyListState, modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        groups.forEach { (letter, contacts) ->
            // create a sticktheader(like contact in the phone)
            stickyHeader {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { heading() },
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = letter.toString(),
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(
                items = contacts,
                // the key to find a contactor is the name
                key = { it.name }
            ) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "${contact.name} ",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

data class Contact(
    val name: String,
    val phone: String
)
//generate the contact numbers and names
fun generateContacts(count: Int = 50): List<Contact> {
    val firstNames = listOf(
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Elijah", "Sophia", "Oliver",
        "Isabella", "William", "Charlotte", "Benjamin", "Mia", "Lucas", "Amelia",
        "Henry", "Harper", "Alexander", "Evelyn", "Matthew", "Abigail", "Theodore",
        "Elizabeth", "James", "Sofia", "Robert", "Avery", "Michael", "Ella", "David"
    )
    val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
        "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez",
        "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    )

    return (1..count).map {
        val fullName = "${firstNames.random()} ${lastNames.random()}"
        val phone = "13${(10000000..99999999).random()}"
        Contact(fullName, phone)
    }.distinctBy { it.name }
        .sortedBy { it.name }
}

@Preview(showBackground = true)
@Composable
fun ContactListPreview() {
    Cw3Theme {

        val demo = listOf(
            Contact("Alice Brown", "13800000001"),
            Contact("Alice Cooper", "13800000002"),
            Contact("Bob Dylan", "13800000003"),
            Contact("Charlie Evans", "13800000004"),
            Contact("David Ford", "13800000005")
        )
        // group demo contacts for preview
        val grouped = remember(demo) {
            demo.groupBy { it.name.first().uppercaseChar() }.toSortedMap()
        }
        val listState = rememberLazyListState()
        ContactsList(groups = grouped, listState = listState)
    }
}