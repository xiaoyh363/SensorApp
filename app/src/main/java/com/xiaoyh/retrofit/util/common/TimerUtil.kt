package com.xiaoyh.retrofit.util.common

import java.util.*

object TimerUtil {

    private val timer: Timer by lazy { Timer() }

    private var timerTask: TimerTask? = null

    fun start(callback: () -> Unit, delay: Long, period: Long) {
        timerTask = object : TimerTask() {
            override fun run() {
                callback()
            }
        }
        timer.schedule(timerTask, delay, period)
    }

    fun start(callback: () -> Unit) {
        this.start(callback, 0, 500)
    }

    fun start(callback: () -> Unit, period: Long) {
        this.start(callback, 0, period)
    }

    fun stop() {
        timerTask?.cancel()
    }
}
