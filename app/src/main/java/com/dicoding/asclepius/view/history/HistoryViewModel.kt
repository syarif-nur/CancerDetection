package com.dicoding.asclepius.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.asclepius.data.ICancerRepository

class HistoryViewModel(repository: ICancerRepository) : ViewModel() {
    val cancerList = repository.getAllCancer().asLiveData()
}