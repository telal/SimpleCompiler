package ru.telal.compiler.utils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.jvm.Throws

@Throws(IOException::class)
fun writeByteArrayToFile(code: ByteArray, filePath: String) {
    val file = File(filePath)
    file.parentFile.mkdirs()
    FileOutputStream(filePath).use { fos -> fos.write(code) }
}

fun getResourceAsText(path: String): String? = object {}.javaClass.getResource(path)?.readText()
