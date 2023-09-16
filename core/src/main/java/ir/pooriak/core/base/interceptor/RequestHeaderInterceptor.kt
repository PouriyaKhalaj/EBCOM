package ir.pooriak.core.base.interceptor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.webkit.WebSettings
import ir.pooriak.core.BuildConfig
import ir.pooriak.core.base.NetworkSSLHandshakeException
import ir.pooriak.core.tools.deviceIpAddress
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.net.ssl.SSLHandshakeException

/**
 * Created by POORIAK on 14,September,2023
 */
internal class RequestHeaderInterceptor(val context: Context) : Interceptor {

    @SuppressLint("HardwareIds")
    private val deviceId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )

    private val userAgent = try {
        WebSettings.getDefaultUserAgent(context)
            .plus(" AppVersion/${BuildConfig.appVersion}")
            .plus(" Ip/${deviceIpAddress(true)}")
            .plus(" Device/$deviceId")
    } catch (e: Exception) {
        (System.getProperty("https.agent") ?: "")
            .plus(" AppVersion/${BuildConfig.appVersion}")
            .plus(" Ip/${deviceIpAddress(true)}")
            .plus(" Device/$deviceId")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return doRequest(chain)
        } catch (e: SSLHandshakeException) {
            throw NetworkSSLHandshakeException()
        }

    }

    private fun doRequest(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val needHeaders = original.headers["needHeaders"] != "false"
        val requestBuilder = original.newBuilder().method(original.method, original.body)
            .removeHeader("needHeaders")

        if (needHeaders) {
            addHeaders(requestBuilder, original)
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun addHeaders(
        requestBuilder: Request.Builder,
        originalRequest: Request
    ) {
        val shouldAddAuthHeaders = originalRequest.headers["isAuthorizable"] != "false"

        requestBuilder.addHeader("Content-type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Accept-Language", "fa-IR")
            .addHeader("User-Agent", userAgent)
            .addHeader("Grpc-metadata-Platform", "ANDROID")
            .addHeader("Grpc-metadata-Device-ID", deviceId)
            .addHeader("Grpc-metadata-Version", BuildConfig.appVersion.toString())
            .addHeader("Grpc-metadata-Device-Info", "${Build.MANUFACTURER}-${Build.MODEL}")
            .removeHeader("isAuthorizable")

    }
}