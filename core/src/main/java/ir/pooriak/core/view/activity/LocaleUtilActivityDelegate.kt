package ir.pooriak.core.view.activity

import android.app.Activity
import android.content.Context
import java.util.Locale

/**
 * Created by POORIAK on 13,September,2023
 */
interface LocaleUtilActivityDelegate {
    fun setLocale(activity: Activity, newLocale: Locale)
    fun attachBaseContext(newBase: Context): Context
    fun onPaused()
    fun onResumed(activity: Activity)
    fun onCreate(activity: Activity)
}