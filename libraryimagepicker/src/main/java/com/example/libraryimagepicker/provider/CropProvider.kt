package com.github.dhaval2404.imagepicker.provider

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.libraryimagepicker.ImagePicker
import com.example.libraryimagepicker.ImagePickerActivity
import com.example.libraryimagepicker.R
import com.example.libraryimagepicker.provider.BaseProvider
import com.github.dhaval2404.imagepicker.util.FileUtil
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException

class CropProvider(activity: ImagePickerActivity) : BaseProvider(activity) {

    companion object {
        private val TAG = CropProvider::class.java.simpleName

        private const val STATE_CROP_FILE = "state.crop_file"
    }

    private val mMaxWidth: Int
    private val mMaxHeight: Int

    private val mCrop: Boolean
    private val mCropAspectX: Float
    private val mCropAspectY: Float
    private var mCropImageFile: File? = null
    private val mFileDir: File

    init {
        val bundle = activity.intent.extras ?: Bundle()

        // Get Max Width/Height parameter from Intent
        mMaxWidth = bundle.getInt(ImagePicker.EXTRA_MAX_WIDTH, 0)
        mMaxHeight = bundle.getInt(ImagePicker.EXTRA_MAX_HEIGHT, 0)

        // Get Crop Aspect Ratio parameter from Intent
        mCrop = bundle.getBoolean(ImagePicker.EXTRA_CROP, false)
        mCropAspectX = bundle.getFloat(ImagePicker.EXTRA_CROP_X, 0f)
        mCropAspectY = bundle.getFloat(ImagePicker.EXTRA_CROP_Y, 0f)

        // Get File Directory
        val fileDir = bundle.getString(ImagePicker.EXTRA_SAVE_DIRECTORY)
        mFileDir = getFileDir(fileDir)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save crop file
        outState.putSerializable(STATE_CROP_FILE, mCropImageFile)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        // Restore crop file
        mCropImageFile = savedInstanceState?.getSerializable(STATE_CROP_FILE) as File?
    }

    fun isCropEnabled() = mCrop

    fun startIntent(uri: Uri) {
        cropImage(uri)
    }

    @Throws(IOException::class)
    private fun cropImage(uri: Uri) {
        val extension = FileUtil.getImageExtension(uri)
        mCropImageFile = FileUtil.getImageFile(fileDir = mFileDir, extension = extension)

        if (mCropImageFile == null || !mCropImageFile!!.exists()) {
            Log.e(TAG, "Failed to create crop image file")
            setError(R.string.error_failed_to_crop_image)
            return
        }

        val options = UCrop.Options()
        options.setCompressionFormat(FileUtil.getCompressFormat(extension))

        val uCrop = UCrop.of(uri, Uri.fromFile(mCropImageFile))
            .withOptions(options)

        if (mCropAspectX > 0 && mCropAspectY > 0) {
            uCrop.withAspectRatio(mCropAspectX, mCropAspectY)
        }

        if (mMaxWidth > 0 && mMaxHeight > 0) {
            uCrop.withMaxResultSize(mMaxWidth, mMaxHeight)
        }

        try {
            uCrop.start(activity, UCrop.REQUEST_CROP)
        } catch (ex: ActivityNotFoundException) {
            setError(
                "uCrop not specified in manifest file." +
                        "Add UCropActivity in Manifest" +
                        "<activity\n" +
                        "    android:name=\"com.yalantis.ucrop.UCropActivity\"\n" +
                        "    android:screenOrientation=\"portrait\"\n" +
                        "    android:theme=\"@style/Theme.AppCompat.Light.NoActionBar\"/>"
            )
            ex.printStackTrace()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                handleResult(mCropImageFile)
            } else {
                setResultCancel()
            }
        }
    }

    private fun handleResult(file: File?) {
        if (file != null) {
            activity.setCropImage(Uri.fromFile(file))
        } else {
            setError(R.string.error_failed_to_crop_image)
        }
    }

    override fun onFailure() {
        delete()
    }

    fun delete() {
        mCropImageFile?.delete()
        mCropImageFile = null
    }
}
