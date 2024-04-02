package com.dicoding.asclepius.view.scan

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.ICancerRepository
import com.dicoding.asclepius.data.local.entity.CancerEntity

class ScanViewModel(private val repository: ICancerRepository) : ViewModel() {
    suspend fun insertCancerResult(cancerEntity: CancerEntity) {
        repository.insertCancer(cancerEntity)
    }
}