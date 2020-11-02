package ru.haliksar.coroutine_samples.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// https://proglib.io/p/kotlin-callbacks

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}

interface Operation<T> {
    fun performAsync(callback: (Result<T>) -> Unit)
    fun cancel() // отменяет текущую операцию
}

suspend fun <T> Operation<T>.perform(): T =
    suspendCoroutine { continuation: Continuation<T> ->
        performAsync { result: Result<T> ->
            when (result) {
                is Result.Success -> continuation.resume(result.data) // успешно, есть значение
                is Result.Error -> continuation.resumeWithException(result.error) // ошибка операции
            }
        }
    }

suspend fun <T> Operation<T>.performCancellable(): T =
    suspendCancellableCoroutine { continuation ->
        performAsync { result: Result<T> ->
            when (result) {
                is Result.Success -> continuation.resume(result.data) // успешно, есть значение
                is Result.Error -> continuation.resumeWithException(result.error) // ошибка операции
            }
        }
        continuation.invokeOnCancellation { cancel() }
    }

fun <T> Operation<T>.performFlow(): Flow<T> =
    callbackFlow {
        performAsync { result: Result<T> ->
            when (result) {
                is Result.Success -> offer(result.data) // успешно, есть значение
                is Result.Error -> close(result.error) // ошибка операции
            }
        }
        awaitClose { cancel() }
    }

fun main() = runBlocking {
    val deferred = async {
        delay(3000)
        object : Operation<Int> {
            override fun performAsync(callback: (Result<Int>) -> Unit) =
                callback(listOf(Result.Error(Exception("ошибочка")), Result.Success(55)).random())

            override fun cancel() =
                println("cancel")
        }
    }
    try {
        println(deferred.await().perform())
    } catch (exc: Exception) {
        println(exc.message)
    }
}