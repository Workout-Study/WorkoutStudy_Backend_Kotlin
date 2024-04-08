package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.mate.*
import com.fitmate.fitgroupservice.service.FitMateService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitMateController(private val fitMateService: FitMateService) {

    /**
     * Register Fit mate Inbound api
     *
     * @param registerMateRequest Data about fit group and request user id
     * @return Boolean about register fit mate success with response entity
     */
    @PostMapping(GlobalURI.MATE_ROOT)
    fun registerFitMate(@RequestBody @Valid registerMateRequest: RegisterMateRequest): ResponseEntity<RegisterMateResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(fitMateService.registerFitMate(registerMateRequest))
    }

    /**
     * Get Fit mate list by group id Inbound api
     *
     * @param fitGroupId target fit group id
     * @return fit group id and fit mates details with response entity
     */
    @GetMapping(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE)
    fun getFitMateListByFitGroup(@PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long): ResponseEntity<FitMateDetailsResponse> {
        return ResponseEntity.ok().body(fitMateService.getFitMateListByFitGroup(fitGroupId))
    }

    /**
     * Delete Fit mate in fit group Inbound api
     *
     * @param fitGroupId target fit group id
     * @param deleteMateRequest request user id
     * @return Boolean about delete fit mate success with response entity
     */
    @DeleteMapping(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE)
    fun deleteFitMate(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @RequestBody @Valid deleteMateRequest: DeleteMateRequest
    ): ResponseEntity<DeleteMateResponse> {
        return ResponseEntity.ok().body(fitMateService.deleteFitMate(fitGroupId, deleteMateRequest))
    }
}