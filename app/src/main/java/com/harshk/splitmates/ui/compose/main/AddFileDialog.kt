package com.harshk.splitmates.ui.compose.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.harshk.splitmates.ui.compose.core.BaseDialog
import com.harshk.splitmates.ui.viewmodel.MainContract

@Composable
fun AddFileDialog(
    state: State<MainContract.State>,
    onSubmit: (name: String) -> Unit,
    onCancel: () -> Unit,
) {
    val text = rememberSaveable { mutableStateOf("") }
    val isShowing = state.value.isAddDialogShowing

    BaseDialog(
        isShowing,
        onSubmit = {
            onSubmit(text.value)
        },
        onCancel = onCancel
    ) {
        TextField(
            value = text.value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                text.value = it
            }
        )
    }
}