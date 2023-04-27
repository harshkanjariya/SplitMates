package com.harshk.splitmates.ui.compose.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshk.splitmates.domain.model.Group
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MainSplitList(groups: MutableStateFlow<List<Group>>, onItemClick: (group: Group) -> Unit) {
    val list = groups.collectAsState()

    LazyColumn {
        itemsIndexed(list.value) { index, item ->
            if (index > 0) {
                Divider(Modifier.height(1.dp))
            }
            MainSplitItem(data = item, onClick = {
                onItemClick(item)
            })
        }
    }
}

@Composable
fun MainSplitItem(data: Group, onClick: () -> Unit) {
    Column(Modifier.padding(5.dp).clickable { onClick() }) {
        Text(text = data.id)
        Text(text = data.name)
    }
}