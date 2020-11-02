package ru.haliksar.coroutine_samples.context

import kotlinx.coroutines.*

//public interface Job : CoroutineContext.Element

fun example1() = runBlocking {
    this.coroutineContext // Любой CoroutineScope имеет CoroutineContext
    val job1: Job = launch {
        println("launch coroutineContext vs runBlocking coroutineContext")
        /*
        * Любой CoroutineContext является неизменным, но вы можете добавлять в него
        * элементы, создавая при этом новый CoroutineContext
        * */
        println(coroutineContext == this@runBlocking.coroutineContext)
        delay(300)
    }
    println("coroutineContext.job vs job1")
    println(coroutineContext.job.children.first() == job1) // Любая задача имеет свой CoroutineContext
}

fun example2() = runBlocking {
    launch { delay(300) }
    launch { delay(300) }
    launch { delay(300) }
    println("coroutineContext.jobs")
    println(coroutineContext.job.children.count())
}

fun example3() = runBlocking {
    val job1 = launch { delay(300) }
    val job2 = launch { delay(300) }
    val job3 = launch { delay(300) }
    coroutineContext.cancelChildren()
    println("coroutineContext.cancel")
    println("job1 ${job1.isCancelled}")
    println("job2 ${job2.isCancelled}")
    println("job3 ${job3.isCancelled}")
}

fun example4(): Unit = runBlocking {
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("example4") + Job())
    println("${coroutineContext[CoroutineName.Key]} is executing on thread : ${Thread.currentThread()}")
    println("${scope.coroutineContext[CoroutineName.Key]} is executing on thread : ${Thread.currentThread()}")
    scope.launch {
        println("${coroutineContext[CoroutineName.Key]} is executing on thread : ${Thread.currentThread()}")
    }
}

fun example5(): Unit = runBlocking {
    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("example4") + job)
    scope.launch {
        delay(300)
        println("...")
    }
    job.cancelChildren()
}

fun example6(): Unit = runBlocking {
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("example4") + Job())
    scope.launch {
        delay(300)
        println("...")
    }
    Dispatchers.IO.cancelChildren()
}

fun main() = runBlocking {
    example1()
    example2()
    example3()
    example4()
    example5()
    example6()
}