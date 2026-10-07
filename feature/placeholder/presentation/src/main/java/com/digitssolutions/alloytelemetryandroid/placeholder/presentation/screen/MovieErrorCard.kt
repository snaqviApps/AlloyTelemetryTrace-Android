package com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MovieErrorCard(
    innerPadding: PaddingValues,
    error: String
) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = innerPadding.calculateTopPadding() + 24.dp,
                start =  innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 12.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 24.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                            )
                    ) {
                        append("Error:\n\n")
                    }
                    if(error.contains("api.themoviedb.org")) {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )) {
                            append("remote Host is not reachable")
                        }
                    }
                    else {
                        append(error)
                    }
                },
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }

    }
}