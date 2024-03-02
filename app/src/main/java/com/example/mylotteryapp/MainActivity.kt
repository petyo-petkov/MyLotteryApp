package com.example.mylotteryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.models.Primitiva
import com.example.mylotteryapp.presentation.viewModelFactory
import com.example.mylotteryapp.ui.theme.MyLotteryAppTheme
import com.example.mylotteryapp.viewModels.ScannerViewModel

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
                App(viewModel = scannerViewModel)
            }
        }
    }
}

@Composable
fun App(
    viewModel: ScannerViewModel
){

    val state = viewModel.state.value
    val boletos by viewModel.boletos.collectAsState()

    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .size(300.dp, 100.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9))
        ) {
            Text(text = state)
        }
        Button(
            onClick = { viewModel.startScanning() },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "scann")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(boletos) { boleto ->
                BoletoItem(
                    boleto = boleto,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(16.dp)
                        .clickable {  }
                )
            }
        }

    }
}
@Composable
fun BoletoItem(
    boleto: Primitiva,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = boleto.numeroSerie.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = boleto.precio.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = boleto.fecha,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = boleto.combinaciones.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

