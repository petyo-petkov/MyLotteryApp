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
import androidx.compose.ui.platform.LocalContext
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
                    onClick = {
                        realmViewModel.deleteBoleto(boletos._id)
                    },
                    modifier = Modifier
                        .sizeIn(minHeight = 200.dp, minWidth = 360.dp)
                        .padding(8.dp)
                ) {
                    BoletoItem(boletos = boletos, sdf = formatter)

                }
            }
        }
    }
}

@Composable
fun FAB(scannerViewModel: ScannerViewModel) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = { scannerViewModel.startScanning(context) },
    ) {
        Text(text = "scann")
    }
}


@Composable
fun BoletoItem(
    boletos: Boletos,
    sdf: SimpleDateFormat,
) {
    boletos.primitivas?.let { primitivas ->
        for (primitiva in primitivas) {
            val date = Date(primitiva.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Tipo: ${primitiva.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                primitiva.combinaciones.forEachIndexed { index, combi ->
                    Text(text = "Columna ${index + 1}: $combi")
                }
                Text(text = "Reintegro: ${primitiva.reintegro}")
                Text(text = "Precio: ${primitiva.precio}")
                Text(text = "Premio: ${primitiva.premio}")
                Text(text = "Joker: ${primitiva.joker}")
            }
        }
    }
    boletos.bonolotos?.let { bonolotos ->
        for (bonoloto in bonolotos) {
            val date = Date(bonoloto.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Tipo: ${bonoloto.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                bonoloto.combinaciones.forEachIndexed { index, combi ->
                    Text(text = "Columna ${index + 1}: $combi")
                }
                Text(text = "Reintegro: ${bonoloto.reintegro}")
                Text(text = "Precio: ${bonoloto.precio}")
                Text(text = "Premio: ${bonoloto.premio}")
            }
        }
    }
    boletos.euroDreams?.let { dreams ->
        for (dream in dreams) {
            val date = Date(dream.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Tipo: ${dream.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                dream.combinaciones.forEachIndexed { index, combi ->
                    dream.dreams.forEach { dream ->
                        Text(text = "Apuesta ${index + 1}: $combi + $dream")
                    }
                }
                Text(text = "Precio: ${dream.precio}")
                Text(text = "Premio: ${dream.premio}")
            }
        }
    }
    boletos.gordos?.let { gordos ->
        for (gordo in gordos) {
            val date = Date(gordo.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Tipo: ${gordo.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                gordo.combinaciones.forEachIndexed { index, combi ->
                    gordo.numeroClave.forEach { clave ->
                        Text(text = "Apuesta ${index + 1}: $combi + $clave")
                    }
                }
                Text(text = "Precio: ${gordo.precio}")
                Text(text = "Premio: ${gordo.premio}")
            }
        }
    }
    boletos.euroMillones?.let { euromillones ->
        for (euro in euromillones) {
            val date = Date(euro.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Tipo: ${euro.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                euro.combinaciones.forEachIndexed { index, combi ->
                    euro.estrellas.forEach { star ->
                        Text(text = "Apuesta ${index + 1}: $combi \u2605 $star")
                    }
                }
                Text(text = "El Millon: ${euro.elMillon}")
                Text(text = "Precio: ${euro.precio}")
                Text(text = "Premio: ${euro.premio}")
            }
        }
    }
    boletos.loterias?.let { loterias ->
        for (loteria in loterias) {
            val date = Date(loteria.fecha!!.epochSeconds * 1000)
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Tipo: ${loteria.tipo}")
                Text(text = "Fecha: ${sdf.format(date)}")
                Text(text = "Numero Loteria ${loteria.numero}")
                Text(text = "Precio: ${loteria.precio}")
                Text(text = "Premio: ${loteria.premio}")
            }
        }
    }
}







