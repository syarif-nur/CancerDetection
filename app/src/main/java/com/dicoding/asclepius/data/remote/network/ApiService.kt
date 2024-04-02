package com.dicoding.asclepius.data.remote.network

import com.dicoding.asclepius.data.remote.response.CancerResponseList
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?q=cancer&category=health&language=en&apiKey=2024bd0744f44f62808393e09ef66ca8")
    suspend fun getCancerArticle(): CancerResponseList
}