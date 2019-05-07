package com.xiaoyh.retrofit.util.common

import java.io.File

object FileUtil {

    private fun getPath(): String {
        return ContextUtil.getContext().filesDir.path
    }

    private fun getFile(fileName: String): File {
        val f = File(getPath() + File.separator + fileName)
        if (!f.exists()) {
            f.createNewFile()
        }
        return f
    }

    fun getFileTree(fileName: String = "", walkFileTree: (file: File) -> Unit) {
        getFile(fileName).walk().filter { it.isFile }.forEach { walkFileTree(it) }
    }

    fun getText(fileName: String): String {
        return getFile(fileName).readText()
    }

    fun getBytes(fileName: String): ByteArray {
        return getFile(fileName).readBytes()
    }

    fun getLines(fileName: String): List<String> {
        return getFile(fileName).readLines()
    }

    fun writeText(fileName: String, text: String) {
        getFile(fileName).writeText(text)
    }

    fun writeBytes(fileName: String, bytes: ByteArray) {
        getFile(fileName).writeBytes(bytes)
    }

    fun appendText(fileName: String, text: String) {
        getFile(fileName).appendText(text)
    }

    fun appendBytes(fileName: String, bytes: ByteArray) {
        getFile(fileName).appendBytes(bytes)
    }

    fun clear(fileName: String) {
        getFile(fileName).writeText("")
    }

    fun delete(fileName: String): Boolean {
        return getFile(fileName).delete()
    }
}