package com.digitssolutions.moviesreviewthree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitssolutions.moviesreviewthree.presentation.screen.LoadMovies
import com.digitssolutions.moviesreviewthree.ui.theme.MoviesReviewThreeTheme
import dagger.hilt.android.AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesReviewThreeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
//                        .padding(12.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = MaterialTheme.shapes.small
                    ),
                    ) { innerPadding ->
                    MoviesLand(innerPadding)
                }
            }
        }
    }
}

@Composable
fun MoviesLand(innerPadding: PaddingValues) {
    LoadMovies(innerPadding)
}



@Preview(showBackground = true)
@Composable
fun MoviesLandPreview() {
    MoviesReviewThreeTheme {
        MoviesLand(
            innerPadding = PaddingValues()
        )
    }
}