package com.example.mylotteryapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mylotteryapp.screens.boletosListScreen.FAB
import com.example.mylotteryapp.screens.homeScreen.HomeScreen
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstMainActivity(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

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


}

