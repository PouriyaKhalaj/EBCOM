package ir.pooriak.core.view.activity

import android.app.Activity
import android.content.Context
import android.view.View
import ir.pooriak.core.tools.locale.LocaleUtils
import java.util.Locale

/**
 * Created by POORIAK on 13,September,2023
 */
class LocaleUtilActivityDelegateImpl : LocaleUtilActivityDelegate {

    private var locale = Locale.getDefault()

    override fun setLocale(activity: Activity, newLocale: Locale) {
        LocaleUtils.setLocale(activity, newLocale)
        locale = newLocale
        if (locale.toString() != newLocale.toString()) activity.recreate()
    }

    override fun attachBaseContext(newBase: Context): Context {
        return LocaleUtils.onAttach(newBase)
    }

    override fun onPaused() {
        locale = Locale.getDefault()
    }

    override fun onResumed(activity: Activity) {
        if (locale == Locale.getDefault()) {
            return
        } else {
            activity.recreate()
        }
    }

    override fun onCreate(activity: Activity) {
        activity.window.decorView.layoutDirection =
            if (LocaleUtils.isRTL(Locale.getDefault())) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR

    }

}