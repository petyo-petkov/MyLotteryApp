package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstMainActivity(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPageOffsetFraction = 0f
    )
    HorizontalPager(state = pagerState) { page ->

        when (page) {
            0 -> Page1(realmViewModel = realmViewModel, scannerViewModel = scannerViewModel, pagerState)
            1 -> Page2(realmViewModel = realmViewModel, pagerState)
            else -> error("No se ha definido una página para el índice $page")
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Page1(realmViewModel: RealmViewModel, scannerViewModel: ScannerViewModel, pagerState: PagerState) {
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = {
            FAB(
                scannerViewModel = scannerViewModel,
                realmViewModel = realmViewModel,
                pagerState = pagerState
            )
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Page2(realmViewModel: RealmViewModel, pagerState: PagerState) {
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = { FabReturn(pagerState, realmViewModel)},
        floatingActionButtonPosition = FabPosition.End

        ) {
        val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
        val listState = rememberLazyListState()
        val boletos = realmViewModel.boletosEnRangoDeFechas

        LazyColumn(
            modifier = Modifier.padding(paddingValues = it),
            state = listState,

            ) {
            items(boletos, key = { it.numeroSerie }) { boleto ->

                ItemBoleto(boleto = boleto, formatter = formatter, realmViewModel = realmViewModel)

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    thickness = 0.5.dp,
                    color = Color.Black
                )
            }
        }
    }

}



/*
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = {
            FAB(
                scannerViewModel = scannerViewModel,
                realmViewModel = realmViewModel,
            )
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
        )

    }

 */


/*
@Composable
fun Page1(realmViewModel: RealmViewModel, scannerViewModel: ScannerViewModel) {
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = {
            FAB(
                scannerViewModel = scannerViewModel,
                realmViewModel = realmViewModel,
                pagerState = pagerState
            )
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
        )

    }
}

@Composable
fun Page2(realmViewModel: RealmViewModel) {
    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },

        ) {
        val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
        val listState = rememberLazyListState()
        val boletos = realmViewModel.boletosEnRangoDeFechas

        LazyColumn(
            modifier = Modifier.padding(paddingValues = it),
            state = listState,

        ) {
            items(boletos, key = { it.numeroSerie }) { boleto ->

                ItemBoleto(boleto = boleto, formatter = formatter, realmViewModel = realmViewModel)

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    thickness = 0.5.dp,
                    color = Color.Black
                )
            }
        }
    }

}

 */




