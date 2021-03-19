package com.juno.weather.utils

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File

class FileUtils(private val context: Context) {
    fun createFile(fileName: String, fileContent: String) {
        createSafeFile(getInternalDir(), fileName, fileContent)
    }

    fun readFile(fileName: String): String? {
        return readSafeFile(getInternalDir(), fileName)
    }

    private fun getInternalDir(): File = context.filesDir

    private fun createSafeFile(dir: File?, fileName: String, fileContent: String) {
        val file = File(dir, fileName)
        if (file.exists()) {
            file.delete()
        }

        val encryptedFile = encryptedFile(file)

        encryptedFile.openFileOutput().use { writer ->
            writer.write(fileContent.toByteArray())
        }
    }

    private fun readSafeFile(dir: File?, fileName: String): String? {
        val file = File(dir, fileName)

        if (file.exists()) {
            val encryptedFile = encryptedFile(file)

            var result: String

            encryptedFile.openFileInput().use { inputStream ->
                result = inputStream.readBytes().decodeToString()
            }

            return result
        }

        return null
    }

    private fun encryptedFile(file: File): EncryptedFile {
        val mainKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedFile.Builder(
            context,
            file,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }
}