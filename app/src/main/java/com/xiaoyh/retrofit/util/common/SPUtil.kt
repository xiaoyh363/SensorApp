package com.xiaoyh.retrofit.util.common

import android.preference.PreferenceManager

object SPUtil {

    private val sp = PreferenceManager.getDefaultSharedPreferences(ContextUtil.getContext())
    private val edit = sp.edit()

    fun <E> put(key: String, value: E, isCommit: Boolean) {
        when (value) {
            is String -> edit.putString(key, value)
            is Long -> edit.putLong(key, value)
            is Int -> edit.putInt(key, value)
            is Float -> edit.putFloat(key, value)
            is Boolean -> edit.putBoolean(key, value)
        }
        if (isCommit) {
            edit.commit()
        } else {
            edit.apply()
        }
    }

    fun <E> put(key: String, value: E) {
        this.put(key, value, false)
    }

    fun getString(key: String): String? {
        return sp.getString(key, null)
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, -1)
    }

    fun getLong(key: String): Long {
        return sp.getLong(key, -1)
    }

    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun getFloat(key: String): Float {
        return sp.getFloat(key, -1f)
    }

    fun getAll(): MutableMap<String, *>? {
        return sp.all
    }

    fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    fun remove(key: String, isCommit: Boolean) {
        edit.remove(key)
        if (isCommit) {
            edit.commit()
        } else {
            edit.apply()
        }
    }

    fun remove(key: String) {
        this.remove(key, false)
    }

    fun clear(isCommit: Boolean) {
        edit.clear()
        if (isCommit) {
            edit.commit()
        } else {
            edit.apply()
        }
    }

    fun clear() {
        this.clear(false)
    }
}