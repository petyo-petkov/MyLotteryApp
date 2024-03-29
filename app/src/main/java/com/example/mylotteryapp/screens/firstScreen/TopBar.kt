package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.RealmViewModel
import kotlin.text.Typography.euro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    realmViewModel: RealmViewModel,
    boletos: List<Boleto>
) {

    var showDialog by remember { mutableStateOf(false) }
    val selected = realmViewModel.selectedCard

    val boleto = realmViewModel.boleto

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
        actions = {
            if (selected) {
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(end = 8.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )

                }
            }
        }
    )
    DialogoBorrar(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            realmViewModel.deleteBoleto(boleto._id)
            realmViewModel.selectedCard = false
        },
        mensaje = "Borrar boleto seleccionado ?"
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
