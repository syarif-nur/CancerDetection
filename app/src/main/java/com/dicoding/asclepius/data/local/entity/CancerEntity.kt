package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cancer_list")
data class CancerEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cancerId")
    var id: Long = 0,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "score")
    var score: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?,
)