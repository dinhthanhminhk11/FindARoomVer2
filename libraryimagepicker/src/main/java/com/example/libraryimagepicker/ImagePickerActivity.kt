package com.example.libraryimagepicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.libraryimagepicker.constant.ImageProvider
import com.github.dhaval2404.imagepicker.provider.CameraProvider
import com.github.dhaval2404.imagepicker.provider.CompressionProvider
import com.github.dhaval2404.imagepicker.provider.CropProvider
import com.github.dhaval2404.imagepicker.provider.GalleryProvider
import com.github.dhaval2404.imagepicker.util.FileUriUtils

class ImagePickerActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "image_picker"

        internal fun getCancelledIntent(context: Context): Intent {
            val intent = Intent()
            val message = context.getString(R.string.error_task_cancelled)
            intent.putExtra(ImagePicker.EXTRA_ERROR, message)
            return intent
        }
    }

    private var mGalleryProvider: GalleryProvider? = null
    private var mCameraProvider: CameraProvider? = null
    private lateinit var mCropProvider: CropProvider
    private lateinit var mCompressionProvider: CompressionProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBundle(savedInstanceState)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        mCameraProvider?.onSaveInstanceState(outState)
        mCropProvider.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun loadBundle(savedInstanceState: Bundle?) {
        // Create Crop Provider
        mCropProvider = CropProvider(this)
        mCropProvider.onRestoreInstanceState(savedInstanceState)

        // Create Compression Provider
        mCompressionProvider = CompressionProvider(this)

        // Retrieve Image Provider
        val provider: ImageProvider? =
            intent?.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PROVIDER) as ImageProvider?

        // Create Gallery/Camera Provider
        when (provider) {
            ImageProvider.GALLERY -> {
                mGalleryProvider = GalleryProvider(this)
                // Pick Gallery Image
                savedInstanceState ?: mGalleryProvider?.startIntent()
            }
            ImageProvider.CAMERA -> {
                mCameraProvider = CameraProvider(this)
                mCameraProvider?.onRestoreInstanceState(savedInstanceState)
                // Pick Camera Image
                savedInstanceState ?: mCameraProvider?.startIntent()
            }
            else -> {
                Log.e(TAG, "Image provider can not be null")
                setError(getString(R.string.error_task_cancelled))
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mCameraProvider?.onRequestPermissionsResult(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCameraProvider?.onActivityResult(requestCode, resultCode, data)
        mGalleryProvider?.onActivityResult(requestCode, resultCode, data)
        mCropProvider.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Handle Activity Back Press
     */
    override fun onBackPressed() {
        setResultCancel()
    }

    fun setImage(uri: Uri) {
        when {
            mCropProvider.isCropEnabled() -> mCropProvider.startIntent(uri)
            mCompressionProvider.isCompressionRequired(uri) -> mCompressionProvider.compress(uri)
            else -> setResult(uri)
        }
    }

    fun setCropImage(uri: Uri) {
        // Delete Camera file after crop. Else there will be two image for the same action.
        // In case of Gallery Provider, we will get original image path, so we will not delete that.
        mCameraProvider?.delete()

        if (mCompressionProvider.isCompressionRequired(uri)) {
            mCompressionProvider.compress(uri)
        } else {
            setResult(uri)
        }
    }

    fun setCompressedImage(uri: Uri) {
        // This is the case when Crop is not enabled

        // Delete Camera file after crop. Else there will be two image for the same action.
        // In case of Gallery Provider, we will get original image path, so we will not delete that.
        mCameraProvider?.delete()

        // If crop file is not null, Delete it after crop
        mCropProvider.delete()

        setResult(uri)
    }

    private fun setResult(uri: Uri) {
        val intent = Intent()
        intent.data = uri
        intent.putExtra(ImagePicker.EXTRA_FILE_PATH, FileUriUtils.getRealPath(this, uri))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    fun setResultCancel() {
        setResult(Activity.RESULT_CANCELED, getCancelledIntent(this))
        finish()
    }

    fun setError(message: String) {
        val intent = Intent()
        intent.putExtra(ImagePicker.EXTRA_ERROR, message)
        setResult(ImagePicker.RESULT_ERROR, intent)
        finish()
    }
}