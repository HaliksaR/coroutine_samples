package ru.haliksar.coroutine_samples.exception

import kotlinx.coroutines.*


fun CoroutineScope.example1() = launch {
    launch {
        delay(100)
        println("launch1")
    }
    launch {
        delay(100)
        println("launch2")
    }
    launch {
        throw Exception("launch3")
    }
}

fun CoroutineScope.example2() = launch {
    val scope = CoroutineScope(Job())
    scope.launch {
        delay(100)
        println("launch1")
    }
    scope.launch {
        delay(100)
        println("launch2")
    }
    scope.launch {
        throw Exception("launch3")
    }
}

fun CoroutineScope.example3() = launch {
    val scope = CoroutineScope(Job())
    val job1 = scope.launch {
        delay(100)
        println("launch1")
    }
    val job2 = scope.launch {
        delay(100)
        println("launch2")
    }
    val job3 = scope.launch {
        throw Exception("launch3")
    }
    job1.join()
    job2.join()
    job3.join()
}

suspend fun example4() {
    val scope = CoroutineScope(SupervisorJob())
    val job1 = scope.launch {
        delay(100)
        println("launch1")
    }
    val job2 = scope.launch {
        delay(100)
        println("launch2")
    }
    val job3 = scope.launch {
        throw Exception("launch3")
    }
    job1.join()
    job2.join()
    job3.join()
}

suspend fun example5() {
    coroutineScope {
        val job1 = launch {
            delay(100)
            println("launch1")
        }
        val job2 = launch {
            delay(100)
            println("launch2")
        }
        val job3 = launch {
            throw Exception("launch3")
        }
        job1.join()
        job2.join()
        job3.join()
    }
}

suspend fun example6() {
    supervisorScope {
        val job1 = launch {
            delay(100)
            println("launch1")
        }
        val job2 = launch {
            delay(100)
            println("launch2")
        }
        val job3 = launch {
            throw Exception("launch3")
        }
        job1.join()
        job2.join()
        job3.join()
    }
}

suspend fun example7() {
    val scope = CoroutineScope(SupervisorJob())
    try {
        val job1 = scope.launch {
            delay(100)
            println("launch1")
        }
        val job2 = scope.launch {
            delay(100)
            println("launch2")
        }
        val job3 = scope.launch {
            throw Exception("launch3")
        }
        job1.join()
        job2.join()
        job3.join()
    } catch (exc: Exception) {
        println(exc.message)
    }
}

suspend fun example8() {
    coroutineScope {
        try {
            launch(SupervisorJob()) {
                val job2 = launch {
                    delay(100)
                    println("launch2")
                }
                val job3 = launch { throw Exception("launch3") }
                job2.join()
                job3.join()
            }.join()
        } catch (exc: Exception) {
            println(exc.message)
        }
    }
}

val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
    println("Ага ща поймаю ${throwable.message}а")
}

suspend fun example9() {
    supervisorScope {
        launch(handler) { throw Exception("кот") }.join()
    }
}

suspend fun example10() {
    coroutineScope {
        launch(handler) { throw Exception("кот") }.join()
    }
}

suspend fun example11() {
    val scope = CoroutineScope(SupervisorJob() + handler)
    scope.launch { throw Exception("кот") }.join()
}

suspend fun example12() {
    val scope = CoroutineScope(Job())
    scope.launch {
        try {
            launch { throw Exception("кот") }
        } catch (exception: Exception) {
            println("Ага ща поймаю ${exception.message}а")
        }
    }.join() // remove
    /**
     * [kotlinx.coroutines.JobSupport.join]
     **/
}

fun main() = runner(12)

fun runner(num: Int) = runBlocking<Unit> {
    when (num) {
        1 -> example1()
        2 -> example2()
        3 -> example3()
        4 -> example4()
        5 -> example5()
        6 -> example6()
        7 -> example7()
        8 -> example8()
        9 -> example9()
        10 -> example10()
        11 -> example11()
        12 -> example12()
    }
}