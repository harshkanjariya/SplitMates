package com.harshk.splitmates

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshk.splitmates.core.BaseFragment
import com.harshk.splitmates.core.GenericAdapter
import com.harshk.splitmates.databinding.FragmentSettingsBinding
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val viewModel: SettingsViewModel by viewModels()

    private val adapter = getAdapter()

    override fun FragmentSettingsBinding.setViewBindingVariables() {
        settingsAccountList.adapter = adapter
        settingsAccountList.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel.splitFiles.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            viewModel.loadFiles()
        }
    }

    private fun getAdapter(): GenericAdapter<SettingListItem> {
        return GenericAdapter(
            areItemsSame = { old, new ->
                old.id == new.id
            },
            areItemContentsEqual = { old, new ->
                old.id == new.id
            }
        )
    }
}