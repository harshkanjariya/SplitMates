package com.harshk.splitmates.ui.compose.main

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harshk.splitmates.R
import com.harshk.splitmates.ui.SplitGroupDetails
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.ui.viewmodel.MainContract
import com.harshk.splitmates.ui.viewmodel.MainViewModel

fun openActivity(context: Context, group: Group) {
    val intent = Intent(context, SplitGroupDetails::class.java)
    intent.putExtra("id", group.id)
    intent.putExtra("name", group.name)
    context.startActivity(intent)
}

@Composable
fun MainFragmentComposable(viewModel: MainViewModel) {
    val state = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainSplitList(
                state.value.groups,
                onItemClick = {
                    openActivity(context, it)
                }
            )
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
                        viewModel.setEvent(
                            MainContract.Event.OnCreateFile(name = it)
                        )
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
