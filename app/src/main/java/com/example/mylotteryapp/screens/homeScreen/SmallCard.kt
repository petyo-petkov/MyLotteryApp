package com.example.mylotteryapp.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SmallCard(title: String, content:String, color: Color) {

    ElevatedCard(
        modifier = Modifier
            .size(180.dp, 120.dp),
        shape = ShapeDefaults.Large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
            Text(
                text = content,
                modifier = Modifier
                    .weight(1f),
                fontSize = 32.sp,
                //style = MaterialTheme.typography.displaySmall,
                color = color,

            )
        }

    }
}

