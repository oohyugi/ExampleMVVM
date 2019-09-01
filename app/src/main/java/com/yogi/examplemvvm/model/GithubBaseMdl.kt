package com.yogi.examplemvvm.model

import com.google.gson.annotations.SerializedName

data class GithubBaseMdl<T>(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val items: T
)