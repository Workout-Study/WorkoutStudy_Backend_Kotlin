package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.dto.management.KickFitMateResponse

interface FitManagementService {

    /**
     * Kick fit mate by leader service
     *
     * @param fitGroupId fit group to kick
     * @param userId user id who to be kick
     * @param kickFitMateRequest request user id ( must be leader id )
     * @return Boolean about kick fit mate success
     */
    fun kickFitMate(fitGroupId: Long, userId: Int, kickFitMateRequest: KickFitMateRequest): KickFitMateResponse
}