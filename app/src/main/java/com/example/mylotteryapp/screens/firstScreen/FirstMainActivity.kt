package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstMainActivity(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    var isListAtEnd by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        topBar = { TopBar(realmViewModel) },
        floatingActionButton = {
            FAB(scannerViewModel = scannerViewModel, realmViewModel = realmViewModel, isListAtEnd = isListAtEnd)
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {

        ListBoletos(
            realmViewModel = realmViewModel,
            paddingValues = it,
            onListEndChange = { newValue -> // Callback desde ListBoletos
                isListAtEnd = newValue
            }
            )

    }
}






