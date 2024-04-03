package com.dicoding.asclepius.view.result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.remote.network.ApiResponse
import com.dicoding.asclepius.databinding.ActivityResultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val resulViewModel: ResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        val result = intent.getStringExtra(EXTRA_RESULT)
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }
        binding.resultText.text = result
        val resultAdapter = ResultAdapter()
        lifecycleScope.launch {
            try {
                val resultArticle = withContext(Dispatchers.IO) {
                    resulViewModel.getArticleList()
                }
                binding.progressBar.visibility = View.VISIBLE
                resultArticle.observe(this@ResultActivity) { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Success -> {
                            binding.progressBar.visibility = View.GONE
                            resultAdapter.differ.submitList(apiResponse.data)
                        }

                        ApiResponse.Empty -> binding.progressBar.visibility = View.GONE
                        is ApiResponse.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@ResultActivity,
                                "Oops Something Wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                with(binding.rvCancer) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = resultAdapter
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ResultActivity,
                    e.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }


}