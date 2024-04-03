package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.dto.management.KickFitMateResponse
import com.fitmate.fitgroupservice.event.event.DeleteFitMateEvent
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitManagementServiceImpl(
    private val fitMateRepository: FitMateRepository,
    private val fitGroupRepository: FitGroupRepository,
    private val fitLeaderRepository: FitLeaderRepository,
    private val eventPublisher: ApplicationEventPublisher
) : FitManagementService {

    /**
     * Kick fit mate by leader service
     *
     * @param fitGroupId fit group to kick
     * @param userId user id who to be kick
     * @param kickFitMateRequest request user id ( must be leader id )
     * @return Boolean about kick fit mate success
     */
    @Transactional
    override fun kickFitMate(
        fitGroupId: Long,
        userId: String,
        kickFitMateRequest: KickFitMateRequest
    ): KickFitMateResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)
        if (fitGroup.isDeleted) throw BadRequestException("Fit group already deleted")

        val fitLeader = getFitLeaderByFitGroup(fitGroup)
        if (fitLeader.fitLeaderUserId != kickFitMateRequest.requestUserId) throw BadRequestException("Only the fit leader can kick a fit mate")

        val fitMate = getFitMateByFitGroupAndUserId(fitGroup, userId)
        fitMate.kick(kickFitMateRequest.requestUserId)

        eventPublisher.publishEvent(DeleteFitMateEvent(fitGroup.id!!, fitMate.id!!))

        return KickFitMateResponse(fitMate.isDeleted)
    }

    private fun getFitMateByFitGroupAndUserId(fitGroup: FitGroup, userId: String): FitMate =
        fitMateRepository.findByFitGroupAndFitMateUserIdAndState(
            fitGroup,
            userId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).orElseThrow { ResourceNotFoundException("The fit mate user id you wish to kick does not in fit group") }

    private fun getFitLeaderByFitGroup(fitGroup: FitGroup): FitLeader =
        fitLeaderRepository.findByFitGroupAndState(
            fitGroup,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).orElseThrow { throw ResourceNotFoundException("Fit Group Leader does not exist") }

    private fun findFitGroupAndGet(fitGroupId: Long): FitGroup =
        fitGroupRepository.findById(fitGroupId)
            .orElseThrow { ResourceNotFoundException("Fit group does not exist") }
}