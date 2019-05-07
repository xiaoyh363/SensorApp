package com.xiaoyh.retrofit.fragment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.RelativeLayout
import android.widget.Switch
import com.github.mikephil.charting.charts.LineChart
import com.google.gson.Gson
import com.xiaoyh.retrofit.R
import com.xiaoyh.retrofit.bean.request.SensorBean
import com.xiaoyh.retrofit.util.MyLineChart
import com.xiaoyh.retrofit.util.DialogUtil
import com.xiaoyh.retrofit.util.MySensor
import com.xiaoyh.retrofit.util.common.FileUtil
import com.xiaoyh.retrofit.util.common.LogUtil
import com.xiaoyh.retrofit.util.common.TimerUtil
import com.xiaoyh.retrofit.util.common.ToastUtil
import kotlinx.android.synthetic.main.fragment_sensor.*

class SensorFragment : Fragment(), SensorEventListener {

    private val gson: Gson by lazy { Gson() }
    private val fileName = "test.json"
    private val delay = 500L
    private val selectedCharts = mutableMapOf<SensorBean.Sensor, MyLineChart>() // 被选中的传感器 -> 对应的 LineChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_sensor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 添加文件数据
        fab_add.setOnClickListener {
            DialogUtil.createChartDialog(activity as AppCompatActivity) { items -> createLineChart(items) }
        }

        // 存储文件数据
        fab_save.setOnClickListener {
            if (selectedCharts.keys.isNotEmpty() && selectedCharts.keys.first().values.isNotEmpty()) {
                FileUtil.writeText(fileName, gson.toJson(SensorBean(delay, selectedCharts.keys.toList())))
                ToastUtil.show("${fileName}保存成功")
            }
        }

        // 读取文件数据
        fab_read.setOnClickListener {
            readLineChart(gson.fromJson(FileUtil.getText(fileName), SensorBean::class.java).sensors)
        }

        // 上传文件数据
        fab_upload.setOnClickListener { FileUtil.getFileTree { LogUtil.d(it.name) } }
    }

    private lateinit var sensorSwitch: Switch
    // Switch 事件
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sensor_menu, menu)
        // Switch 监听
        sensorSwitch = (menu.findItem(R.id.action_switch).actionView as Switch)
        // 打开 switch 时需要关闭fab，关闭时再打开
        sensorSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 注册被选择的传感器
                selectedCharts.keys.forEach {
                    MySensor.sm.registerListener(this, MySensor.sm.getDefaultSensor(it.type), MySensor.delay)
                }
                // 打开定时器
                TimerUtil.start({
                    selectedCharts.forEach { entry ->
                        MySensor.values[entry.key.type]?.let { array ->
                            entry.key.values.add(FloatArray(array.size) { array[it] })  // 将数据存储到bean中
                            entry.value.addData(array)                                  // 绘制折线图
                        }
                    }
                }, delay)
                fab_add.isEnabled = false
            } else {
                MySensor.sm.unregisterListener(this)
                TimerUtil.stop()
                fab_add.isEnabled = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorSwitch.isChecked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        selectedCharts.clear()
    }

    // 传感器接收到的数据
    override fun onSensorChanged(event: SensorEvent) {
        MySensor.values[event.sensor.type] = event.values
    }

    // 绘制折线图
    private fun createLineChart(items: List<Int>) {
        // 每次选择时要清除前面的配置
        sensor_list.removeAllViews()
        selectedCharts.clear()
        // 给被选中的 type 创建对应的折线图
        items.forEach {
            // 布局配置
            val child = LineChart(context)
            val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.lineChartHeight))
            lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.lineChartGAP)
            child.layoutParams = lp
            // 为 bean 对折线图建立映射
            val sensor = SensorBean.Sensor(it)
            selectedCharts[sensor] = MyLineChart(child, MySensor.totalNames[it] + MySensor.units[it])
            // 在布局中添加折线图
            sensor_list.addView(child)
        }
    }

    // 读取折线图
    private fun readLineChart(sensors: List<SensorBean.Sensor>) {
        // 每次选择时要清除前面的配置
        sensor_list.removeAllViews()
        selectedCharts.clear()
        // 给被选中的 type 创建对应的折线图
        sensors.forEach {
            // 布局配置
            val child = LineChart(context)
            val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.lineChartHeight))
            lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.lineChartGAP)
            child.layoutParams = lp
            // 为 bean 对折线图建立映射
            val lineChart = MyLineChart(child, MySensor.totalNames[it.type] + MySensor.units[it.type])
            lineChart.addDatas(it.values)
            selectedCharts[it] = lineChart
            // 在布局中添加折线图
            sensor_list.addView(child)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}