package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.BankCode
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@ExtendWith(MockitoExtension::class)
class FitManagementServiceTest {

    @InjectMocks
    private lateinit var fitManagementService: FitManagementServiceImpl

    @Mock
    private lateinit var fitGroupRepository: FitGroupRepository

    @Mock
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Mock
    private lateinit var fitMateRepository: FitMateRepository

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    private val fitMateUserId = 9756
    private val requestUserId = 11422
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val bankCode = BankCode("090", "카카오뱅크")
    private val penaltyAccount = "3333-03-5367420"
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L
    private val fitLeaderId = 3L
    private val fitMateId = 5L
    private val maxFitMate = 20
    private val presentFitMateCount = 7
    private lateinit var fitGroup: FitGroup
    private lateinit var fitLeader: FitLeader
    private lateinit var fitMate: FitMate

    @BeforeEach
    fun setFitGroupAndFitLeader() {
        fitGroup = FitGroup(
            fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, requestUserId.toString()
        )

        fitGroup.id = fitGroupId

        fitLeader = FitLeader(fitGroup, requestUserId, requestUserId.toString())

        fitLeader.id = fitLeaderId

        fitMate = FitMate(fitGroup, fitMateUserId, fitMateUserId.toString())

        fitMate.id = fitMateId
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate - 성공 테스트")
    fun `kick fit mate service success test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, fitMateUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.of(fitMate))
        //when then
        Assertions.assertDoesNotThrow {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate fit group does not exist - 실패 테스트")
    fun `kick fit mate service fit group does not exist fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate fit group already deleted - 실패 테스트")
    fun `kick fit mate service fit group already deleted fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate fit leader not exist - 실패 테스트")
    fun `kick fit mate service fit leader not exist fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitLeader.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate request user and fit leader not matched - 실패 테스트")
    fun `kick fit mate service request user and fit leader not matched fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        val notMatchedLeaderUserId = requestUserId % 2

        val wrongFitLeader = FitLeader(fitGroup, notMatchedLeaderUserId, notMatchedLeaderUserId.toString())

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(wrongFitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Kick fit mate fit mate user id not in fit group - 실패 테스트")
    fun `kick fit mate service fit mate user id not in fit group fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, fitMateUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }
}