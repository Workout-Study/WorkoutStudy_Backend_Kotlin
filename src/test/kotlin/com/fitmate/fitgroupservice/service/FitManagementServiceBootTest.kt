package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.management.KickFitMateRequest
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.BankCode
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.repository.BankCodeRepository
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class FitManagementServiceBootTest {

    @Autowired
    private lateinit var fitManagementService: FitManagementService

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var fitMateRepository: FitMateRepository

    @Autowired
    private lateinit var bankCodeRepository: BankCodeRepository

    private val fitMateUserId = "fitMateUserId"
    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val penaltyAccountBankCode = "090"
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

    private lateinit var bankCode: BankCode
    private lateinit var fitGroup: FitGroup
    private lateinit var fitLeader: FitLeader
    private lateinit var fitMate: FitMate

    @BeforeEach
    fun createTestFitGroup() {
        bankCode = bankCodeRepository.findByCode(penaltyAccountBankCode).get()

        val fitGroup = FitGroup(
            fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, "test"
        )

        val savedFitGroup = fitGroupRepository.save(fitGroup)

        val fitLeader = FitLeader(savedFitGroup, requestUserId, "test")

        this.fitLeader = fitLeaderRepository.save(fitLeader)

        this.fitGroup = savedFitGroup

        this.fitMate = fitMateRepository.save(FitMate(savedFitGroup, fitMateUserId, fitMateUserId))
    }

    @Test
    @DisplayName("[통합][Service] Kick fit mate - 성공 테스트")
    fun `kick fit mate service success test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        //when then
        Assertions.assertDoesNotThrow {
            fitManagementService.kickFitMate(
                fitGroup.id!!,
                fitMate.fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Kick fit mate fit group does not exist - 실패 테스트")
    fun `kick fit mate service fit group does not exist fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitManagementService.kickFitMate(
                -1,
                fitMateUserId,
                kickFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Kick fit mate fit group already deleted - 실패 테스트")
    fun `kick fit mate service fit group already deleted fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitGroup.delete()
        fitGroupRepository.save(fitGroup)

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
    @DisplayName("[통합][Service] Kick fit mate fit leader not exist - 실패 테스트")
    fun `kick fit mate service fit leader not exist fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)

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
    @DisplayName("[통합][Service] Kick fit mate request user and fit leader not matched - 실패 테스트")
    fun `kick fit mate service request user and fit leader not matched fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)

        val wrongFitLeader = FitLeader(fitGroup, "notMatchedLeader", "test")
        fitLeaderRepository.save(wrongFitLeader)

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
    @DisplayName("[통합][Service] Kick fit mate fit mate user id not in fit group - 실패 테스트")
    fun `kick fit mate service fit mate user id not in fit group fail test`() {
        //given
        val kickFitMateRequest = KickFitMateRequest(
            requestUserId,
        )

        fitMate.delete()
        fitMateRepository.save(fitMate)

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