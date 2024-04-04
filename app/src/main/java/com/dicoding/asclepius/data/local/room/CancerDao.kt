package com.dicoding.asclepius.data.local.room

import Resource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.CancerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CancerDao {

    @Query("SELECT * FROM cancer_list")
    fun getAllCancer(): Flow<List<CancerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCancer(cancer: CancerEntity)

}