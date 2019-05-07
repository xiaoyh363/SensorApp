package com.xiaoyh.retrofit.util

import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import com.xiaoyh.retrofit.util.common.ContextUtil

/***
 * 由于每个手机拥有的传感器都不一样
 * 所以需要先获取该手机已有的传感器列表，之后再创建其对应的值列表
 * 然后通过索引来进行操控
 ***/
object MySensor {

    /***
     * type 所对应的传感器的物理量
     *
     * 报告模式：1.连续模式；2.变化模式；3.单次模式；4.特殊模式
     * 本 APP 的传感器只包括前2种模式的传感器
     *
     * 连续模式：以恒定速率生成事件，其采样率等于 HAL 接口中的 sampling_period_ns
     * 变化模式：仅在测量值发生变化时生成事件，其采样率不会超过 sampling_period_ns
     *
     * 连续模式的传感器：加速度计，磁力计，陀螺仪，方向、压力、重力、线性加速度、旋转矢量传感器
     * 变化模式的传感器：光照、温度、距离、相对湿度传感器（5,7,8,12,13）
     ***/
    val totalNames = arrayOf(
        "",
        "加速度", "磁场强度", "方向", "角速度", "光照强度",
        "压力", "温度", "距离", "重力", "线性加速度",
        "旋转矢量", "相对湿度", "环境温度"
    )

    // 对应的单位
    val units = arrayOf(
        "",
        "(m/s²)", "(uT)", "(rad)", "(rad/s)", "(lux)",
        "(hPa)", "(℃)", "(m)", "(m/s²)", "(m/s²)",
        "", "(%)", "(℃)"
    )

    // 获取传感器管理者
    val sm = ContextUtil.getContext().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager

    /***
     * 传感器延迟，不同的传感器延迟也不同，应该与硬件厂商有关
     *          压力      磁场强度    其他      频率范围
     * normal   200ms      195ms     151ms    ( 5~10HZ )
     * ui       66ms       48ms      37ms     ( 15~26HZ )
     * game     38ms       19ms      18ms     ( 26~55HZ )
     * fastest  38ms       9ms       4ms      ( 26HZ、100~250HZ )
     * 速率越快似乎越耗电
     *
     * Android 不支持以高于 1000Hz 的频率生成事件。
     */
    const val delay = SensorManager.SENSOR_DELAY_NORMAL

    // 该手机已有传感器的type列表
    lateinit var types: List<Int>

    // 传感器对应的值域
    val values = mutableMapOf<Int, FloatArray>()
}