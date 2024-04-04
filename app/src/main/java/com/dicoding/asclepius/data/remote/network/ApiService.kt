package com.dicoding.asclepius.data.remote.network

import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.CancerResponseList
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?q=cancer&category=health&language=en&apiKey=" + BuildConfig.API_KEY)
    suspend fun getCancerArticle(): CancerResponseList
}