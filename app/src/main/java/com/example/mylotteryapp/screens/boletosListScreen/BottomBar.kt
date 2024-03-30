package com.example.mylotteryapp.screens.boletosListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(){

    BottomAppBar(
        modifier = Modifier
            .size(height = 80.dp, width = 420.dp )
           ,
        containerColor = MaterialTheme.colorScheme.primary,
        contentPadding = PaddingValues(horizontal = 115.dp, vertical = 12.dp),
        scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()


    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {  },
                modifier = Modifier
            ) {
                Icon(Icons.Filled.Menu, null)
            }
            ElevatedButton(
                onClick = {  },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    MaterialTheme.colorScheme.tertiary
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

            ) {
                Icon(Icons.Filled.Add, null)
            }

            IconButton(
                onClick = {  },
                modifier = Modifier
            ) {
                Icon(Icons.Filled.Delete, null, tint = Color.Red)
            }
        }
    }

}