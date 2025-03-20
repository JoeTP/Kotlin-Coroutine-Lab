package com.example.mvvmapp

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//Proof Of Concept for Shared Flow
fun main(): Unit = runBlocking {

    val sharedFlow = MutableSharedFlow<Int>( 3)


    //Emitter
    launch {
        for (i in 1..5) {
            sharedFlow.emit(i)
            println("Emitter emitted: $i")
            delay(100)
        }
    }

    //Consumer 1
    launch {
        sharedFlow.collect { value ->
            println("Collector 1 received: ===$value")
        }
    }

    //Consumer 2
    launch {
        delay(400)
        println("IM HEEEEEEEREEEEEEEE")
        sharedFlow.collect { value ->
            println("Collector 2 received: $value")
        }
    }

    delay(2000)

}