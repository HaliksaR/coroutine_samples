package ru.haliksar.coroutine_samples.scope

import kotlinx.coroutines.*


// два независимых скоупа
// если в одном из них произойдет ошибка, он не приостановит другой,
// так как они не связанны по иерархии
suspend fun example1() {
    val scope1 = CoroutineScope(Job())
    val scope2 = CoroutineScope(Job())
    scope1.launch {
        launch {
            delay(200)
            println("scope1 launch1")
        }
        launch {
            delay(300)
            println("scope1 launch3")
        }
        launch {
            delay(250)
            throw Exception("scope1 launch2")
        }
    }.join()
    scope2.launch {
        launch { println("scope2 launch1") }
        launch { println("scope2 launch2") }
    }.join()
}

// один скоуп, а именно launch скоуп который является дочерним к CoroutineScope
// в начале метода
// кажый следующий launch создает дочерний CoroutineScope к его родителю
// если хоть в одном launch случиться ошибка, то вверх по
// иерархии все аварийно завершится
fun CoroutineScope.example2() = launch {
    launch {
        launch {
            delay(200)
            println("scope1 launch1")
        }
        launch {
            delay(300)
            println("scope1 launch3")
        }
        launch {
            delay(250)
            throw Exception("scope1 launch2")
        }
    }.join()
    launch {
        launch { println("scope2 launch1") }
        launch { println("scope2 launch2") }
    }.join()
}

// почти аналогично example2, только мы сами (хрен знает зачем), привязали наш
// новый скоуп к контексту родительского
// кажый следующий launch создает дочерний CoroutineScope к его родителю
// если хоть в одном launch случиться ошибка, то вверх по
// иерархии все аварийно завершится
fun CoroutineScope.example3() = launch {
    val scope = CoroutineScope(coroutineContext)
    scope.launch {
        launch {
            delay(200)
            println("scope1 launch1")
        }
        launch {
            delay(300)
            println("scope1 launch3")
        }
        launch {
            delay(250)
            throw Exception("scope1 launch2")
        }
    }.join()
    launch {
        launch {
            delay(300)
            println("scope2 launch1")
        }
        launch {
            delay(300)
            println("scope2 launch2")
        }
    }.join()
}

fun main() = runBlocking<Unit> {
//    example1()
//    example2()
    example3()
}