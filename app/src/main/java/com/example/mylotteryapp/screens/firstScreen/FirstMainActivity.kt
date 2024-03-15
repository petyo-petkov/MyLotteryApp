package com.example.mylotteryapp.screens.firstScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstMainActivity(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    val boletos by realmViewModel.boletos.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = { TopBar() },
        bottomBar = { BottomBar(scannerViewModel) },

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            ListBoletosItem(
                boletos = boletos,
                formatter = formatter,
                realmViewModel = realmViewModel,
                paddingValues = it,
            )
        }

    }
}
