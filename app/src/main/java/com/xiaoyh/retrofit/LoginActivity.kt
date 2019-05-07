package com.xiaoyh.retrofit

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.mvp.presenter.LoginPresenter
import com.xiaoyh.retrofit.mvp.view.RetrofitView
import com.xiaoyh.retrofit.mvp.base.BaseActivity
import com.xiaoyh.retrofit.util.common.LogUtil
import kotlinx.android.synthetic.main.activity_login.*
import com.xiaoyh.retrofit.bean.reception.LoginReception
import com.xiaoyh.retrofit.bean.request.TokenBean
import com.xiaoyh.retrofit.util.common.SPUtil
import com.xiaoyh.retrofit.util.common.StatusBarUtil
import com.xiaoyh.retrofit.util.common.ToastUtil

class LoginActivity : BaseActivity<RetrofitView<LoginReception>, LoginPresenter>(), RetrofitView<LoginReception>,
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        login.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login -> {
                val userNameText = userName.text.toString()
                val passWordBytes = passWord.text.toString().toByteArray()
                if (userNameText.isEmpty() || passWordBytes.isEmpty()) {
                    ToastUtil.show("用户名和密码不能为空")
                } else {
                    val passWord64 = Base64.encodeToString(passWordBytes, Base64.DEFAULT)
                    presenter?.login(UserBean(userNameText, passWord64))
                }
            }
            R.id.register -> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    override fun onResult(result: LoginReception) {
        when (result.state) {
            200 -> {
                SPUtil.put("appId", result.appId)
                SPUtil.put("token", result.token)
                TokenBean.getInstance().appId = result.appId
                TokenBean.getInstance().token = result.token
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
        ToastUtil.show(result.info)
    }

    override fun onCompleted() {
        LogUtil.d("响应结束")
    }

    override fun onError(e: Throwable) {
        LogUtil.d("onError:$e")
        ToastUtil.show("服务器出错")
    }

    override fun createView(): RetrofitView<LoginReception> {
        return this
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }
}
