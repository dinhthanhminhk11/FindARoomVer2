package com.github.dhaval2404.imagepicker.util

import android.annotation.SuppressLint
import android.media.ExifInterface
import android.util.Log
import java.io.File

object ExifDataCopier {

    @SuppressLint("NewApi")
    fun copyExif(filePathOri: File, filePathDest: File) {
        try {
            val oldExif = ExifInterface(filePathOri)
            val newExif = ExifInterface(filePathDest)
            val attributes: List<String> = listOf(
                "FNumber",
                "ExposureTime",
                "ISOSpeedRatings",
                "GPSAltitude",
                "GPSAltitudeRef",
                "FocalLength",
                "GPSDateStamp",
                "WhiteBalance",
                "GPSProcessingMethod",
                "GPSTimeStamp",
                "DateTime",
                "Flash",
                "GPSLatitude",
                "GPSLatitudeRef",
                "GPSLongitude",
                "GPSLongitudeRef",
                "Make",
                "Model",
                "Orientation"
            )
            for (attribute in attributes) {
                setIfNotNull(oldExif, newExif, attribute)
            }
            newExif.saveAttributes()
        } catch (ex: Exception) {
            Log.e("ExifDataCopier", "Error preserving Exif data on selected image: $ex")
        }
    }

    private fun setIfNotNull(oldExif: ExifInterface, newExif: ExifInterface, property: String) {
        if (oldExif.getAttribute(property) != null) {
            newExif.setAttribute(property, oldExif.getAttribute(property))
        }
    }
}
