package com.fitmate.fitgroupservice.dto.filter

data class FitGroupFilterRequest(
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
    val sort: String?
)
