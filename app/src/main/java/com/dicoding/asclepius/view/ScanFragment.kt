package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentScanBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.createOutputFileUri
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding

    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage(uri: Uri) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        imageClassifierHelper =
            ImageClassifierHelper(context = requireContext(), classifierListener = object :
                ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(result: List<Classifications>?, inference: Long) {
                    result?.let { it ->
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            println(it)
                            val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                            val displayResult = sortedCategories.joinToString("\n") {
                                it.label + " " + NumberFormat.getPercentInstance().format(it.score)
                                    .trim()
                            }
                            intent.putExtra(ResultActivity.EXTRA_RESULT, displayResult)
                            intent.putExtra(
                                ResultActivity.EXTRA_IMAGE_URI,
                                currentImageUri.toString()
                            )
                            startActivity(intent)
                        } else {
                            showToast(getString(R.string.image_classifier_failed))
                        }
                    }
                }
            })
        imageClassifierHelper.classifyStaticImage(uri)
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            startCropping()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val cropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val croppedImageUri =
                        result.data?.let { UCrop.getOutput(it) } ?: return@registerForActivityResult
                    currentImageUri = croppedImageUri
                    showImage()
                }

                AppCompatActivity.RESULT_CANCELED -> {
                    showImage()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_image_warning),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    private fun startCropping() {
        currentImageUri?.let { sourceUri ->
            val destinationUri = createOutputFileUri(requireContext())
            val intent = UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(16f, 9f)
                .withMaxResultSize(400, 400)
                .getIntent(requireContext())
            cropLauncher.launch(intent)
        }
    }


}