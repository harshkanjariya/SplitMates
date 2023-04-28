package com.harshk.splitmates.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.ui.compose.split_group_details.SplitGroupDetailsComposable
import com.harshk.splitmates.ui.viewmodel.SplitGroupDetailsContract
import com.harshk.splitmates.ui.viewmodel.SplitGroupDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplitGroupDetails : AppCompatActivity() {
    private val viewModel: SplitGroupDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val group = Group(
            id = intent.getStringExtra("id") ?: "",
            name = intent.getStringExtra("name") ?: "",
        )
        viewModel.setEvent(
            SplitGroupDetailsContract.Event.SetupGroup(group = group)
        )
        setContent {
            SplitGroupDetailsComposable(viewModel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}