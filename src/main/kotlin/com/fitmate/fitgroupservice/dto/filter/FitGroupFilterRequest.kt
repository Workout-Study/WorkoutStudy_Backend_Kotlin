package com.fitmate.fitgroupservice.dto.filter

data class FitGroupFilterRequest(
    val withMaxGroup: Boolean = true,
    val category: Int? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
    val sort: String? = null
)
