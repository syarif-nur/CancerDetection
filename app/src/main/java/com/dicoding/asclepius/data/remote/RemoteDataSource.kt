package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.data.remote.network.ApiResponse
import com.dicoding.asclepius.data.remote.network.ApiService
import com.dicoding.asclepius.data.remote.response.ArticleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getCancerArticle(): Flow<ApiResponse<List<ArticleList>>> {
        return flow {
            try {
                val response = apiService.getCancerArticle()
                val dataArray = response.articles
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}