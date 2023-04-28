package com.harshk.splitmates.ui.compose.split_group_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshk.splitmates.ui.viewmodel.SplitGroupDetailsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SplitGroupDetailsComposable(viewModel: SplitGroupDetailsViewModel) {
    val state = viewModel.uiState.collectAsState()
    val group = state.value.group.collectAsState()
    val members = state.value.members.collectAsState()

    Column {
        Row {
            FlowRow(Modifier.weight(1f)) {
                Text(text = group.value.name)
            }
            Icon(Icons.Filled.Add, contentDescription = "")
        }
        FlowRow {
            repeat(members.value.size) {
                Text(
                    text = members.value[it].email,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}