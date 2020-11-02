package ru.haliksar.coroutine_samples.job

import kotlinx.coroutines.*

fun CoroutineScope.work(): Job =
    launch(start = CoroutineStart.LAZY) {
        delay(300)
        println("hello")
    }

fun CoroutineScope.work1(): Job =
    launch(start = CoroutineStart.LAZY) {
        delay(300)
        println("hello1")
    }

fun CoroutineScope.work2(): Job =
    launch {
        delay(300)
        println("hello2")
    }


fun main() = runBlocking {
    val job = work()
    job.start()
    println(job.isActive)
    job.join()
    println(job.isActive)

    println()

    val job1 = work1()
    job1.start()
    println(job1.isCancelled)
    job1.cancel()
    job.invokeOnCompletion { }
    println(job1.isCancelled)

    println()

    launch {
        println("1")
    }.join()
    launch {
        println("2")
    }.join()
    println("yield 2")

    println()

    launch {
        println("1")
    }
    launch {
        println("2")
    }
    yield()
    println("yield 2")

    println()

    launch {
        println("1")
    }.join()
    launch {
        println("2")
    }
    println("yield 2")
}