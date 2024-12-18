package com.sumin.coroutineflow.lessons.lesson9

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.Default)

suspend fun main() {

    val flow = MutableStateFlow<Int>(0)

    val producer = coroutineScope.launch {
        repeat(10){
            println("emit: $it")
            flow.emit(it)
            delay(200)
        }
    }

    val consumer = coroutineScope.launch {
        flow.collectLatest{
            println("collect: $it")
            delay(1000)
            println("after collect: $it")
        }
    }

    producer.join()
    consumer.join()
}
