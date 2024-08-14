package com.example.mylotteryapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.RealmViewModel


@Composable
fun ListBoletos(
    realmViewModel: RealmViewModel,
    paddingValues: PaddingValues,
    boletos: List<Boleto>

) {

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        state = listState
    ) {
        items(boletos, key = { it.numeroSerie }) { boleto ->
            ItemBoleto(
                boleto = boleto,
                realmViewModel = realmViewModel,
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 4.dp),
                thickness = 0.5.dp,
                color = Color.Black
            )
        }
        item {
            Spacer(modifier = Modifier.size(80.dp))
        }



    }

}

