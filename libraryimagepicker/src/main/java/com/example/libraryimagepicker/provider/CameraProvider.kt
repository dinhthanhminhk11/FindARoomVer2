package com.github.dhaval2404.imagepicker.provider

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat.requestPermissions
import com.example.libraryimagepicker.ImagePicker
import com.example.libraryimagepicker.ImagePickerActivity
import com.example.libraryimagepicker.R
import com.example.libraryimagepicker.provider.BaseProvider
import com.github.dhaval2404.imagepicker.util.FileUtil
import com.github.dhaval2404.imagepicker.util.IntentUtils
import com.github.dhaval2404.imagepicker.util.PermissionUtil
import java.io.File


class CameraProvider(activity: ImagePickerActivity) : BaseProvider(activity) {

    companion object {

        private const val STATE_CAMERA_FILE = "state.camera_file"

        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )

        private const val CAMERA_INTENT_REQ_CODE = 4281
        private const val PERMISSION_INTENT_REQ_CODE = 4282
    }

    private var mCameraFile: File? = null

    private val mFileDir: File

    init {
        val bundle = activity.intent.extras ?: Bundle()

        // Get File Directory
        val fileDir = bundle.getString(ImagePicker.EXTRA_SAVE_DIRECTORY)
        mFileDir = getFileDir(fileDir)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save Camera File
        outState.putSerializable(STATE_CAMERA_FILE, mCameraFile)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        // Restore Camera File
        mCameraFile = savedInstanceState?.getSerializable(STATE_CAMERA_FILE) as File?
    }

    fun startIntent() {
        if (!IntentUtils.isCameraAppAvailable(this)) {
            setError(R.string.error_camera_app_not_found)
            return
        }

        checkPermission()
    }

    private fun checkPermission() {
        if (isPermissionGranted(this)) {
            // Permission Granted, Start Camera Intent
            startCameraIntent()
        } else {
            // Request Permission
            requestPermission()
        }
    }

    private fun startCameraIntent() {
        // Create and get empty file to store capture image content
        val file = FileUtil.getImageFile(fileDir = mFileDir)
        mCameraFile = file

        // Check if file exists
        if (file != null && file.exists()) {
            val cameraIntent = IntentUtils.getCameraIntent(this, file)
            activity.startActivityForResult(cameraIntent!!, CAMERA_INTENT_REQ_CODE)
        } else {
            setError(R.string.error_failed_to_create_camera_image_file)
        }
    }

    private fun requestPermission() {
        requestPermissions(activity, getRequiredPermission(activity), PERMISSION_INTENT_REQ_CODE)
    }

    private fun isPermissionGranted(context: Context): Boolean {
        return getRequiredPermission(context).none {
            !PermissionUtil.isPermissionGranted(context, it)
        }
    }

    private fun getRequiredPermission(context: Context): Array<String> {
        return REQUIRED_PERMISSIONS.filter {
            PermissionUtil.isPermissionInManifest(context, it)
        }.toTypedArray()
    }

    fun onRequestPermissionsResult(requestCode: Int) {
        if (requestCode == PERMISSION_INTENT_REQ_CODE) {
            // Check again if permission is granted
            if (isPermissionGranted(this)) {
                // Permission is granted, Start Camera Intent
                startIntent()
            } else {
                // Exit with error message
                val error = getString(R.string.permission_camera_denied)
                setError(error)
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_INTENT_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                handleResult()
            } else {
                setResultCancel()
            }
        }
    }

    private fun handleResult() {
        activity.setImage(Uri.fromFile(mCameraFile))
    }

    override fun onFailure() {
        delete()
    }

    fun delete() {
        mCameraFile?.delete()
        mCameraFile = null
    }
}
