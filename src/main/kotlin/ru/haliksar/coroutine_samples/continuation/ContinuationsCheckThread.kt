package ru.haliksar.coroutine_samples.continuation

import kotlinx.coroutines.*

private suspend fun compressImage(): ByteArray {
    println("compressImage ${Thread.currentThread()}")
    delay(2000)
    return byteArrayOf(1, 0, 0, 0, 0, 1, 1, 0)
}

private suspend fun sendToServer(image: ByteArray) {
    println("sendToServer ${Thread.currentThread()}")
    delay(5000)
    println(image.size)
}

// https://lankydan.dev/cancelling-child-coroutines
// https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
// https://dzone.com/articles/cancelling-coroutines
// https://proandroiddev.com/kotlin-coroutine-job-hierarchy-finish-cancel-and-fail-2d3d42a768a9
// https://nuancesprog.ru/p/7900/
fun CoroutineScope.blockedChecker() = launch {
    try {
        while (isActive) {
            delay(200)
            println("Im not blocked ${Thread.currentThread()}")
        }
    } catch (cancellationException: CancellationException) {
        println("Ok...")
    } finally {
        withContext(NonCancellable) {
            delay(2000)
            println("...")
        }
    }
}

fun main() = runBlocking {
    println("Start ${Thread.currentThread()}")
    val job = launch {
        while (isActive) {
            delay(500)
            println("Im not blocked ${Thread.currentThread()}")
        }
    }
    val image = compressImage()
    sendToServer(image)
    println("End")
    job.cancel()
}