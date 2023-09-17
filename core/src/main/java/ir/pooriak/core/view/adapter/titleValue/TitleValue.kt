package ir.pooriak.core.view.adapter.titleValue

import android.text.SpannableString
import androidx.annotation.StyleRes
import ir.pooriak.core.R

/**
 * Created by POORIAK on 17,September,2023
 */
sealed class TitleValue {
    data class Spannable(
        val title: SpannableString,
        val value: SpannableString,
        val endLine: Boolean = false,
    ) : TitleValue()

    class SpannableType(
        vararg val spans: SpanType,
        val endLine: Boolean = false,
    ) : TitleValue()

    data class StringStyle(
        val title: String,
        val value: String,
        val endLine: Boolean = false,
        @StyleRes val titleAppearance: Int = R.style.ebcom_Text_EnMedium14sp,
        @StyleRes val valueAppearance: Int = R.style.ebcom_Text_EnBold14sp
    ) : TitleValue()
}

sealed class SpanType {
    data class Title(val title: SpannableString) : SpanType()
    data class Value(val value: SpannableString) : SpanType()
}
