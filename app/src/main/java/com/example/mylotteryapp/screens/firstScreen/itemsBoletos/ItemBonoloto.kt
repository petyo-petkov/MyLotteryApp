package com.example.mylotteryapp.screens.firstScreen.itemsBoletos

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylotteryapp.R
import com.example.mylotteryapp.models.Bonoloto
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ItemBonoloto(
    bonoloto: Bonoloto,
    formatter: SimpleDateFormat,

    ) {
    val date = Date(bonoloto.fecha!!.epochSeconds * 1000)
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
            .clickable { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bonoloto),
                        contentDescription = null
                    )
                }
                // Text container
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = formatter.format(date),
                        modifier = Modifier,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = bonoloto.tipo,
                        modifier = Modifier,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${bonoloto.precio} ${Typography.euro}",
                        modifier = Modifier,
                        fontSize = 12.sp,

                        )

                }
                // Icono final
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .rotate(rotationState)
                )
            }
            if (expandedState) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp, end = 12.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        bonoloto.combinaciones.forEachIndexed { index, combi ->
                            Text("${index + 1}: $combi")

                        }
                        Text("Reintegro: ${bonoloto.reintegro}")
                        Text("Premio: ${bonoloto.premio} ${Typography.euro}")
                    }
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }


                }
            }
        }
    }


}

