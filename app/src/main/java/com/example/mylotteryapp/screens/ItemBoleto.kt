package com.example.mylotteryapp.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mylotteryapp.R
import com.example.mylotteryapp.models.Boleto
import com.example.mylotteryapp.viewModels.RealmViewModel
import io.realm.kotlin.internal.platform.WeakReference
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBoleto(
    boleto: Boleto,
    formatter: SimpleDateFormat,
    realmViewModel: RealmViewModel,
) {

    val date = Date(boleto.fecha.epochSeconds * 1000)
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )
    var selected by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current
    var show by rememberSaveable { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
            .combinedClickable(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    isExpanded = !isExpanded
                    realmViewModel.isExpanded = isExpanded
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    selected = !selected
                    realmViewModel.selectedCard = selected
                    realmViewModel.boleto = boleto
                }
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (!selected) {
                MaterialTheme.colorScheme.secondary
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
                        .padding(horizontal = 16.dp)
                        .size(48.dp),
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

                                else -> R.drawable.ic_launcher_foreground
                            }
                        ),
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
                        text = boleto.tipo,
                        modifier = Modifier,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${boleto.precio} ${Typography.euro}",
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

            if (isExpanded) {
                ExpandedContent(
                    boleto = boleto ,
                    onConfirm = { show = true },
                    realmViewModel = realmViewModel)

            }
        }
    }
    DialogoPremio(
        show = show,
        onDismiss = { show = false },
        onConfirm = { valor ->
            realmViewModel.updatePremio(boleto, valor)
            show = false
            isExpanded = !isExpanded
            realmViewModel.isExpanded = isExpanded
        }
    )

}


// Funcioón para cargar imagenes optimizada
@Composable
fun loadImage(imageResourceId: Int): Painter {
    val imageCache = remember { mutableMapOf<Int, WeakReference<Painter>>() }

    val cachedImage = imageCache[imageResourceId]?.get()
    if (cachedImage != null) {
        return cachedImage
    } else {
        val painter = painterResource(id = imageResourceId)
        imageCache[imageResourceId] = WeakReference(painter)
        return painter
    }
}





