package com.dicoding.asclepius.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.CancerEntity


@Database(entities = [CancerEntity::class], version = 1, exportSchema = false)
abstract class CancerDatabase : RoomDatabase() {
    abstract fun cancerDao(): CancerDao
}