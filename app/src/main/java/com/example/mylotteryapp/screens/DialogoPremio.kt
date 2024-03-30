package com.example.mylotteryapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoPremio(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit,

    ) {
    var valor by remember { mutableStateOf("") }
    val premio by remember { derivedStateOf { if (valor.isNotEmpty()) valor.toDouble() else 0.0 } }


    AnimatedVisibility(
        visible = show,
        modifier = Modifier,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        BasicAlertDialog(
            onDismissRequest = { onDismiss() },
            modifier = Modifier.padding(16.dp)
        ) {
            Card(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = valor,
                        onValueChange = {
                            if (it.isEmpty() || it.toDoubleOrNull() != null) {
                                valor = it
                            }
                        },
                        modifier = Modifier,
                        label = { Text(text = "Ingresar o corregir premio:") },
                        placeholder = { Text(text = "0.00 ${Typography.euro}") },
                        shape = ShapeDefaults.Large,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.secondary
                            )
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(0.dp),

                        ) {
                        Text("Cancelar")
                    }
                    TextButton(
                        onClick = { onConfirm(premio) },
                        modifier = Modifier.padding(0.dp),

                        ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}
