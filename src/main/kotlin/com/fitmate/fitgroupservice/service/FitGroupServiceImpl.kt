package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.event.event.DeleteFitGroupEvent
import com.fitmate.fitgroupservice.event.event.UpdateFitGroupEvent
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.entity.MultiMediaEndPoint
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupServiceImpl(private val fitGroupRepository: FitGroupRepository,
                          private val fitLeaderRepository: FitLeaderRepository,
                          private val fitMateRepository: FitMateRepository,
                          private val multiMediaEndPointRepository: MultiMediaEndPointRepository,
                          private val eventPublisher: ApplicationEventPublisher) : FitGroupService {

    /**
     * Register Fit Group service
     *
     * @param registerFitGroupRequest Data about fit group and leader user id
     * @return Boolean about register fit group success
     */
    @Transactional
    override fun registerFitGroup(registerFitGroupRequest: RegisterFitGroupRequest): RegisterFitGroupResponse {
        val savedFitGroup = fitGroupRepository.save(createFitGroup(registerFitGroupRequest));
        val savedFitLeader = fitLeaderRepository.save(createFitLeader(savedFitGroup, registerFitGroupRequest))

        registerFitGroupRequest.multiMediaEndPoints?.forEach {
            multiMediaEndPointRepository.save(
                    createMultiMediaEndPoint(it, savedFitGroup, registerFitGroupRequest.requestUserId)
            )
        }

        return RegisterFitGroupResponse(savedFitGroup.id != null && savedFitLeader.id != null)
    }

    private fun createMultiMediaEndPoint(endPoint: String, fitGroup: FitGroup, requestUserId: String): MultiMediaEndPoint {
        return MultiMediaEndPoint(fitGroup, endPoint, requestUserId)
    }

    private fun createFitLeader(savedFitGroup: FitGroup, registerFitGroupRequest: RegisterFitGroupRequest): FitLeader {
        return FitLeader(
                savedFitGroup,
                registerFitGroupRequest.requestUserId,
                registerFitGroupRequest.requestUserId
        )
    }

    private fun createFitGroup(registerFitGroupRequest: RegisterFitGroupRequest): FitGroup {
        return FitGroup(
                registerFitGroupRequest.fitGroupName,
                registerFitGroupRequest.penaltyAmount,
                registerFitGroupRequest.category,
                registerFitGroupRequest.introduction,
                registerFitGroupRequest.cycle ?: 1,
                registerFitGroupRequest.frequency,
                registerFitGroupRequest.maxFitMate,
                registerFitGroupRequest.requestUserId
        )
    }

    /**
     * Get Detail about fit group
     *
     * @param fitGroupId Request fit group id
     * @return Detail data about fit group
     */
    @Transactional(readOnly = true)
    override fun getFitGroupDetail(fitGroupId: Long): FitGroupDetailResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)
        if (fitGroup.isDeleted()) throw BadRequestException("Fit group already deleted")

        val fitLeader = findFitLeaderAndGet(fitGroup)

        return FitGroupDetailResponse(
                fitGroupId,
                fitLeader.fitLeaderUserId,
                fitGroup.fitGroupName,
                fitGroup.penaltyAmount,
                fitGroup.category,
                fitGroup.introduction,
                fitGroup.cycle,
                fitGroup.frequency,
                fitGroup.createdAt,
                fitGroup.maxFitMate,
                getFitMateCountByFitGroup(fitGroup),
                findMultiMediaEndPointsAndGet(fitGroup)
        )
    }

    private fun findFitGroupAndGet(fitGroupId: Long): FitGroup {
        return fitGroupRepository.findById(fitGroupId).orElseThrow { ResourceNotFoundException("Fit group does not exist") }
    }

    private fun findFitLeaderAndGet(fitGroup: FitGroup): FitLeader {
        return fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
                .orElseThrow { ResourceNotFoundException("Fit Leader does not exist") }
    }

    private fun findMultiMediaEndPointsAndGet(fitGroup: FitGroup): List<String> {
        val multiMediaEndpoints = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        return multiMediaEndpoints?.map { it.endPoint } ?: listOf()
    }

    private fun getFitMateCountByFitGroup(fitGroup: FitGroup): Int {
        return fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED) ?: 0
    }

    /**
     * Update Data About Fit Group service
     *
     * @param fitGroupId Request fit group id
     * @param updateFitGroupRequest Request Data about fit group
     * @return Boolean about update fit group success
     */
    @Transactional
    override fun updateFitGroup(fitGroupId: Long, updateFitGroupRequest: UpdateFitGroupRequest): UpdateFitGroupResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)
        if (fitGroup.isDeleted()) throw BadRequestException("Fit group already deleted")

        val fitLeader = findFitLeaderAndGet(fitGroup)
        checkFitLeaderWithRequestUser(fitLeader, updateFitGroupRequest.requestUserId)

        val presentFitMateCount = getFitMateCountByFitGroup(fitGroup)
        if (presentFitMateCount > updateFitGroupRequest.maxFitMate) throw BadRequestException("Fit mate count bigger then new max fit mate")

        fitGroup.update(updateFitGroupRequest)

        val multiMediaEndpoints = multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        multiMediaEndpoints?.forEach { it.delete() }

        updateFitGroupRequest.multiMediaEndPoints?.forEach {
            multiMediaEndPointRepository.save(
                    createMultiMediaEndPoint(it, fitGroup, updateFitGroupRequest.requestUserId)
            )
        }

        eventPublisher.publishEvent(UpdateFitGroupEvent(fitGroup.id!!))

        return UpdateFitGroupResponse(true)
    }

    /**
     * Delete fit group service
     *
     * @param fitGroupId Request fit group id
     * @param deleteFitGroupRequest Request user id
     * @return Boolean about delete fit group success
     */
    @Transactional
    override fun deleteFitGroup(fitGroupId: Long, deleteFitGroupRequest: DeleteFitGroupRequest): DeleteFitGroupResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)
        if (fitGroup.isDeleted()) throw BadRequestException("Fit group already deleted")

        val fitLeader = findFitLeaderAndGet(fitGroup)

        checkFitLeaderWithRequestUser(fitLeader, deleteFitGroupRequest.requestUserId)

        fitGroup.delete()
        fitLeader.delete()

        getFitMatesByFitGroup(fitGroup).forEach { it.delete() }

        eventPublisher.publishEvent(DeleteFitGroupEvent(fitGroup.id!!))

        return DeleteFitGroupResponse(fitGroup.isDeleted() && fitLeader.isDeleted())
    }

    private fun checkFitLeaderWithRequestUser(fitLeader: FitLeader, requestUserId: String) {
        if (fitLeader.fitLeaderUserId != requestUserId)
            throw BadRequestException("Request user does not match with fit leader. fit group only the leader can update.")
    }

    private fun getFitMatesByFitGroup(fitGroup: FitGroup): List<FitMate> {
        return fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED) ?: listOf()
    }
}