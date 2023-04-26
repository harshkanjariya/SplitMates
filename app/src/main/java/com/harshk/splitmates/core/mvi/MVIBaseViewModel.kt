package com.harshk.splitmates.core.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MVIBaseViewModel<State: UiState, Event: UiEvent , Effect: UiEffect>: ViewModel() {
    abstract fun createInitialState(): State

    private val initialState by lazy { createInitialState() }
    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    init {
        subscribeEvents()
    }

    fun setState(reduce: State.() -> State) {
        Log.e("setState > 29", "$currentState")
        _uiState.value = currentState.reduce()
        Log.e("setState > 29", "$currentState")
    }

    fun setEvent(e: Event) {
        viewModelScope.launch {
            _event.emit(e)
        }
    }

    fun setEffect(e: Effect) {
        viewModelScope.launch {
            _effect.emit(e)
        }
    }

    abstract fun handleEvent(event: Event)

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }
}