package com.example.mylotteryapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun FabReturn(
    realmViewModel: RealmViewModel,
    navigator: DestinationsNavigator
) {
    FloatingActionButton(
        onClick = {
            navigator.popBackStack()
            realmViewModel.boletosEnRangoDeFechas = emptyList()

        }, containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
    }
}