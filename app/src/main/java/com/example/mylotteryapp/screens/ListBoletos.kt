package com.example.mylotteryapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("FrequentlyChangedStateReadInComposition", "UnrememberedMutableState")
@Composable
fun ListBoletos(
    realmViewModel: RealmViewModel,
    paddingValues: PaddingValues,
    boletos: List<Boleto>

) {
    realmViewModel.getBoletos()

    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
        state = listState
    ) {
        items(boletos, key = { it.numeroSerie }) { boleto ->

                ItemBoleto(
                    boleto = boleto,
                    formatter = formatter,
                    realmViewModel = realmViewModel,
                    )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    thickness = 0.5.dp,
                    color = Color.Black
                )

        }

    }

}

