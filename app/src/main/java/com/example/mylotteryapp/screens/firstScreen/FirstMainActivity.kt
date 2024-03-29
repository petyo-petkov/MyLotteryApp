package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mylotteryapp.screens.pantallaPrincipal.PrincipalScreen
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstMainActivity(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPageOffsetFraction = 0f
    )
    HorizontalPager(state = pagerState) { page ->
/*
        Scaffold(
            modifier = Modifier,
            topBar = {
                val boletosToShow = when (page) {
                    0 -> realmViewModel.boletos
                    else -> realmViewModel.boletosEnRangoDeFechas
                }
                TopBar(realmViewModel = realmViewModel, boletos = boletosToShow)
            },
            floatingActionButton = {
                when (page) {
                    0 -> FAB(
                        scannerViewModel = scannerViewModel,
                        realmViewModel = realmViewModel,
                        pagerState = pagerState
                    )

                    else -> FabReturn(pagerState, realmViewModel)
                }

            },
            floatingActionButtonPosition =
            when (page) {
                0 -> FabPosition.Center
                else -> FabPosition.End
            }

        ) {

            ListBoletos(
                realmViewModel = realmViewModel,
                paddingValues = it,
                boletos = when (page) {
                    0 -> realmViewModel.boletos
                    else -> realmViewModel.boletosEnRangoDeFechas
                }

            )

        }

 */
        when (page) {

            0 -> {
                PrincipalScreen(scannerViewModel, realmViewModel)
            }

            1 -> {
                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        TopBar(
                            realmViewModel = realmViewModel,
                            boletos = realmViewModel.boletos
                        )
                    },
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
                        boletos = realmViewModel.boletos

                    )

                }
            }

            2 -> {
                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        TopBar(
                            realmViewModel = realmViewModel,
                            boletos = realmViewModel.boletosEnRangoDeFechas
                        )
                    },
                    floatingActionButton = {
                        FabReturn(pagerState = pagerState, realmViewModel = realmViewModel)
                    },
                    floatingActionButtonPosition = FabPosition.End

                ) {

                    ListBoletos(
                        realmViewModel = realmViewModel,
                        paddingValues = it,
                        boletos = realmViewModel.boletosEnRangoDeFechas

                    )

                }
            }
        }


    }
}

