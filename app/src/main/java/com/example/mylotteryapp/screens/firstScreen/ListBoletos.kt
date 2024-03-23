package com.example.mylotteryapp.screens.firstScreen

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
import com.example.mylotteryapp.viewModels.RealmViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ListBoletos(
    realmViewModel: RealmViewModel,
    paddingValues: PaddingValues,
    onListEndChange: (Boolean) -> Unit
) {
    realmViewModel.getBoletos()
    realmViewModel.getPrecios()
    realmViewModel.getPremio()

    val boletos = realmViewModel.boletos
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    val scrollState = rememberLazyListState()
    val isListAtEnd =
        scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == boletos.lastIndex
    onListEndChange(isListAtEnd)

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
        state = scrollState


    ) {
        items(boletos, key = { it.numeroSerie }) { boleto ->

            ItemBoleto(boleto = boleto, formatter = formatter, realmViewModel = realmViewModel)

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 4.dp),
                thickness = 0.5.dp,
                color = Color.Black
            )
        }

    }


}


