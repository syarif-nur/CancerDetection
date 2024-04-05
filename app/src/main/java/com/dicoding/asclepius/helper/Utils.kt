package com.dicoding.asclepius.helper

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.dicoding.asclepius.data.local.Resource
import com.dicoding.asclepius.data.local.entity.CancerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun createOutputFileUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_" + ".jpg"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return Uri.fromFile(File(storageDir, imageFileName))
}

fun getCursorDataAsFlow(entityFlow: Flow<List<CancerEntity>>): Flow<Resource<List<CancerEntity>>> {
    return entityFlow.map { entities ->
        try {
            Resource.Success(entities.reversed())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}