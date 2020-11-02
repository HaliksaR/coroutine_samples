package ru.haliksar.coroutine_samples.sandbox

import kotlinx.coroutines.*


class Scenario(
    private val usecase1: Usecase1,
    private val usecase2: Usecase2,
) {

    suspend operator fun invoke() {
        coroutineScope {
            listOf(
                async { usecase1() },
                async { usecase2() },
            ).awaitAll()
        }
    }
}

class Usecase1 {
    suspend operator fun invoke() {
        println("start1")
        delay(100)
//        throw Exception("поймай")
        println("end1")
    }
}

class Usecase2 {
    suspend operator fun invoke() {
        println("start2")
        delay(1000)
        println("end2")
    }
}

fun main() = runBlocking<Unit> {
    try {
        launch {
            val usecase = Scenario(Usecase1(), Usecase2())
            val usecase2 = Scenario(Usecase1(), Usecase2())
            launch { usecase.invoke() }
            launch { usecase2.invoke() }
        }
        delay(10)
        cancel()
    } catch (throwable: Throwable) {
        println(throwable.message)
    }
}