package com.yogi.examplemvvm.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yogi.examplemvvm.BuildConfig
import com.yogi.examplemvvm.model.GithubBaseMdl
import com.yogi.examplemvvm.model.GithubUserMdl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by oohyugi on 2019-08-31.
 * github: https://github.com/oohyugi
 */
interface ApiService {


    @GET("search/users?")
    suspend fun getListUser(
        @Query("q") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GithubBaseMdl<List<GithubUserMdl>>


    companion object Factory {

        fun makeApiServices(baseUrl: String): ApiService {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }


            val client = if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val request = chain.request().apply {
                            newBuilder().header(
                                "Cache-Control",
                                "public, max-age=" + 5
                            ).build()
                        }
                        chain.proceed(request)
                    }
                    .addInterceptor(interceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val request = chain.request().apply {
                            newBuilder().header(
                                "Cache-Control",
                                "public, max-age=" + 5
                            ).build()
                        }
                        chain.proceed(request)
                    }
                    .build()
            }

            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }

    }
}