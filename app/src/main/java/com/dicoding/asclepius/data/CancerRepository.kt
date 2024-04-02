package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.local.LocalDataSource
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.data.remote.RemoteDataSource
import com.dicoding.asclepius.data.remote.network.ApiResponse
import com.dicoding.asclepius.data.remote.response.ArticleList
import kotlinx.coroutines.flow.Flow

interface ICancerRepository {
    fun getAllCancer(): Flow<List<CancerEntity>>

    suspend fun insertCancer(cancer: CancerEntity)

    suspend fun getArticleList(): Flow<ApiResponse<List<ArticleList>>>
}

class CancerRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ICancerRepository {

    override fun getAllCancer(): Flow<List<CancerEntity>> {
        return localDataSource.getAllCancer()
    }


    override suspend fun insertCancer(cancer: CancerEntity) {
        localDataSource.insertCancer(cancer)
    }


    override suspend fun getArticleList(): Flow<ApiResponse<List<ArticleList>>> {
        return remoteDataSource.getCancerArticle()
    }
}