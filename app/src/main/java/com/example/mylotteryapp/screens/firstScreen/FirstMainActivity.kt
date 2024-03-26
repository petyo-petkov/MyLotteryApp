package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        pageCount = { 2 },
        initialPageOffsetFraction = 0f
    )
    HorizontalPager(state = pagerState) { page ->

            when(page){

                0 -> {
                    val boletos = realmViewModel.boletos
                    Scaffold(
                        modifier = Modifier,
                        topBar = { TopBar(
                            realmViewModel = realmViewModel,
                            boletos = boletos
                        ) },
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
                            boletos = boletos
                        )

                    }
                }

                1 -> {
                    val boletos = realmViewModel.boletosEnRangoDeFechas
                    Scaffold(
                        modifier = Modifier,
                        topBar = { TopBar(
                            realmViewModel = realmViewModel,
                            boletos = boletos

                        ) },
                        floatingActionButton = { FabReturn(pagerState, realmViewModel) },
                        floatingActionButtonPosition = FabPosition.End

                    ) {

                        ListBoletos(
                            realmViewModel = realmViewModel,
                            paddingValues = it,
                            boletos = boletos
                        )

                    }

                }
                else -> error("No se ha definido una página para el índice $page")

            }

    }
}
