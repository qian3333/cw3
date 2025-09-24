package com.example.cw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cw3.ui.theme.Cw3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cw3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 25% 宽度的部分
        Box(
            modifier = Modifier
                .weight(1f) // 1份，总共4份(1+3)，即25%
                .fillMaxHeight()
                .background(Color(0xFFE3F2FD)), // 浅蓝色
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "25% Width",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }

        // 75% 宽度的部分
        Column(
            modifier = Modifier
                .weight(3f) // 3份，总共4份，即75%
                .fillMaxHeight()
                .padding(start = 16.dp)
        ) {
            // 2:3:5 比例的子项
            Box(
                modifier = Modifier
                    .weight(2f) // 2份
                    .fillMaxSize()
                    .background(Color(0xFFE8F5E9)), // 浅绿色
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2/10",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Box(
                modifier = Modifier
                    .weight(3f) // 3份
                    .fillMaxSize()
                    .background(Color(0xFFFFF8E1)), // 浅黄色
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "3/10",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Box(
                modifier = Modifier
                    .weight(5f) // 5份
                    .fillMaxSize()
                    .background(Color(0xFFFCE4EC)), // 浅粉色
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "5/10",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Cw3Theme {
        MainScreen()
    }
}