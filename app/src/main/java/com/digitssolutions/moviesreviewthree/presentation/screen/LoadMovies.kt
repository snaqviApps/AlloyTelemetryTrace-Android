package com.digitssolutions.moviesreviewthree.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digitssolutions.moviesreviewthree.presentation.MoviesUIState
import com.digitssolutions.moviesreviewthree.presentation.viewmodel.MoviesViewModel
import com.digitssolutions.moviesreviewthree.ui.theme.MoviesReviewThreeTheme

@Composable
fun LoadMovies (
    innerPadding: PaddingValues,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val moviesUIState by viewModel.moviesUIState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = innerPadding.calculateTopPadding() + 12.dp),
                text = "This is Top Bar",
                textAlign = TextAlign.Center
            )
        },
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        bottomBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = innerPadding.calculateBottomPadding() + 12.dp),
                text = "This is Bottom Bar",
                textAlign = TextAlign.Center
            )
        },
        content = { rootInnerPadding ->
            when (val mState = moviesUIState) {
                is MoviesUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(width = 104.dp, height = 84.dp),
                            strokeWidth = 4.dp
                        )
                    }
                }
                is MoviesUIState.Error -> {
                    MovieErrorCard(
                        innerPadding = rootInnerPadding,
                        error= mState.error
                    )
                }
                is MoviesUIState.Success -> {
                    MovieCard(innerPadding = rootInnerPadding, movieList = mState.movie)
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun MoviesLandPreview() {
    MoviesReviewThreeTheme {
        LoadMovies(
            innerPadding = PaddingValues()
        )
    }
}