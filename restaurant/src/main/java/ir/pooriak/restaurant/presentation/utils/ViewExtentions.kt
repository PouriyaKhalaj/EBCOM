package ir.pooriak.restaurant.presentation.utils

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import ir.pooriak.restaurant.domain.model.Status

/**
 * Created by POORIAK on 17,September,2023
 */
fun Status?.toSpannableString(context: Context): SpannableString? {
    this?.run {
        return SpannableString(show).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(context, color)
                ),
                0,
                show.length,
                0
            )// set color
        }
    }
    return null
}