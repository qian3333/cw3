package com.example.cw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cw3.ui.theme.Cw3Theme

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
                    LoginForm(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginForm(modifier: Modifier = Modifier) {
    // use remember to persist across process death/config changes.
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var submissionStatus by remember { mutableStateOf("") }

    fun validateFields(): Boolean {
        // mark a field as error if it is empty.

        usernameError = username.isEmpty()
        passwordError = password.isEmpty()
        return !usernameError && !passwordError
    }

    fun handleSubmit() {
        if (validateFields()) {
            submissionStatus = "login submitted"
        } else {
            submissionStatus = ""
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Account Login",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            // outlinedTextField is a Material component that shows an outlined input box.
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (usernameError) usernameError = it.isEmpty()
                },
                label = { Text("Username") },
                isError = usernameError,
                supportingText = {
                    if (usernameError) {
                        Text("username cannot be empty")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError) passwordError = it.isEmpty()
                },
                label = { Text("Password") },
                isError = passwordError,
                supportingText = {
                    if (passwordError) {
                        Text("password cannot be empty")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = ::handleSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    "Sign In",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
            // show submissionStatus when not empty
            if (submissionStatus.isNotEmpty()) {
                Text(
                    text = submissionStatus,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    Cw3Theme {
        LoginForm()
    }
}

