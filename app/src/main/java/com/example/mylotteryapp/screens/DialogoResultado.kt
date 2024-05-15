package com.example.mylotteryapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DialogoResultado(
    show: Boolean,
    onDismiss: () -> Unit,
    text: String,
    tipo: String
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
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = "CANCELAR",
                        color = MaterialTheme.colorScheme.onSurface
                        )
                }
            },
            title = {
                Text(
                    text = "Resultado sorteo $tipo: ",
                    style = MaterialTheme.typography.titleLarge
                    ) },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            textContentColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}