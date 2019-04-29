package ua.com.ponomarenko.carmarket.data.network.repsonse

import com.google.gson.annotations.SerializedName

data class BaseResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("pageSize") val pageSize: Int,
        @SerializedName("totalPageCount") val totalPageCount: Int,
        @SerializedName("wkda") val wkda: Map<String, String>
)