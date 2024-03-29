package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.example.mylotteryapp.viewModels.RealmViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FabReturn(
    pagerState: PagerState,
    realmViewModel: RealmViewModel
) {

    val coroutine = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            coroutine.launch {
                pagerState.animateScrollToPage(
                    page = 0,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            realmViewModel.boletosEnRangoDeFechas = emptyList()

        }
        , containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
    }
}