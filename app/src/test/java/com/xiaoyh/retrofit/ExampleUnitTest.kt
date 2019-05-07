package com.xiaoyh.retrofit

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import rx.Observable
import rx.Subscriber
import java.lang.Thread.sleep

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private var flag = true

    private val observable = Observable.create(Observable.OnSubscribe<Int> { subscriber ->
        while (flag) {
            sleep(1000)
            subscriber.onNext(0)
        }
        subscriber.onCompleted()
    })

    private val subscriber = object : Subscriber<Int>() {
        override fun onNext(t: Int) {
            println(t)
        }

        override fun onCompleted() {
            println("结束")
        }

        override fun onError(e: Throwable?) {
        }
    }

    @Test
    fun main() {
        println("主线程开始\n")
        cor()
        println("\n主线程执行完毕")
    }

    // 但是这个函数是堵塞线程的
    private fun cor() = runBlocking {
        println("放上面的主协程开始")
        launch { doSth() }
        println("放下面的主协程开始") // 主协程在这里会立即执行，不会堵塞
    }

    // 挂起函数
    private suspend fun doSth() {
        delay(2000L)
        println("耗时操作执行完毕")
    }
}
