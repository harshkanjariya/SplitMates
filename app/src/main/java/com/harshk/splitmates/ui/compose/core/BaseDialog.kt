package com.harshk.splitmates.ui.compose.core

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BaseDialog(
    isShowingFlow: MutableStateFlow<Boolean>,
    onSubmit: () -> Unit,
    onCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    val isShowing = isShowingFlow.collectAsState()

    if (isShowing.value) {
        Dialog(onDismissRequest = onCancel) {
            Card {
                Column(modifier = Modifier.padding(10.dp)) {
                    content()
                    Row {
                        Button(onClick = onSubmit, modifier = Modifier.weight(1f)) {
                            Text(text = "Ok")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick = onCancel, modifier = Modifier.weight(1f)) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}