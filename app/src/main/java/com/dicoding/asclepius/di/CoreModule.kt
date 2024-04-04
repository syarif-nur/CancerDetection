package com.dicoding.asclepius.di

import androidx.room.Room
import com.dicoding.asclepius.data.ICancerRepository
import com.dicoding.asclepius.data.CancerRepository
import com.dicoding.asclepius.data.local.LocalDataSource
import com.dicoding.asclepius.data.local.room.CancerDatabase
import com.dicoding.asclepius.data.remote.RemoteDataSource
import com.dicoding.asclepius.data.remote.network.ApiService
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.result.ResultViewModel
import com.dicoding.asclepius.view.scan.ScanViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val myModule = module {
    single {
        Room.databaseBuilder(androidContext(), CancerDatabase::class.java, "cancer_database")
            .build()
            .cancerDao()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single<ICancerRepository> {
        CancerRepository(
            get(),
            get()
        )
    }
    viewModel { ResultViewModel(get()) }
    viewModel { ScanViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}
