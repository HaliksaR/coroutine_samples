package ru.haliksar.coroutine_samples.benchmark

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


private const val count = 100_000
private const val delayTimeMs = 1L
private const val nRepeatDelay = 10

fun testSimpleCoroutine(dump: Boolean) = runner { coroutineScope: CoroutineScope, i: Int ->
    coroutineScope.launch(Dispatchers.IO) { if (dump) println("${i}: ${Thread.currentThread()}") }
}

fun testSimpleThread(dump: Boolean) = runner { _, i: Int ->
    Thread { if (dump) println("${i}: ${Thread.currentThread()}") }.start()
}

fun runner(callback: (CoroutineScope, Int) -> Unit) = measureTimeMillis {
    runBlocking { repeat(count) { callback(this, it) } }
}

fun coroutinesIoDispatcher() = runBlocking {
    repeat(count) {
        launch(Dispatchers.IO) {
            repeat(nRepeatDelay) { delay(delayTimeMs) }
        }
    }
}

fun coroutinesDefaultDispatcher() = runBlocking {
    repeat(count) {
        launch(Dispatchers.Default) {
            repeat(nRepeatDelay) { delay(delayTimeMs) }
        }
    }
}

fun coroutinesBlockingDispatcher() = runBlocking {
    repeat(count) {
        launch {
            repeat(nRepeatDelay) { delay(delayTimeMs) }
        }
    }
}

fun threads() {
    val threads = List(count) {
        thread(start = true) {
            repeat(nRepeatDelay) { sleep(delayTimeMs) }
        }
    }
    threads.forEach { it.join() }
}

fun threadsBase() {
    val threads = List(count) {
        Thread { repeat(nRepeatDelay) { sleep(delayTimeMs) } }
    }
    threads.forEach { it.start() }
    threads.forEach { it.join() }
}

// https://stackoverflow.com/questions/48106252/why-threads-are-showing-better-performance-than-coroutines
// https://www.baeldung.com/kotlin-threads-coroutines
// https://proandroiddev.com/kotlin-coroutines-and-threading-fundamentals-9fd0130437ae
// https://www.iditect.com/how-to/56515133.html
/*
* count = 100_000
*
* Coroutine 877 ms
* Thread 6952 ms
*
* coroutinesIoDispatcher 2604 ms
* coroutinesDefaultDispatcher 1056 ms
* coroutinesBlockingDispatcher 681 ms
* threads 7968 ms
* threadsBase 8410 ms
* */
@InternalCoroutinesApi
fun main() = runBlocking {
    val dump = false
    if (dump) {
        val coroutine = testSimpleCoroutine(dump)
        val thread = testSimpleThread(dump)
        println("Coroutine $coroutine ms")
        println("Thread $thread ms")
    } else {
        println("Coroutine ${testSimpleCoroutine(dump)} ms")
        println("Thread ${testSimpleThread(dump)} ms")
    }
    println("coroutinesIoDispatcher ${measureTimeMillis { coroutinesIoDispatcher() }} ms")
    println("coroutinesDefaultDispatcher ${measureTimeMillis { coroutinesDefaultDispatcher() }} ms")
    println("coroutinesBlockingDispatcher ${measureTimeMillis { coroutinesBlockingDispatcher() }} ms")
    println("threads ${measureTimeMillis { threads() }} ms")
    println("threadsBase ${measureTimeMillis { threadsBase() }} ms")
}