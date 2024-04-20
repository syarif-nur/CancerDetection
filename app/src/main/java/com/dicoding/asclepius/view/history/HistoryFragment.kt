package com.dicoding.asclepius.view.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.Resource
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.view.result.ResultActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val historyAdapter = HistoryAdapter()
            historyAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, ResultActivity::class.java)
                intent.putExtra(
                    ResultActivity.EXTRA_RESULT,
                    "${selectedData.result} ${selectedData.score}"
                )
                intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, selectedData.imageUrl)
                startActivity(intent)
            }

            historyViewModel.cancerList.observe(viewLifecycleOwner) { cancer ->
                if (cancer != null) {
                    when (cancer) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            historyAdapter.differ.submitList(cancer.data)
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Oops Something Wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            with(binding.rvCancerHistory) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = historyAdapter
            }
        }
    }

}