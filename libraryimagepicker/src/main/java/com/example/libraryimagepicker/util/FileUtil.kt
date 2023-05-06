package com.github.dhaval2404.imagepicker.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.StatFs
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtil {

    fun getImageFile(fileDir: File, extension: String? = null): File? {
        try {
            // Create an image file name
            val ext = extension ?: ".jpg"
            val fileName = getFileName()
            val imageFileName = "$fileName$ext"

            // Create Directory If not exist
            if (!fileDir.exists()) fileDir.mkdirs()

            // Create File Object
            val file = File(fileDir, imageFileName)

            // Create empty file
            file.createNewFile()

            return file
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    private fun getFileName() = "IMG_${getTimestamp()}"

    private fun getTimestamp(): String {
        val timeFormat = "yyyyMMdd_HHmmssSSS"
        return SimpleDateFormat(timeFormat, Locale.getDefault()).format(Date())
    }

    fun getFreeSpace(file: File): Long {
        val stat = StatFs(file.path)
        val availBlocks = stat.availableBlocksLong
        val blockSize = stat.blockSizeLong
        return availBlocks * blockSize
    }


    fun getImageResolution(context: Context, uri: Uri): Pair<Int, Int> {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val stream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(stream, null, options)
        return Pair(options.outWidth, options.outHeight)
    }

    fun getImageResolution(file: File): Pair<Int, Int> {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        return Pair(options.outWidth, options.outHeight)
    }

    fun getImageSize(context: Context, uri: Uri): Long {
        return getDocumentFile(context, uri)?.length() ?: 0
    }

    fun getTempFile(context: Context, uri: Uri): File? {
        try {
            val destination = File(context.cacheDir, "image_picker.png")

            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor ?: return null

            val src = FileInputStream(fileDescriptor).channel
            val dst = FileOutputStream(destination).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()

            return destination
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    fun getDocumentFile(context: Context, uri: Uri): DocumentFile? {
        var file: DocumentFile? = null
        if (isFileUri(uri)) {
            val path = FileUriUtils.getRealPath(context, uri)
            if (path != null) {
                file = DocumentFile.fromFile(File(path))
            }
        } else {
            file = DocumentFile.fromSingleUri(context, uri)
        }
        return file
    }

    @Suppress("DEPRECATION")
    fun getCompressFormat(extension: String): Bitmap.CompressFormat {
        return when {
            extension.contains("png", ignoreCase = true) -> Bitmap.CompressFormat.PNG
            extension.contains("webp", ignoreCase = true) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Bitmap.CompressFormat.WEBP_LOSSLESS
                } else {
                    Bitmap.CompressFormat.WEBP
                }
            }
            else -> Bitmap.CompressFormat.JPEG
        }
    }

    fun getImageExtension(file: File): String {
        return getImageExtension(Uri.fromFile(file))
    }

    fun getImageExtension(uriImage: Uri): String {
        var extension: String? = null

        try {
            val imagePath = uriImage.path
            if (imagePath != null && imagePath.lastIndexOf(".") != -1) {
                extension = imagePath.substring(imagePath.lastIndexOf(".") + 1)
            }
        } catch (e: Exception) {
            extension = null
        }

        if (extension == null || extension.isEmpty()) {
            // default extension for matches the previous behavior of the plugin
            extension = "jpg"
        }

        return ".$extension"
    }

    private fun isFileUri(uri: Uri): Boolean {
        return "file".equals(uri.scheme, ignoreCase = true)
    }
}
