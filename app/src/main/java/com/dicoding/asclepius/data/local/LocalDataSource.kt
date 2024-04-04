package com.dicoding.asclepius.data.local

import Resource
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.data.local.room.CancerDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val cancerDao: CancerDao) {
    fun getAllCancer(): Flow<List<CancerEntity>> = cancerDao.getAllCancer()

    fun insertCancer(cancerList: CancerEntity) = cancerDao.insertCancer(cancerList)
}