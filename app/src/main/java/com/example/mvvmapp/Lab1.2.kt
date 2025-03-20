import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val stateFlow = MutableStateFlow(0)

    launch {
        for (i in 1..5) {
            stateFlow.value = i
            println("Emitter emitted: $i")
            delay(500)
        }
    }

    delay(5000)

    // First collector
    launch {
        stateFlow.collect { value ->
            println("Collector 1 received: ===$value")
        }
    }

    // Second collector (starts collecting after a delay)
    launch {
        delay(1000)
        println("IM HEEEEREEEE")
        stateFlow.collect { value ->
            println("Collector 2 received: $value")
        }
    }

    delay(3000)
}