package com.mjapa21.midtermapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mjapa21.designsystem.components.CategoryChip
import com.mjapa21.designsystem.components.InfoBox
import com.mjapa21.designsystem.components.SectionHeader
import com.mjapa21.theme.MidtermAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MidtermAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        SectionHeader(
            title = "Sample Section",
            iconRes = com.mjapa21.designsystem.R.drawable.ic_arrow_next,
            onClick = { /* Handle click */ }
        )
        InfoBox(
            imageUrl = "https://www.themealdb.com/images/media/meals/bza0g51782856541.jpg",
            title = "Sample Title",
            description = "This is a sample description for the InfoBox component.",
        )

        CategoryChip(
            category = "Sample Beef Category",
            imageUrl = "https://www.themealdb.com/images/category/beef.png",
            onClick = { /* Handle click */ }
        )
        CategoryChip(
            category = "Sample Beef Category",
            onClick = { /* Handle click */ }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MidtermAppTheme {
        Greeting("Android")
    }
}