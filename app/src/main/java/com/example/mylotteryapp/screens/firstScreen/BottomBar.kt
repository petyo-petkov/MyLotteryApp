package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun BottomBar(scannerViewModel: ScannerViewModel){
    BottomAppBar(
        actions = {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Check, contentDescription = "Localized description")
        }
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Localized description",
            )
        }
    },
        modifier = Modifier,
        floatingActionButton = { FAB(scannerViewModel )},
        contentPadding = PaddingValues(horizontal = 30.dp)

    )
}