package com.harshk.splitmates

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshk.splitmates.core.BaseFragment
import com.harshk.splitmates.core.GenericAdapter
import com.harshk.splitmates.databinding.FragmentSettingsBinding
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val viewModel: SettingsViewModel by viewModels()

    private val adapter = getAdapter()

    override fun FragmentSettingsBinding.setViewBindingOnCreateView() {
        settingsAccountList.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun FragmentSettingsBinding.setViewBindingOnViewCreated() {
        settingsAccountList.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splitFiles.collect {
                    adapter.submitList(it)
                }
            }
        }
        viewModel.loadFiles()

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        settingsAccountList.addItemDecoration(itemDecoration)
    }

    private fun getAdapter(): GenericAdapter<SettingListItem> {
        return GenericAdapter(
            R.layout.settings_list_item,
            areItemsSame = { old, new ->
                old.id == new.id
            },
            areItemContentsEqual = { old, new ->
                old.selected == new.selected
            },
            onHold = {
                it.selected = true
            }
        )
    }

}