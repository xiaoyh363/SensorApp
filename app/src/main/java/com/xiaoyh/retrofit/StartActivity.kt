package com.xiaoyh.retrofit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //展示0.5s后前往主页
        Handler().postDelayed({
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
            finish()
        }, 500)
    }
}