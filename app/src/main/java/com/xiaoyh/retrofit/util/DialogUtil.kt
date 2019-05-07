package com.xiaoyh.retrofit.util

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.xiaoyh.retrofit.R
import com.xiaoyh.retrofit.util.common.ContextUtil
import com.xiaoyh.retrofit.util.common.SPUtil

object DialogUtil {

    fun createChartDialog(activity: Activity, callBack: (items: List<Int>) -> Unit) {
        val choices = BooleanArray(MySensor.types.size) { SPUtil.getBoolean("sensor$it") }
        val items = Array(MySensor.types.size) { MySensor.totalNames[MySensor.types[it]] }
        val selected = mutableListOf<Int>()

        val dialog = AlertDialog.Builder(activity)
            .setTitle("请选择传感器")
            .setMultiChoiceItems(items, choices) { _, which, isChecked ->
                choices[which] = isChecked
            }
            .setPositiveButton("确定") { _, _ ->
                for ((i, choice) in choices.withIndex()) {
                    SPUtil.put("sensor$i", choice)
                    if (choice) selected.add(MySensor.types[i])
                }
                callBack(selected)
            }
            .setNegativeButton("取消", null)
            .create()
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextUtil.getColor(R.color.md_red_500))
    }
}