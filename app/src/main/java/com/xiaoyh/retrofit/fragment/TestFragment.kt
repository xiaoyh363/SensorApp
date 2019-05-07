package com.xiaoyh.retrofit.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.xiaoyh.retrofit.R
import com.xiaoyh.retrofit.bean.request.SensorBean
import com.xiaoyh.retrofit.util.common.FileUtil
import com.xiaoyh.retrofit.util.common.LogUtil
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    private val fileName = "serializable.json"
    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        write.setOnClickListener {
            FileUtil.clear(fileName)
            val serialBean = SensorBean(500, listOf(
                SensorBean.Sensor(1, mutableListOf(floatArrayOf(6f, 0f, 8f), floatArrayOf(0f, 6f, 8f))),
                SensorBean.Sensor(4, mutableListOf(floatArrayOf(1f, 1f, 1f), floatArrayOf(3f, 4f, 5f)))
            ))
            FileUtil.writeText(fileName, gson.toJson(serialBean))
            FileUtil.getFileTree { LogUtil.d(it.toString()) }
        }

        deserializable.setOnClickListener {
            val bean = gson.fromJson(FileUtil.getText(fileName), SensorBean::class.java)
            LogUtil.d(bean.toString())
        }
    }
}