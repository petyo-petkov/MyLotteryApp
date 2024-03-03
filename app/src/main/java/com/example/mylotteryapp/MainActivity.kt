package com.example.mylotteryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.models.Boletos
import com.example.mylotteryapp.presentation.viewModelFactory
import com.example.mylotteryapp.ui.theme.MyLotteryAppTheme
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLotteryAppTheme {
                val scannerViewModel = viewModel<ScannerViewModel>(
                    factory = viewModelFactory {
                        ScannerViewModel(MyApp.appModule.scannerRepository, MyApp.appModule.realm)
                    }
                )
                val realmViewModel = viewModel<RealmViewModel>(
                    factory = viewModelFactory {
                        RealmViewModel(MyApp.appModule.realm)
                    }
                )

                App(realmViewModel, scannerViewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    realmViewModel: RealmViewModel,
    scannerViewModel: ScannerViewModel
) {

    val boletos by realmViewModel.primitiva.collectAsState()
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }


    Scaffold(
        modifier = Modifier,
        floatingActionButton = { FAB(scannerViewModel = scannerViewModel) },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(boletos) { boleto ->

                val date = Date(boleto.fecha!!.epochSeconds *1000)
                Card(
                    onClick = { realmViewModel.deleteBoleto(boleto._id) },
                    modifier = Modifier
                        .size(320.dp, 200.dp)
                ) {
                    Column(modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Text(text =  "Tipo: ${boleto.tipo}")
                        Text(text = "Nunero: ${boleto.numeroSerie}")
                        Text(text = formatter.format(date))
                        Text(text = "Combinaciones: ${boleto.combinaciones}")
                        Text(text = "R= ${boleto.reintegro}")
                        Text(text = "Precio: ${boleto.precio}")
                        Text(text = "Premio: ${boleto.premio}")
                    }
                }

            }
        }
    }

}

@Composable
fun BoletoItem(
    boleto: Boletos,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        boleto.primitiva.toString()
    }
}

@Composable
fun FAB(
    scannerViewModel: ScannerViewModel
) {
    FloatingActionButton(
        onClick = { scannerViewModel.startScanning() },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "scann")
    }
}

