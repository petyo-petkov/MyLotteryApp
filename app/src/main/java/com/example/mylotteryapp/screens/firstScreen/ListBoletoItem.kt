package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemBonoloto
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemElGordo
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemEuroDreams
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemEuromillones
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemLoterias
import com.example.mylotteryapp.screens.firstScreen.itemsBoletos.ItemPrimitiva
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ListBoletosItem(
    realmViewModel: RealmViewModel,
    paddingValues: PaddingValues,
) {
    realmViewModel.getBoletos()
    realmViewModel.getPrecios()

    val boletos = realmViewModel.boletos
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }

    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        boletos.forEach { boleto ->

            boleto.primitivas?.let { primitivas ->
                items(primitivas) { primitiva ->

                    ItemPrimitiva(
                        primitiva = primitiva,
                        formatter = formatter,
                        realmViewModel = realmViewModel,
                        boleto = boleto,

                        )

                    HorizontalDivider(
                        modifier = Modifier,
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }
            }

            boleto.bonolotos?.let { bonolotos ->
                items(bonolotos) { bonoloto ->

                    ItemBonoloto(
                        bonoloto = bonoloto,
                        formatter = formatter,
                        realmViewModel,
                        boleto
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.euroMillones?.let { euromillones ->
                items(euromillones) { euromillon ->

                    ItemEuromillones(
                        euromillon,
                        formatter,
                        realmViewModel,
                        boleto
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.gordos?.let { gordos ->
                items(gordos) { gordo ->

                    ItemElGordo(
                        gordo = gordo,
                        formatter = formatter,
                        realmViewModel,
                        boleto
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.euroDreams?.let { euroDreams ->
                items(euroDreams) { dream ->

                    ItemEuroDreams(
                        dream = dream,
                        formatter = formatter,
                        realmViewModel,
                        boleto
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }

            }

            boleto.loterias?.let { loterias ->
                items(loterias) { loteria ->

                    ItemLoterias(
                        loteria = loteria,
                        formatter = formatter,
                        realmViewModel = realmViewModel,
                        boleto = boleto
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        thickness = 0.5.dp,
                        color = Color.Black
                    )
                }
            }

        }

    }
}


