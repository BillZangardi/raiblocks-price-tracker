package me.billzangardi.raiblocks.data.services

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.billzangardi.raiblocks.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by zangardiw on 12/22/17.
 */

object CoindeskApiFactory {
    fun makeStarterService(): CoindeskApi {
        return makeCoindeskNetworkService(makeGson())
    }

    private fun makeCoindeskNetworkService(gson: Gson): CoindeskApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.COINDESK_API)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(CoindeskApi::class.java)
    }

    @Suppress("DEPRECATION")
    private fun makeOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
            httpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }
        return httpClientBuilder.build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .create()
    }
}