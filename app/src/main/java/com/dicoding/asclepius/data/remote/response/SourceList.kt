package com.dicoding.asclepius.data.remote.response

import com.google.gson.annotations.SerializedName

data class SourceList(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Any? = null
)
