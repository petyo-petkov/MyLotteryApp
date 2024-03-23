package com.example.mylotteryapp.screens.firstScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylotteryapp.viewModels.RealmViewModel
import com.example.mylotteryapp.viewModels.ScannerViewModel

@Composable
fun FAB(
    scannerViewModel: ScannerViewModel,
    realmViewModel: RealmViewModel,
    isListAtEnd: Boolean
) {
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }

    AnimatedVisibility(
        visible = !isListAtEnd,
        enter = fadeIn(animationSpec = tween(150)),
        exit = fadeOut(animationSpec = tween(300))
        ) {
        ElevatedCard(
            modifier = Modifier.size(200.dp, 50.dp),
            shape = AbsoluteRoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFECB3)),
            elevation = CardDefaults.elevatedCardElevation(2.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(Icons.Filled.Menu, null)
                }
                ElevatedButton(
                    onClick = { scannerViewModel.startScanning(context) },
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
                    shape = ButtonDefaults.elevatedShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE93D8)),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)

                ) {
                    Icon(Icons.Filled.Add, null)
                }

                IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(Icons.Filled.Delete, null, tint = Color.Red)
                }
            }

        }
    }

//   if (!isListAtEnd){
//
//       ElevatedCard(
//           modifier = Modifier.size(200.dp, 50.dp),
//           shape = AbsoluteRoundedCornerShape(30.dp),
//           colors = CardDefaults.cardColors(containerColor = Color(0xFFFFECB3)),
//           elevation = CardDefaults.elevatedCardElevation(4.dp)
//
//       ) {
//           Row(
//               modifier = Modifier.fillMaxWidth(),
//               verticalAlignment = Alignment.CenterVertically,
//               horizontalArrangement = Arrangement.SpaceAround
//           ) {
//               IconButton(
//                   onClick = { /*TODO*/ }
//               ) {
//                   Icon(Icons.Filled.Menu, null)
//               }
//               ElevatedButton(
//                   onClick = { scannerViewModel.startScanning(context) },
//                   modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
//                   shape = ButtonDefaults.elevatedShape,
//                   colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE93D8)),
//                   elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
//
//               ) {
//                   Icon(Icons.Filled.Add, null)
//               }
//
//               IconButton(
//                   onClick = { showDialog = true }
//               ) {
//                   Icon(Icons.Filled.Delete, null, tint = Color.Red)
//               }
//           }
//
//       }
//   }


    DialogoBorrar(
        show = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = realmViewModel::deleteAllBoletos,
        mensaje = "Borrar todos los boletos?"
    )
}

