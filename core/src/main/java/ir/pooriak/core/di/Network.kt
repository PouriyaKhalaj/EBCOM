package ir.pooriak.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import ir.pooriak.core.BuildConfig
import ir.pooriak.core.base.interceptor.RequestHeaderInterceptor
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit


/**
 * Created by POORIAK on 13,September,2023
 */
private const val TEN_SECONDS = 60L

val coreNetworkModule = module {
    /**
     * providing network dependencies
     */
    single { provideOkHttpClient(context = androidContext()) }
    single { provideRetrofit(okHttpClient = get(), BASE_URL = BuildConfig.BASE_URL) }
}

private fun provideOkHttpClient(context: Context): OkHttpClient {
    val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .allEnabledTlsVersions()
        .allEnabledCipherSuites()
        .build()

    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TEN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TEN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TEN_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(logging)
        .addInterceptor(RequestHeaderInterceptor(context))
        .followRedirects(false)
        .connectionSpecs(Collections.singletonList(spec))

    return okHttpClient.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

