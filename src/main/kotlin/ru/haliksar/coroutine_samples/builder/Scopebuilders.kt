package ru.haliksar.coroutine_samples.builder

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun main() = runBlocking<Unit> {
    launch { println("launch ${this is CoroutineScope}") }
    launch { println(workAsync().await()) }
    coroutineScope { println("coroutineScope ${this is CoroutineScope}") }
    supervisorScope { println("supervisorScope ${this is CoroutineScope}") }
    launch {
        broadcastChannel().consumeEach {
            println(it)
            cancel()
        }
    }
    launch {
        receiveChannel().consumeEach {
            println(it)
            cancel()
        }
    }
    launch {
        flow().collect {
            println(it)
            cancel()
        }
    }
    launch {
        callbackFlow().collect {
            println(it)
            cancel()
        }
    }
    withContext(Dispatchers.Default) { println("withContext ${this is CoroutineScope}") }
}

fun CoroutineScope.workAsync(): Deferred<String> = async {
    "async ${this is CoroutineScope}"
}

fun CoroutineScope.broadcastChannel(): BroadcastChannel<String> = broadcast {
    send("broadcast ${this is CoroutineScope}")
    awaitClose { cancel() }
}

fun CoroutineScope.receiveChannel(): ReceiveChannel<String> = produce {
    send("produce ${this is CoroutineScope}")
    awaitClose { cancel() }
}

fun CoroutineScope.flow(): Flow<String> = flow {
    emit("flow ${this is CoroutineScope}")
}

fun CoroutineScope.callbackFlow(): Flow<String> = callbackFlow {
    offer("callbackFlow ${this is CoroutineScope}")
    awaitClose { cancel() }
}

