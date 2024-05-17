package com.example.mylotteryapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylotteryapp.R
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.Orden
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ResultadosViewModel
import io.realm.kotlin.internal.platform.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBoleto(
    boleto: Boleto,
    realmViewModel: RealmViewModel
) {
    val resultadoViewModel: ResultadosViewModel = viewModel()
    val resultado by resultadoViewModel.resultado.collectAsState()

    val coroutine = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current
    val formatter = rememberSaveable { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }
    val formatterResultados = rememberSaveable { SimpleDateFormat("yyyyMMdd", Locale.ENGLISH) }
    val date = Date(boleto.fecha.epochSeconds * 1000)

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )
    var selected by remember { mutableStateOf(false) }
    var showDialogoPremio by rememberSaveable { mutableStateOf(false) }
    var showDialogoResultado by rememberSaveable { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                )
            )
            .combinedClickable(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    isExpanded = !isExpanded
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    selected = !selected
                    when {
                        selected -> realmViewModel.addOrRemoveSelecionados(boleto, Orden.ADD)
                        !selected -> realmViewModel.addOrRemoveSelecionados(boleto, Orden.REMOVE)
                    }
                }
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (!selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.errorContainer
            },
            contentColor = Color.Black
        )
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
                        .padding(horizontal = 24.dp)
                        .size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = loadImage(
                            when (boleto.tipo) {
                                "Primitiva" -> R.drawable.la_primitiva

                                "Bonoloto" -> R.drawable.bonoloto

                                "Euromillones" -> R.drawable.euromillones

                                "El Gordo" -> R.drawable.el_godo

                                "Euro Dreams" -> R.drawable.euro_dreams

                                "Loteria Nacional" -> R.drawable.loteria_nacional

                                else -> R.drawable.logo
                            }
                        ),
                        contentDescription = null
                    )
                }
                // Text container
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .padding(vertical = 2.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = formatter.format(date),
                        modifier = Modifier,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = boleto.tipo,
                        modifier = Modifier,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Precio ${boleto.precio} ${Typography.euro}",
                        modifier = Modifier,
                        fontSize = 12.sp,

                        )

                    Text(
                        text = "Premio ${boleto.premio} ${Typography.euro}",
                        modifier = Modifier,
                        fontSize = 12.sp,

                        )

                }
                // Icono final
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier

                        .padding(horizontal = 16.dp)
                        .rotate(rotationState)
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(tween(600)),
                exit = fadeOut(
                    tween(
                        durationMillis = 200
                    )
                )
            ) {
                ExpandedContent(
                    boleto = boleto,
                    onEditarPremio = { showDialogoPremio = true },
                    onGetResultado = {
                        resultadoViewModel.fetchResultado(
                            boleto,
                            formatterResultados.format(date),
                            formatterResultados.format(date)
                        )

                        showDialogoResultado = true
                    }
                )
            }
        }
    }
    DialogoPremio(
        show = showDialogoPremio,
        onDismiss = { showDialogoPremio = false },
        onConfirm = { valor ->
            realmViewModel.updatePremio(boleto, valor)
            showDialogoPremio = false
            isExpanded = !isExpanded

        }
    )

    DialogoResultado(
        show = showDialogoResultado,
        onDismiss = { showDialogoResultado = false },
        text = resultado,
        tipo = boleto.tipo
    )


}


// Funcioón para cargar imagenes optimizada
@Composable
fun loadImage(imageResourceId: Int): Painter {
    val imageCache = remember { mutableMapOf<Int, WeakReference<Painter>>() }
    val cachedImage = imageCache[imageResourceId]?.get()
    when (cachedImage) {
        null -> {
            val image = painterResource(id = imageResourceId)
            imageCache[imageResourceId] = WeakReference(image)
            return image
        }

        else -> {
            return cachedImage
        }
    }
}
/*
suspend fun getResultado(boleto: Boleto, fechaInicio: String, fechaFin: String): String {

    return when (boleto.tipo) {
        "Primitiva" -> resultados<ResultadosPrimitiva>(fechaInicio, fechaFin)[0].combinacion

        "Bonoloto" -> resultados<ResultadosBonoloto>(fechaInicio, fechaFin)[0].combinacion

        "Euromillones" -> resultados<ResultadosEuromillones>(
            fechaInicio,
            fechaFin
        )[0].combinacion

        "El Gordo" -> resultados<ResultadosElGordo>(fechaInicio, fechaFin)[0].combinacion

        "Euro Dreams" -> resultados<ResultadosEuroDreams>(
            fechaInicio,
            fechaFin
        )[0].combinacion

        "Loteria Nacional" -> {
            val resultadoLoteriaNacional =
                resultados<ResultadosLoteriaNacional>(fechaInicio, fechaFin)
            val primerPremio = resultadoLoteriaNacional[0].primerPremio.decimo
            val segundoPremio = resultadoLoteriaNacional[0].segundoPremio.decimo
            //val tercerPremio: String? = resultadoLoteriaNacional[0].tercerosPremios[0].decimo
            """
                            Primer premio: $primerPremio
                            Segundo Premio: $segundoPremio
                        """.trimIndent()
        }

        else -> "Boleto desconosido"

    }

}

 */





