package com.harshk.splitmates.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    private val _splitFiles = MutableStateFlow<List<Group>>(emptyList())
    val splitFiles = _splitFiles.asStateFlow()
    var selectedList = HashMap<String, Group>()
    var isSelectionOn = MutableStateFlow(false)

    fun loadFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _splitFiles.emit(useCase.listFiles())
        }
    }

    fun toggleItemSelection(item: Group) {
        if (selectedList.containsKey(item.id)) {
            selectedList.remove(item.id)
            if (selectedList.size == 0) {
                isSelectionOn.value = false
            }
        } else {
            selectedList[item.id] = item
            isSelectionOn.value = true
        }
    }

    fun deleteSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteFiles(selectedList.values.toList())
            val updatedList = ArrayList<Group>()
            for (file in splitFiles.value) {
                if (!selectedList.containsKey(file.id)) {
                    updatedList.add(file)
                }
            }
            _splitFiles.emit(updatedList)
            selectedList.clear()
            isSelectionOn.value = false
        }
    }
}