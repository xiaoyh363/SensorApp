package com.xiaoyh.retrofit.util.common

import android.widget.Toast

object ToastUtil {

    fun show(msg: String) {
        val toast = Toast.makeText(ContextUtil.getContext(), "", Toast.LENGTH_SHORT)
        toast.setText(msg)
        toast.show()
    }
}