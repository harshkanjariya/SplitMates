package com.harshk.splitmates.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.compose.setContent
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.ui.compose.split_group_details.SplitGroupDetailsComposable

class SplitGroupDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val group = Group(
            id = intent.getStringExtra("id") ?: "",
            name = intent.getStringExtra("name") ?: "",
        )
        setContent {
            SplitGroupDetailsComposable(group)
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