# Task 1 (Proof of Concept)
Shared Flow
---
> [!NOTE] 
> *Please refere to "com.example.mvvmapp.Lab1.1"*

line 9: initializing sharedFlow with buffer = 3 (saving the last 3 emitted values)

line 13: starting to emit 5 values 1 to 5 with delay 100ms between each emit so the total time will be (n-1)*100 = 400ms  //n = emits

**PS: the delay is after the emitions so thats why the last delay didn't count**

line 22: the first consumer arrives to collect (since the sharedFlow is hot stream _"flow"_ it will stream data as soon as it emits) so consumer 1 will collect all the data because it was collecting the moment the sharedFlow started to emit

line 29: the second consumer arrives but he got delayed 400ms

being delayed for 400ms made it just in time to collect the last emition from the stream which is (5) but with the cache beying 3, it will recover values (2,3,4)

![alt text](image.png)

---

State Flow
---
Definition: a hot stream that emits the last state of the data (collectors will collect the last changed data or else it will ignore it)

> [!NOTE] 
> *Please refere to "com.example.mvvmapp.Lab1.2"*

line 8: initializing stateFlow with init value = 0

line 10: starting to emit 5 values 1 to 5 with delay 100ms between each emit so the total time will be (n-1)*100 = 400ms  //n = emits

line 21: consumer (1) will start collecting (note the init value is 1 not 0 because of the definition I stated before)
// to get the value 0, consumer (1) should be moved to the next line after initialization so it holds the first state which is = 0

line 28: consumer (2) arrives after a delay of 1s so the first value it collected is 3 and continue to change as the emitter sends "different" values

![alt text](image-1.png)

**PS: if we added delay of 5s, we will find out that the *stateFlow* caches the last state of the stream which is 5, and so, consumer (1) & (2) will collect 5 as last state**

![alt text](image-2.png)

---
Task 2 & 3
===

converting API & DB calls to Flow and implementing search bar 


![alt text](<Recording 2025-03-18 013606.gif>)


*Please Check my ViewModel for Favorite and AllProducts Features in com.example.mvvmapp.features to check the conversion of LiveData to Flow criteria*

---

Task 4
===
||cellectLatest|collect|
|-------|:-----------:|:-----:|
|Def|Cancels the previous collection when a new value is emitted. Only the latest value is processed.|Collects all values emitted by the Flow sequentially.|
|UseCase|Searching query, prevent unwanted processes if the user changed the query that will result behind it process to a list|When you want to process every value emitted by the Flow, even if the processing of a previous value is still ongoing.|
|Code| 

```kotlin
flow {
    emit(1)
    delay(100)
    emit(2)
    delay(100)
    emit(3)
}.collect { value ->
    delay(200) 
    println("Collected: $value")
}
```
```
cellectLatest Output:
Collected: 1
Collected: 2
Collected: 3
```

```
cellect Output:
Collected: 3
```
---



||emit|tryEmit|
|-------|:-----------:|:-----:|
|Def|Passing a value through the data Flow|Returns Boolean for the ability to emit some value|
|UseCase|When you want to ensure the value is emitted, even if it requires waiting.|When you want to emit a value without blocking the coroutine, and you're okay with dropping the value if the buffer is full.|
|Code| 

```kotlin
val sharedFlow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1)
launch {
    sharedFlow.emit(1) 
    println("Emitted: 1")
    // Suspends if the buffer is full
}
```

```kotlin
val sharedFlow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1)
launch {
    val result = sharedFlow.tryEmit(1) 
    println("Emitted: 1, Success: $result")
    // Returns true if successful, false if buffer is full
}
```
