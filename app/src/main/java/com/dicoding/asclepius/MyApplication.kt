package com.dicoding.asclepius

import android.app.Application
import com.dicoding.asclepius.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
//                    databaseModule,
//                    networkModule,
//                    repositoryModule,
//                    viewModelModule,
                    myModule
                )
            )
        }
    }
}