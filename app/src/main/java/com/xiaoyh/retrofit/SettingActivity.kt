package com.xiaoyh.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.xiaoyh.retrofit.bean.request.TokenBean
import com.xiaoyh.retrofit.util.common.SPUtil
import com.xiaoyh.retrofit.util.common.StatusBarUtil
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        if (TokenBean.getInstance().isEmpty) {
            setting.removeView(logout)
        } else {
            logout.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logout -> {
                SPUtil.remove("appID")
                SPUtil.remove("token")
                TokenBean.getInstance().token = null
                finish()
            }
        }
    }
}