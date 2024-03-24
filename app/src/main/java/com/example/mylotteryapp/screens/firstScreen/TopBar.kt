package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
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
import com.example.mylotteryapp.viewModels.RealmViewModel
import kotlin.text.Typography.euro

@Composable
fun TopBar(realmViewModel: RealmViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val selected = realmViewModel.selectedCard
    val boleto = realmViewModel.boleto
    val gastado = realmViewModel.gastado
    val ganado = realmViewModel.ganado
    val balance = ganado - gastado

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = ShapeDefaults.ExtraSmall,
    ) {

        // Nah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
                .background(color = MaterialTheme.colorScheme.tertiary),
        ) {
            // Vacio
        }
        // Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(64.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoColumn(text1 = "Gastado", text2 = "$gastado $euro")
            InfoColumn(text1 = "Ganado", text2 = "$ganado $euro")
            InfoColumn(text1 = "Balance", text2 = "$balance $euro")

        }
        HorizontalDivider(color = Color.Black, thickness = 0.4.dp)

        if (selected) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { showDialog = true }

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )

                }
            }
            DialogoBorrar(
                show = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = {
                    realmViewModel.deleteBoleto(boleto!!._id)
                    realmViewModel.boleto = null
                    realmViewModel.selectedCard = false
                },
                mensaje = "Borrar boleto seleccionado ?"
            )

        }
    }

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
