package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.dto.management.KickFitMateResponse
import com.fitmate.fitgroupservice.service.FitManagementService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FitManagementController(private val fitManagementService: FitManagementService) {

    /**
     * Kick fit mate by leader inbound api
     *
     * @param fitGroupId fit group to kick
     * @param userId user id who to be kick
     * @param kickFitMateRequest request user id ( must be leader id )
     * @return Boolean about kick fit mate success with response entity
     */
    @DeleteMapping(
        GlobalURI.FIT_MATE_MANAGEMENT_ROOT +
                GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE +
                GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE
    )
    fun kickFitMate(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) userId: Int,
        @RequestBody @Valid kickFitMateRequest: KickFitMateRequest
    ): ResponseEntity<KickFitMateResponse> {
        return ResponseEntity.ok().body(fitManagementService.kickFitMate(fitGroupId, userId, kickFitMateRequest))
    }
}