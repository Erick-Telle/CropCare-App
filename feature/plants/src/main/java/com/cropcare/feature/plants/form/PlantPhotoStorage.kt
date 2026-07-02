package com.cropcare.feature.plants.form

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object PlantPhotoStorage {

    private const val PHOTOS_DIR = "plant_photos"

    fun getPhotosDir(context: Context): File {
        val dir = File(context.filesDir, PHOTOS_DIR)
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun createPhotoFile(context: Context): File {
        val name = "plant_${System.currentTimeMillis()}.jpg"
        return File(getPhotosDir(context), name)
    }

    fun copyUriToStorage(context: Context, uri: Uri): String? = runCatching {
        val destFile = createPhotoFile(context)
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        } ?: return null
        destFile.absolutePath
    }.getOrNull()

    fun deletePhoto(path: String?) {
        if (path.isNullOrBlank()) return
        runCatching { File(path).delete() }
    }
}
