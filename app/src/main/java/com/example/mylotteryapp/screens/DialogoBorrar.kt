package com.example.mylotteryapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DialogoBorrar(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    mensaje: String
) {
    AnimatedVisibility (
        visible = show,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                },) {
                    Text(text = "BORRAR", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "CANCELAR",
                        color = MaterialTheme.colorScheme.onSurface)

                }
            },
            title = { Text(text = "Borrar") },
            text = { Text(text = mensaje) },
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}