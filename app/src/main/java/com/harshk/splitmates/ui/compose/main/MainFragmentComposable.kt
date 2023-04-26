package com.harshk.splitmates.ui.compose.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harshk.splitmates.R
import com.harshk.splitmates.ui.viewmodel.MainContract
import com.harshk.splitmates.ui.viewmodel.MainViewModel

@Composable
fun MainFragmentComposable(viewModel: MainViewModel) {
    val state = viewModel.uiState.collectAsState()

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainSplitList()
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                onClick = {
                    viewModel.setEvent(MainContract.Event.OnAddDialogShow)
                }
            ) {
                AddFileDialog(
                    state = state,
                    onSubmit = {

                    },
                    onCancel = {
                        viewModel.setEvent(MainContract.Event.OnAddDialogHide)
                    }
                )
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    }
}
