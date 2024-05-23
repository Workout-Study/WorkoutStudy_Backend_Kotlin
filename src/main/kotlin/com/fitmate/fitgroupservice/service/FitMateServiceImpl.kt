package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.mate.*
import com.fitmate.fitgroupservice.event.event.DeleteFitMateEvent
import com.fitmate.fitgroupservice.event.event.RegisterFitMateEvent
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceAlreadyExistException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import com.fitmate.fitgroupservice.persistence.repository.UserForReadRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitMateServiceImpl(
    private val fitMateRepository: FitMateRepository,
    private val fitGroupRepository: FitGroupRepository,
    private val fitLeaderRepository: FitLeaderRepository,
    private val userForReadRepository: UserForReadRepository,
    private val eventPublisher: ApplicationEventPublisher
) : FitMateService {

    /**
     * Register Fit mate service
     *
     * @param registerMateRequest Data about fit group and request user id
     * @return Boolean about register fit mate success
     */
    @Transactional
    override fun registerFitMate(registerMateRequest: RegisterMateRequest): RegisterMateResponse {
        val fitGroup = findFitGroupAndGet(registerMateRequest.fitGroupId)
        if (fitGroup.isDeleted) throw BadRequestException("Fit group already deleted")

        fitMateRepository.findByFitGroupAndFitMateUserIdAndState(
            fitGroup,
            registerMateRequest.requestUserId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).ifPresent { throw ResourceAlreadyExistException("Request user already included in fit group") }

        fitLeaderRepository.findByFitGroupAndFitLeaderUserIdAndState(
            fitGroup,
            registerMateRequest.requestUserId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).ifPresent { throw BadRequestException("Fit Group Leader can't register on fit mate") }

        val presentFitMateCount =
            fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED) ?: 0
        if (presentFitMateCount >= fitGroup.maxFitMate) throw BadRequestException("fit group already full")

        val newFitMate =
            FitMate(fitGroup, registerMateRequest.requestUserId, registerMateRequest.requestUserId.toString())
        val savedFitMate = fitMateRepository.save(newFitMate)

        eventPublisher.publishEvent(RegisterFitMateEvent(fitGroup.id!!, savedFitMate.id!!))

        return RegisterMateResponse(savedFitMate.id != null)
    }

    private fun findFitGroupAndGet(fitGroupId: Long): FitGroup =
        fitGroupRepository.findById(fitGroupId)
            .orElseThrow { ResourceNotFoundException("Fit group does not exist") }


    /**
     * Get Fit mate list by group id service
     *
     * @param fitGroupId target fit group id
     * @return fit group id and fit mates details
     */
    @Transactional(readOnly = true)
    override fun getFitMateListByFitGroup(fitGroupId: Long): FitMateDetailsResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)
        if (fitGroup.isDeleted) throw BadRequestException("Fit group already deleted")

        val optionalFitLeader =
            fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)

        val fitMates = fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)

        val fitMatesDetails = fitMates?.map {
            val userForRead =
                userForReadRepository.findByUserIdAndState(it.fitMateUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
                    .orElseThrow { ResourceNotFoundException("user for read does not exist") }

            FitMateDetailDto(
                it.id!!,
                it.fitMateUserId,
                userForRead.nickname,
                it.createdAt
            )
        } ?: listOf()

        var fitLeaderUserForRead: UserForRead? = null

        val fitLeader: FitLeader? = if (optionalFitLeader.isPresent) {
            fitLeaderUserForRead =
                userForReadRepository.findByUserIdAndState(
                    optionalFitLeader.get().fitLeaderUserId,
                    GlobalStatus.PERSISTENCE_NOT_DELETED
                )
                    .orElseThrow { ResourceNotFoundException("user for read does not exist") }
            optionalFitLeader.get()
        } else null

        return FitMateDetailsResponse(
            fitGroup.id!!,
            FitLeaderDetailDto(fitLeader?.fitLeaderUserId, fitLeaderUserForRead?.nickname, fitLeader?.createdAt),
            fitMatesDetails
        )
    }

    /**
     * Delete Fit mate in fit group service
     *
     * @param fitGroupId target fit group id
     * @param deleteMateRequest request user id
     * @return Boolean about delete fit mate success
     */
    @Transactional
    override fun deleteFitMate(fitGroupId: Long, deleteMateRequest: DeleteMateRequest): DeleteMateResponse {
        val fitGroup = findFitGroupAndGet(fitGroupId)

        val fitMate = fitMateRepository.findByFitGroupAndFitMateUserIdAndState(
            fitGroup,
            deleteMateRequest.requestUserId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        ).orElseThrow { throw ResourceNotFoundException("Request user are not included in fit group") }

        fitMate.delete()

        eventPublisher.publishEvent(DeleteFitMateEvent(fitGroup.id!!, fitMate.id!!))

        return DeleteMateResponse(fitMate.isDeleted)
    }
}