package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {
    private val repository = CryptoRepository
    private val loadingFlow = MutableSharedFlow<State>()

    init {
        viewModelScope.launch {
            repository.loadData()
        }
    }

    val state: Flow<State> = repository.dataFlow
        .filter {
            it.isNotEmpty()
        }
        .map { State.Content(it) as State }
        .onStart {
            emit(State.Loading)
            println("onStart")
        }
        .onEach {
            println("on each")
        }.onCompletion {
            println("onCompletion $it")
        }.mergeWith(loadingFlow)

    fun refreshList() {
        viewModelScope.launch {
            loadingFlow.emit(State.Loading)
            repository.loadData()
        }
    }

    private fun Flow<State>.mergeWith(another: Flow<State>): Flow<State> =
        merge(this, another)

}

