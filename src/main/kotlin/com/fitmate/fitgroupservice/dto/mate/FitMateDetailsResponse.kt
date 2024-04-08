package com.fitmate.fitgroupservice.dto.mate

data class FitMateDetailsResponse(val fitGroupId: Long,
                                  val fitLeaderDetail: FitLeaderDetailDto,
                                  val fitMateDetails: List<FitMateDetailDto>)
