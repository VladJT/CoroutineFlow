package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    //  private val _state = MutableLiveData<State>(State.Initial)
    val state: Flow<State> = repository.getCurrencyList()
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
        }
}

