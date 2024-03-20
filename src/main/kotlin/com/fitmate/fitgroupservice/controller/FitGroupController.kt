package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.service.FitGroupService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitGroupController(private val fitGroupService: FitGroupService) {

    /**
     * Register Fit Group Inbound api
     *
     * @param registerFitGroupRequest Data about fit group and leader user id
     * @return Boolean about register fit group success with response entity
     */
    @PostMapping(GlobalURI.GROUP_ROOT)
    fun registerFitGroup(@RequestBody @Valid registerFitGroupRequest: RegisterFitGroupRequest): ResponseEntity<RegisterFitGroupResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(fitGroupService.registerFitGroup(registerFitGroupRequest))
    }

    /**
     * Get Detail about fit group Inbound api
     *
     * @param fitGroupId Request fit group id
     * @return Detail data about fit group with response entity
     */
    @GetMapping("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}")
    fun getFitGroupDetail(@PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long): ResponseEntity<FitGroupDetailResponse> {
        return ResponseEntity.ok().body(fitGroupService.getFitGroupDetail(fitGroupId));
    }

    /**
     * Update Data About Fit Group Inbound api
     *
     * @param fitGroupId Request fit group id
     * @param updateFitGroupRequest Request Data about fit group
     * @return Boolean about update fit group success with response entity
     */
    @PutMapping("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}")
    fun updateFitGroup(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @RequestBody @Valid updateFitGroupRequest: UpdateFitGroupRequest
    ): ResponseEntity<UpdateFitGroupResponse> {
        return ResponseEntity.ok().body(fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest));
    }

    /**
     * Delete fit group inbound api
     *
     * @param fitGroupId Request fit group id
     * @param deleteFitGroupRequest Request user id
     * @return Boolean about delete fit group success with response entity
     */
    @DeleteMapping("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}")
    fun deleteFitGroup(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @RequestBody @Valid deleteFitGroupRequest: DeleteFitGroupRequest
    ): ResponseEntity<DeleteFitGroupResponse> {
        return ResponseEntity.ok().body(fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest));
    }
}