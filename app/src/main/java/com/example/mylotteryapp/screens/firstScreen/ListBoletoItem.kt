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
    realmViewModel.getPremio()

    val boletos = realmViewModel.boletos
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }


    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        items(boletos) {boleto ->
            ItemBoleto(boleto = boleto, formatter = formatter, realmViewModel = realmViewModel)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 4.dp),
                thickness = 0.5.dp,
                color = Color.Black
            )
        }

    }
}


