package ir.pooriak.core.view.utils

import android.content.res.Resources
import android.os.Build
import android.widget.TextView
import androidx.annotation.StyleRes

/**
 * Created by POORIAK on 17,September,2023
 */
fun TextView.setTextAppearanceEx(@StyleRes styleRes: Int) = apply {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) setTextAppearance(context, styleRes)
    else setTextAppearance(styleRes)
}

val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()