package ru.haliksar.coroutine_samples.continuation

import kotlinx.coroutines.*

// https://medium.com/androiddevelopers/the-suspend-modifier-under-the-hood-b7ce46af624f
private suspend fun compressImage(): ByteArray {
    delay(2000)
    return byteArrayOf(1, 0, 0, 0, 0, 1, 1, 0)
}

private suspend fun sendToServer(image: ByteArray) {
    delay(5000)
    println(image.size)
}

/*fun main() = runBlocking {
    println("Start")
    val image = compressImage()
    sendToServer(image)
    println("End")
}*/

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(this.coroutineContext + Job())
    scope.launch {
        try {
            launch { throw Exception("кот") }
        } catch (exception: Exception) {
            println("Ага ща поймаю ${exception.message}а")
        }
    }.join()
}