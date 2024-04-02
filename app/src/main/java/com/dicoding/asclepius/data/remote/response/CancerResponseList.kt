package com.dicoding.asclepius.data.remote.response

import com.google.gson.annotations.SerializedName

data class CancerResponseList(
	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticleList>,

	@field:SerializedName("status")
	val status: String? = null
)



