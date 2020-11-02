package ru.haliksar.coroutine_samples.sandbox

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class UseCase {

    private var scopeJob: CoroutineScope? = null

    companion object {
        val instance = UseCase()
    }

    suspend operator fun invoke(
        handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _: CoroutineContext, _: Throwable -> }
    ) {
        scopeJob = CoroutineScope(Job() + handler)
        scopeJob?.launch {
            listOf(
                async {
                    println("start1")
                    delay(100)
                    throw Exception("поймай")
                    println("end1")
                },
                async {
                    println("start2")
                    delay(1000)
                    println("end2")
                },
            ).forEach { it.await() }
        }?.join()
    }

    fun cancel() = scopeJob?.coroutineContext?.cancelChildren()
}

private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
    println(throwable.message)
}

fun main() = runBlocking<Unit> {
    val usecase = UseCase.instance
    usecase.invoke(handler)
    yield()
    delay(250)
    usecase.cancel()
    val usecase2 = UseCase.instance
    usecase2.invoke(handler)
    yield()
    delay(250)
    usecase2.cancel()
}