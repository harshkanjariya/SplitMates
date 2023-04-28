package com.harshk.splitmates.ui.compose.split_group_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.harshk.splitmates.domain.model.Group

@Composable
fun SplitGroupDetailsComposable(group: Group) {
    Column {
        Text(text = "New Page")
        Text(text = group.id)
        Text(text = group.name)
    }
}
