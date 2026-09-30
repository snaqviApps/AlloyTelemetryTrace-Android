package com.digitssolutions.moviesreviewthree.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.digitssolutions.moviesreviewthree.domain.model.Movie

@Composable
fun MovieCard(
    innerPadding: PaddingValues,
    movieList: List<Movie>?
) {
    movieList?.let {
        Card(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 12.dp,
                    start =  innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = innerPadding.calculateBottomPadding() + 20.dp
                )
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            LazyColumn() {
                items(movieList) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(2.dp)
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                    ) {
                        Text("${it.title}")
                        Spacer(Modifier.height(4.dp))
                        Text(modifier = Modifier.background(Color.Red),
                            text = "Language: ${it.originalLanguage}"
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Overview:\n${it.overview}")
                        Spacer(Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}

