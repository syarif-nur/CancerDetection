package com.dicoding.asclepius.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.asclepius.data.CancerRepository
import com.dicoding.asclepius.data.ICancerRepository

class ResultViewModel(private val repository: ICancerRepository) : ViewModel() {
    suspend fun getArticleList() = repository.getArticleList().asLiveData()
}