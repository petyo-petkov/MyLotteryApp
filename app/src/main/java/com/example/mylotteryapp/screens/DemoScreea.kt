package com.example.mylotteryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.text.Typography.euro

@Preview(showBackground = true, showSystemUi = true)
@Composable

fun DemoScreen() {

    val modifier = Modifier

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = { FAB() },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color(0xFF282A42)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = it
        ) {
            item {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        modifier = modifier
                            .padding(top = 30.dp)
                            .size(320.dp, 40.dp)
                            .alpha(0.9f),
                        shape = AbsoluteRoundedCornerShape(
                            topLeft = 8.dp,
                            topRight = 8.dp,
                            bottomLeft = 0.dp,
                            bottomRight = 0.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface
                        )
                    ) {
                        Text(
                            text = "Gastado:                 23 $euro",
                            modifier
                                .padding(8.dp)
                                .alpha(0.9f)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center
                        )
                    }
                    Card(
                        modifier
                            .padding(8.dp)
                            .size(320.dp, 40.dp)
                            .alpha(0.9f),
                        shape = AbsoluteRoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors
                            (containerColor = MaterialTheme.colorScheme.inverseSurface)
                    ) {
                        Text(
                            text = "Ganado:               141 $euro",
                            modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center
                        )
                    }
                    Card(
                        modifier = modifier
                            .size(320.dp, 40.dp),
                        shape = AbsoluteRoundedCornerShape(
                            topLeft = 0.dp,
                            topRight = 0.dp,
                            bottomLeft = 8.dp,
                            bottomRight = 8.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface
                        )
                    ) {
                        Text(
                            text = "Balance:              118 $euro",
                            modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            item {
                Card(
                    modifier
                        .size(320.dp, 300.dp)
                        .padding(top = 30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface
                    )

                ) {
                    LazyColumn {
                        items(7) {
                            Card(
                                modifier
                                    .fillMaxWidth()
                                    .size(40.dp),
                                shape = ShapeDefaults.ExtraSmall,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                Text(text = "Boleto ")
                            }
                        }
                    }

                }
            }

            item {
                Box(
                    modifier
                        .size(320.dp, 320.dp)
                        .padding(top = 30.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier.fillMaxSize(),

                        ) {
                        Row(
                            modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(
                                modifier.size(150.dp, 150.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                Text(text = "Algo")
                            }
                            Card(
                                modifier.size(150.dp, 150.dp), colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                Text(text = "Algo")
                            }
                        }
                        Row(
                            modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(
                                modifier.size(150.dp, 150.dp), colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                Text(text = "Algo")
                            }
                            Card(
                                modifier.size(150.dp, 150.dp), colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                Text(text = "Algo")
                            }
                        }
                    }


                }
            }

        }
    }

}

@Composable
fun FAB() {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        modifier = Modifier,
        shape = CircleShape
    ) {
        Icon(Icons.Filled.Add, null)
    }
}