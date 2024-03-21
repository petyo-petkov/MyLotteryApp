package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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

    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = {
            FAB(scannerViewModel = scannerViewModel, realmViewModel = realmViewModel)
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it
        )
    }
}






