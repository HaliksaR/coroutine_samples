package ru.haliksar.coroutine_samples.sandbox

import kotlinx.coroutines.*


class Scenario(
    private val usecase1: Usecase1,
    private val usecase2: Usecase2,
) {

    suspend operator fun invoke() = try {
        coroutineScope {
            listOf(
                async { usecase1() },
                async { usecase2() },
            ).awaitAll()
        }
    } catch (throwable: Throwable) {
        println("invoke " + throwable.message)
    }
}

class Usecase1 {
    suspend operator fun invoke() {
        withContext(Dispatchers.Default) {
            println("start1 ${Thread.currentThread()}")
            delay(100)
            println("end1  ${Thread.currentThread()}")
        }
    }
}

class Usecase2 {
    suspend operator fun invoke() {
        withContext(Dispatchers.Unconfined) {
            println("start2 ${Thread.currentThread()}")
            delay(1000)
            throw Exception("поймай")
            println("end2  ${Thread.currentThread()}")
        }
    }
}

fun main() = runBlocking<Unit> {
    try {
        coroutineScope {
            repeat(2) {
                launch(Dispatchers.IO) {
                    println(Thread.currentThread())
                    Scenario(Usecase1(), Usecase2())()
                }
            }
            delay(150)
            cancel()
        }
    } catch (throwable: Throwable) {
        println("main " + throwable.message)
    }
}