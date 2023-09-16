package ir.pooriak.ebcom

import android.app.Application
import androidx.multidex.MultiDexApplication
import androidx.paging.ExperimentalPagingApi
import ir.pooriak.ebcom.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by POORIAK on 14,September,2023
 */
class EBCOM : Application() {
    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EBCOM)
            modules(applicationModule)
        }
    }
}