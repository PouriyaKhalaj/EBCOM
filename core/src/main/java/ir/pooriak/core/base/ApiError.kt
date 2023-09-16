package ir.pooriak.core.base

import com.google.gson.annotations.SerializedName

/**
 * Created by POORIAK on 13,September,2023
 */
data class ApiError(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("details") val details: List<ErrorDetail>
)

class ErrorDetail(
    @SerializedName("@type") val type: String,
    @SerializedName("field") val field: String,
    @SerializedName("describ") val description: String,
    @SerializedName("message") val message: String?
)