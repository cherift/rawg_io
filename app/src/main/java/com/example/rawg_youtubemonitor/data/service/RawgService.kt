package com.example.rawg_youtubemonitor.data.service

import com.example.rawg_youtubemonitor.data.model.GetGameResponse
import com.example.rawg_youtubemonitor.data.model.GetVideoResponse
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgService {

    @GET("games/{id}/youtube")
    fun getGameVideos(@Path("id") id: String,
                      @Query("page") page: Int,
                      @Query("page_size") page_size: Int) : Single<GetVideoResponse>

    @GET("games")
    fun getSearchedGames(@Query("search") search: String,
                         @Query("page") page: Int) : Single<GetGameResponse>

    companion object {
        operator fun invoke() : RawgService {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(RawgService::class.java)
        }
    }
}