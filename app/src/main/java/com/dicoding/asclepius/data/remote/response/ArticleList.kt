package com.dicoding.asclepius.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleList(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: Any? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("source")
    val source: SourceList? = null,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null
)