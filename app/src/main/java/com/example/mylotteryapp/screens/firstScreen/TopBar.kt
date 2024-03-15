package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.viewModels.RealmViewModel
import kotlin.math.exp
import kotlin.text.Typography.euro

@Composable
fun TopBar() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = ShapeDefaults.ExtraSmall,
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
            ) {

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Gastado: 54 $euro", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                VerticalDivider(
                    modifier = Modifier.padding(vertical = 6.dp),
                    thickness = 0.4.dp,
                    color = Color.Black
                )

                Text("Ganado: 89 $euro", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                VerticalDivider(
                    modifier = Modifier.padding(vertical = 6.dp),
                    thickness = 0.4.dp,
                    color = Color.Black
                )

                Text("Balance: 254 $euro", fontSize = 16.sp, fontWeight = FontWeight.Medium)

            }
        }
    }

}
