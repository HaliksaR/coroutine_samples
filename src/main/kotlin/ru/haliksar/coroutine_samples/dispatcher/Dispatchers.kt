package ru.haliksar.coroutine_samples.dispatcher

import kotlinx.coroutines.*


val myThreadPool: ExecutorCoroutineDispatcher = newFixedThreadPoolContext(3, "myThreadPool")

val mainThread = newSingleThreadContext("mainThread")

fun checkMainThread() = check(Thread.currentThread().name == "mainThread")

fun example1() = runBlocking {
    withContext(Dispatchers.IO) {
        println("thread : ${Thread.currentThread()}")
    }
    withContext(myThreadPool) {
        println("thread : ${Thread.currentThread()}")
    }
}

fun example2() = runBlocking {
    withContext(Dispatchers.IO) {
        withContext(mainThread) {
            checkMainThread()
            println("thread : ${Thread.currentThread()}")
        }
    }
}

fun main() = runBlocking {
    println("thread : ${Thread.currentThread()}")
    example1()
    example2()
}