package com.harshk.splitmates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.harshk.splitmates.core.BaseFragment
import com.harshk.splitmates.core.GenericAdapter
import com.harshk.splitmates.databinding.FragmentSettingsBinding
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.viewmodel.MainViewModel
import com.harshk.splitmates.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val viewModel: SettingsViewModel by viewModels()
    override fun FragmentSettingsBinding.setViewBindingVariables() {
        settingsAccountList.adapter = getAdapter()
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