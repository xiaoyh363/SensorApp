package com.xiaoyh.retrofit.fragment

import android.hardware.Sensor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.app.AppCompatActivity
import com.xiaoyh.retrofit.R
import com.xiaoyh.retrofit.util.MySensor
import kotlinx.android.synthetic.main.fragment_index.*

class IndexFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val tempSet = mutableSetOf<Int>()
        // 利用 set 的互异性，排除重复元素
        MySensor.sm.getSensorList(Sensor.TYPE_ALL).forEach { if (it.type < MySensor.totalNames.size) tempSet.add(it.type) }
        // 将集合排序后添加
        MySensor.types = tempSet.sorted()

        return inflater.inflate(R.layout.fragment_index, container, false)
    }

    // 必须在 layout 加载完后才能调用 widget
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        for (type in MySensor.types) {
            sensor_txv.append("${MySensor.totalNames[type]}\n")
        }
    }
}