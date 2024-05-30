package com.example.mylotteryapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DialogoResultado(
    show: Boolean,
    onDismiss: () -> Unit,
    combinacion: String,
    tipo: String,
    premio: String,

    ) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }
                ) {
                    Text(text = "Ok", color = Color.Black)
                }
            },
            dismissButton = { },
            title = {
                Text(
                    text = "Resultado sorteo $tipo: ",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = combinacion,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Premio: $premio",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                }
            },

            textContentColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}