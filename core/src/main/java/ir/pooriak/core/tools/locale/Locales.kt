package ir.pooriak.core.tools.locale

import java.util.Locale

/**
 * Created by POORIAK on 13,September,2023
 */
class Locales {

    companion object {
        val PERSIAN = Locale(PERSIAN_LANGUAGE_CODE, PERSIAN_COUNTRY_CODE)
        val ENGLISH = Locale(ENGLISH_LANGUAGE_CODE, ENGLISH_COUNTRY_CODE)

        private val rtlLanguage = hashSetOf(PERSIAN_LANGUAGE_CODE)

        fun RTL(): Set<String> {
            return rtlLanguage
        }

        fun getLocale(language: String): Locale {
            return when (language) {
                PERSIAN_LANGUAGE_CODE -> PERSIAN
                ENGLISH_LANGUAGE_CODE -> ENGLISH
                else -> PERSIAN
            }
        }
    }
}