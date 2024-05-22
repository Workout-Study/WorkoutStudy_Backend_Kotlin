package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.mate.DeleteMateRequest
import com.fitmate.fitgroupservice.dto.mate.RegisterMateRequest
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceAlreadyExistException
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
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@ExtendWith(MockitoExtension::class)
class FitMateServiceTest {

    @InjectMocks
    private lateinit var fitMateService: FitMateServiceImpl

    @Mock
    private lateinit var fitGroupRepository: FitGroupRepository

    @Mock
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Mock
    private lateinit var fitMateRepository: FitMateRepository

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

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

        fitMate = FitMate(fitGroup, requestUserId, requestUserId.toString())

        fitMate.id = fitMateId
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate - 성공 테스트")
    fun `register fit mate service success test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, requestUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndFitLeaderUserIdAndState(
                fitGroup,
                requestUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(presentFitMateCount)
        Mockito.`when`(
            fitMateRepository.save(any(FitMate::class.java))
        ).thenReturn(fitMate)
        //when then
        Assertions.assertDoesNotThrow { fitMateService.registerFitMate(registerFitMateRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate fit group does not exist - 실패 테스트")
    fun `register fit mate service fit group does not exist fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate fit group already deleted - 실패 테스트")
    fun `register fit mate service fit group already deleted fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate user already included in fit group - 실패 테스트")
    fun `register fit mate service user already included in fit group fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, requestUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.of(fitMate))
        //when then
        Assertions.assertThrows(ResourceAlreadyExistException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate leader can't register on fit mate - 실패 테스트")
    fun `register fit mate service leader can't register on fit mate fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, requestUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndFitLeaderUserIdAndState(
                fitGroup,
                requestUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Register fit mate fit group already full - 실패 테스트")
    fun `register fit mate service fit group already full fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository
                .findByFitGroupAndFitMateUserIdAndState(fitGroup, requestUserId, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndFitLeaderUserIdAndState(
                fitGroup,
                requestUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(maxFitMate)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] fit mate list 조회 - 성공 테스트")
    fun `get fit mate list service success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(
            fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(listOf(fitMate))
        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[단위][Service] fit mate list 조회 empty mate - 성공 테스트")
    fun `get fit mate list service empty mate success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(
            fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(listOf())
        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[단위][Service] fit mate list 조회 empty leader - 성공 테스트")
    fun `get fit mate list service empty leader success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitLeaderRepository.findByFitGroupAndState(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.empty())
        Mockito.`when`(
            fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)
        ).thenReturn(listOf(fitMate))
        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[단위][Service] fit mate list 조회 fit group does not exist - 실패 테스트")
    fun `get fit mate list service fit group does not exist fail test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.getFitMateListByFitGroup(
                fitGroup.id!!
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] fit mate list 조회 fit group already deleted - 실패 테스트")
    fun `get fit mate list service fit group already deleted fail test`() {
        //given
        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.getFitMateListByFitGroup(
                fitGroup.id!!
            )
        }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit mate - 성공 테스트")
    fun `delete fit mate service success test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository.findByFitGroupAndFitMateUserIdAndState(
                fitGroup,
                requestUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.of(fitMate))
        //when then
        Assertions.assertDoesNotThrow { fitMateService.deleteFitMate(fitGroup.id!!, deleteMateRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit mate fit group does not exist - 실패 테스트")
    fun `delete fit mate service fit group does not exist fail test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.deleteFitMate(fitGroup.id!!, deleteMateRequest)
        }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit mate user are not included in fit group - 실패 테스트")
    fun `delete fit mate service user are not included in fit group test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroup.id!!)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(
            fitMateRepository.findByFitGroupAndFitMateUserIdAndState(
                fitGroup,
                requestUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )
        ).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.deleteFitMate(fitGroup.id!!, deleteMateRequest)
        }
    }
}