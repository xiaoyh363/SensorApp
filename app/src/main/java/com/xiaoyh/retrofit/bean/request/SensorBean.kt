package com.xiaoyh.retrofit.bean.request

/**
 * {
 *      delay : Int
 *      sensors : [
 *      {
 *          type : Int
 *          values : [
 *              Float[],
 *              Float[],
 *              ...
 *          ]
 *      },
 *      {
 *          type : Int
 *          values : [
 *              Float[],
 *              Float[],
 *              ...
 *          ]
 *      },
 *      ...
 *      ]
 * }
 */
data class SensorBean(var delay: Long = 500, val sensors: List<Sensor>) {
    data class Sensor(val type: Int, val values: MutableList<FloatArray> = mutableListOf())
}

