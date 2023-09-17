package ir.pooriak.core.tools.locale

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.Locale

/**
 * Created by POORIAK on 13,September,2023
 */
class LocaleUtils {

    companion object {
        private val SELECTED_LANGUAGE = "Locale.Utils.Selected.Language"
        private val SELECTED_COUNTRY = "Locale.Utils.Selected.Country"

        fun onAttach(context: Context): Context {
            val locale: Locale = loadLocale(context)
            return setLocale(context, locale)
        }

        private fun loadLocale(context: Context): Locale {
            val preferences: SharedPreferences = getPreferences(context)
            val language = preferences.getString(SELECTED_LANGUAGE, PERSIAN_LANGUAGE_CODE)
            val country = preferences.getString(SELECTED_COUNTRY, PERSIAN_COUNTRY_CODE)
            return Locale(language.toString(), country.toString())
        }

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(
                LocaleUtils::class.java.name, Context.MODE_PRIVATE
            )
        }

        fun getLocale(context: Context): Locale {
            return loadLocale(context)
        }

        fun setLocale(context: Context, locale: Locale): Context {
            persist(context, locale)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, locale)
            } else {
                updateResourcesLegacy(context, locale)
            }
        }

        fun isRTL(locale: Locale): Boolean {
            return Locales.RTL().contains(locale.language)
        }

        private fun persist(context: Context, locale: Locale) {
            getPreferences(context).edit()?.putString(SELECTED_LANGUAGE, locale.language)
                ?.putString(SELECTED_COUNTRY, locale.country)?.apply()
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, locale: Locale): Context {
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
            Locale.setDefault(locale)
            val resources = context.resources
            val displayMetrics = resources.displayMetrics
            val configuration = resources.configuration
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, displayMetrics)
            return context
        }
    }
}