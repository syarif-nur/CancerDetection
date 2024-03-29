package com.dicoding.asclepius.helper

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun createOutputFileUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_" + ".jpg"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return Uri.fromFile(File(storageDir, imageFileName))
}


