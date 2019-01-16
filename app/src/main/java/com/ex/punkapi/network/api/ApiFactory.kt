package com.ex.punkapi.network.api

import android.content.Context
import com.ex.punkapi.BuildConfig
import com.ex.punkapi.common.Constants
import com.ex.punkapi.network.ApiService
import com.ex.punkapi.network.util.BooleanDeserializer
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

object ApiFactory {

    /**
     * 기본 레트로핏 빌더 (공통 설정 적용)
     *
     * @return 기본 설정이 적용된 레트로핏 빌더
     */
    private var sGson: Gson? = null


    val gson: Gson?
        @Synchronized get() {

            if (sGson == null) {
                sGson = GsonBuilder()
                    .setDateFormat(JSON_DATE_FORMAT)
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDeserializer())
                    .create()
            }
            return sGson
        }


    fun retrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createOkHttpClient(context: Context): OkHttpClient {

        val builder = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()

            var request: Request? = null


            if (request == null) {
                val builder = original.newBuilder()
                builder.method(original.method(), original.body())

                request = builder.build()
            }

            chain.proceed(request!!)
        }

        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.connectTimeout(20, TimeUnit.SECONDS)

        // 디버깅용 로거 활성화
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }


    fun createApiService(context: Context): ApiService {
        return retrofit(context).create(ApiService::class.java)
    }



}