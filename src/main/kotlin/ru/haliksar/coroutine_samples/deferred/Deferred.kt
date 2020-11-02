package ru.haliksar.coroutine_samples.deferred

import kotlinx.coroutines.*

fun CoroutineScope.workAsync(): Deferred<String> =
    async(start = CoroutineStart.LAZY) {
        delay(300)
        "hello"
    }

fun main() = runBlocking {
    val deferred = workAsync()
    deferred.start()
    println(deferred.await())
}