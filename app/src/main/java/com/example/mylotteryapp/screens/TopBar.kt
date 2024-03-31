package com.example.mylotteryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.models.Boleto
import kotlin.text.Typography.euro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    boletos: List<Boleto>
) {

    var gastado = 0.0
    var ganado = 0.0

    boletos.forEach {
        gastado += it.precio
        it.premio?.let { ganado += it }
    }

    val balance = ganado - gastado

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoColumn(text1 = "Gastado", text2 = "$gastado $euro")
                InfoColumn(text1 = "Ganado", text2 = "$ganado $euro")
                InfoColumn(text1 = "Balance", text2 = "$balance $euro")

            }

        },
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
    )
}

@Composable
fun InfoColumn(
    text1: String, text2: String
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text1, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(text2, fontSize = 16.sp, fontWeight = FontWeight.Medium)

    }

}