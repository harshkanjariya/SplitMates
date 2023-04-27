package com.harshk.splitmates.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshk.splitmates.R
import com.harshk.splitmates.core.BaseFragment
import com.harshk.splitmates.core.GenericAdapter
import com.harshk.splitmates.databinding.FragmentSettingsBinding
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate), MenuProvider {
    private val viewModel: SettingsViewModel by viewModels()

    private val adapter = getAdapter()

    private var mainMenu: Menu? = null

    override fun FragmentSettingsBinding.setViewBindingOnCreateView() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this@SettingsFragment, viewLifecycleOwner, Lifecycle.State.RESUMED)

        settingsAccountList.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun FragmentSettingsBinding.setViewBindingOnViewCreated() {
        settingsAccountList.adapter = adapter
        val title = activity?.title

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splitFiles.collect {
                    adapter.submitList(it)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.isSelectionOn.collect {
                (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(!it)
                mainMenu?.findItem(R.id.menu_delete)?.isVisible = it
                if (it) {
                    activity?.title = ""
                } else {
                    activity?.title = title
                }
            }
        }
        viewModel.loadFiles()

        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        settingsAccountList.addItemDecoration(itemDecoration)
    }

    private fun getAdapter(): GenericAdapter<Group> {
        val updateItem: (item: Group, position: Int) -> Unit = { item, position ->
            item.selected = !item.selected
            viewModel.toggleItemSelection(item)
            adapter.notifyItemChanged(position)
        }
        return GenericAdapter(
            R.layout.settings_list_item,
            areItemsSame = { old, new ->
                old.id == new.id
            },
            areItemContentsEqual = { old, new ->
                old.selected == new.selected
            },
            onHold = updateItem,
            onClick = { item, position ->
                if (viewModel.isSelectionOn.value) {
                    updateItem(item, position)
                }
            }
        )
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        mainMenu = menu
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_delete -> {
                viewModel.deleteSelected()
                true
            }
            else -> {
                false
            }
        }
    }

}