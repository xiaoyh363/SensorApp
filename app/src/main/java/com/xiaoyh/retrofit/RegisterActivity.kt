package com.xiaoyh.retrofit

import android.os.Bundle
import android.util.Base64
import com.xiaoyh.retrofit.bean.reception.BaseReception
import com.xiaoyh.retrofit.bean.request.UserBean
import com.xiaoyh.retrofit.mvp.base.BaseActivity
import com.xiaoyh.retrofit.mvp.presenter.CommonPresenter
import com.xiaoyh.retrofit.mvp.view.RetrofitView
import com.xiaoyh.retrofit.util.common.LogUtil
import com.xiaoyh.retrofit.util.common.StatusBarUtil
import com.xiaoyh.retrofit.util.common.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RetrofitView<BaseReception>, CommonPresenter>(), RetrofitView<BaseReception> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        confirm.setOnClickListener {
            val userNameText = userName.text.toString()
            val passWordBytes = passWord.text.toString().toByteArray()
            if (userNameText.isEmpty() || passWordBytes.isEmpty()) {
                ToastUtil.show("用户名和密码不能为空")
            } else {
                val passWord64 = Base64.encodeToString(passWordBytes, Base64.DEFAULT)
                presenter?.register(UserBean(userNameText, passWord64))
            }
        }
    }

    override fun onResult(result: BaseReception) {
        if (result.state == 200) {
            finish()
        }
        ToastUtil.show(result.info)
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
        LogUtil.d("RegisterActivity:$e")
    }

    override fun createView(): RetrofitView<BaseReception> {
        return this
    }

    override fun createPresenter(): CommonPresenter {
        return CommonPresenter()
    }
}