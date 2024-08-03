package com.ryadovoy.todo.data

import com.ryadovoy.todo.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
}
