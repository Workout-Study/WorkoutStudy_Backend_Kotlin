package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.mate.*

interface FitMateService {

    /**
     * Register Fit mate service
     *
     * @param registerMateRequest Data about fit group and request user id
     * @return Boolean about register fit mate success
     */
    fun registerFitMate(registerMateRequest: RegisterMateRequest): RegisterMateResponse

    /**
     * Get Fit mate list by group id service
     *
     * @param fitGroupId target fit group id
     * @return fit group id and fit mates details
     */
    fun getFitMateListByFitGroup(fitGroupId: Long): FitMateDetailsResponse

    /**
     * Delete Fit mate in fit group service
     *
     * @param fitGroupId target fit group id
     * @param deleteMateRequest request user id
     * @return Boolean about delete fit mate success
     */
    fun deleteFitMate(fitGroupId: Long, deleteMateRequest: DeleteMateRequest): DeleteMateResponse
}