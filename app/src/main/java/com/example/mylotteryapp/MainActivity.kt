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
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.models.Bonoloto
import com.example.mylotteryapp.models.EuroMillones
import com.example.mylotteryapp.models.Primitiva
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
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    val boletos by realmViewModel.boletos.collectAsState()

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
            items(boletos) { boletos ->
                Card(
                    onClick = { realmViewModel.deleteBoleto(boletos._id) },
                    modifier = Modifier
                        .sizeIn(minHeight = 200.dp, minWidth = 360.dp)
                        .padding(8.dp)
                ) {
                    boletos.primitivas?.let { primitivas ->
                        for (primitiva in primitivas) {
                            PrimitivaItem(primitiva, formatter)
                        }
                    }
                    boletos.bonolotos?.let { bonolotos ->
                        for (bonoloto in bonolotos) {
                            BonolotoItem(bonoloto, formatter)
                        }
                    }
                    boletos.euroMillones?.let { euromillones ->
                        for (euro in euromillones) {
                            EuroMillonesItem(euro, formatter)
                        }
                    }
                }
            }
        }
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

@Composable
fun PrimitivaItem(
    boleto: Primitiva,
    sdf: SimpleDateFormat,
) {
    val date = Date(boleto.fecha!!.epochSeconds * 1000)
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Tipo: ${boleto.tipo}")
        Text(text = "Fecha: ${sdf.format(date)}")
        boleto.combinaciones.forEachIndexed { index, combi ->
            Text(text = "Columna ${index + 1}: $combi")
        }
        Text(text = "Reintegro: ${boleto.reintegro}")
        Text(text = "Precio: ${boleto.precio}")
        Text(text = "Premio: ${boleto.premio}")
        Text(text = "Joker: ${boleto.joker}")
    }

}

@Composable
fun BonolotoItem(
    boleto: Bonoloto,
    sdf: SimpleDateFormat
) {
    val date = Date(boleto.fecha!!.epochSeconds * 1000)
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(text = "Tipo: ${boleto.tipo}")
        Text(text = "Fecha: ${sdf.format(date)}")
        boleto.combinaciones.forEachIndexed { index, combi ->
            Text(text = "Columna ${index + 1}: $combi")
        }
        Text(text = "Reintegro: ${boleto.reintegro}")
        Text(text = "Precio: ${boleto.precio}")
        Text(text = "Premio: ${boleto.premio}")
    }

}

@Composable
fun EuroMillonesItem(
    boleto: EuroMillones,
    sdf: SimpleDateFormat
) {
    val date = Date(boleto.fecha!!.epochSeconds * 1000)
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(text = "Tipo: ${boleto.tipo}")
        Text(text = "Fecha: ${sdf.format(date)}")
        boleto.combinaciones.forEachIndexed { index, combi ->
            boleto.estrellas.forEach { star ->
                Text(text = "Apuesta ${index + 1}: $combi  \u2605 $star")
            }
        }
        Text(text = "El Millon: ${boleto.elMillon}")
        Text(text = "Precio: ${boleto.precio}")
        Text(text = "Premio: ${boleto.premio}")
    }

}

