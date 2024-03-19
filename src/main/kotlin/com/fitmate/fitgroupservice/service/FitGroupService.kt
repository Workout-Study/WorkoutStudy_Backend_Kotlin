package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.group.*

interface FitGroupService {

    /**
     * Register Fit Group service
     *
     * @param registerFitGroupRequest Data about fit group and leader user id
     * @return Boolean about register fit group success
     */
    fun registerFitGroup(registerFitGroupRequest: RegisterFitGroupRequest): RegisterFitGroupResponse

    /**
     * Get Detail about fit group service
     *
     * @param fitGroupId Request fit group id
     * @return Detail data about fit group
     */
    fun getFitGroupDetail(fitGroupId: Long): FitGroupDetailResponse

    /**
     * Update Data About Fit Group service
     *
     * @param fitGroupId Request fit group id
     * @param updateFitGroupRequest Request Data about fit group
     * @return Boolean about update fit group success
     */
    fun updateFitGroup(fitGroupId: Long, updateFitGroupRequest: UpdateFitGroupRequest): UpdateFitGroupResponse

    /**
     * Delete fit group service
     *
     * @param fitGroupId Request fit group id
     * @param deleteFitGroupRequest Request user id
     * @return Boolean about delete fit group success
     */
    fun deleteFitGroup(fitGroupId: Long, deleteFitGroupRequest: DeleteFitGroupRequest): DeleteFitGroupResponse
}