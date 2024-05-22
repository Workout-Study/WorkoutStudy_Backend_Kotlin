package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.mate.DeleteMateRequest
import com.fitmate.fitgroupservice.dto.mate.RegisterMateRequest
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceAlreadyExistException
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
class FitMateServiceBootTest {

    @Autowired
    private lateinit var fitMateService: FitMateService

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var fitMateRepository: FitMateRepository

    @Autowired
    private lateinit var bankCodeRepository: BankCodeRepository

    private val requestUserId = 11422
    private val leaderUserId = 873
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val penaltyAccountBankCode = "090"
    private val penaltyAccount = "3333-03-5367420"
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val maxFitMate = 20
    private val presentFitMateCount = 7

    private lateinit var bankCode: BankCode
    private lateinit var fitGroup: FitGroup
    private lateinit var fitLeader: FitLeader

    @BeforeEach
    fun createTestFitGroup() {
        bankCode = bankCodeRepository.findByCode(penaltyAccountBankCode).get()

        val fitGroup = FitGroup(
            fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, requestUserId
        )

        val savedFitGroup = fitGroupRepository.save(fitGroup)

        val fitLeader = FitLeader(savedFitGroup, leaderUserId, requestUserId)

        this.fitLeader = fitLeaderRepository.save(fitLeader)

        this.fitGroup = savedFitGroup
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate - 성공 테스트")
    fun `register fit mate service success test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        //when then
        Assertions.assertDoesNotThrow { fitMateService.registerFitMate(registerFitMateRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate fit group does not exist - 실패 테스트")
    fun `register fit mate service fit group does not exist fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            -1
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate fit group already deleted - 실패 테스트")
    fun `register fit mate service fit group already deleted fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        fitGroup.delete()

        fitGroupRepository.save(fitGroup)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate user already included in fit group - 실패 테스트")
    fun `register fit mate service user already included in fit group fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        fitMateRepository.save(FitMate(fitGroup, requestUserId, requestUserId))

        //when then
        Assertions.assertThrows(ResourceAlreadyExistException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate leader can't register on fit mate - 실패 테스트")
    fun `register fit mate service leader can't register on fit mate fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            leaderUserId,
            fitGroup.id!!
        )

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Register fit mate fit group already full - 실패 테스트")
    fun `register fit mate service fit group already full fail test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(
            requestUserId,
            fitGroup.id!!
        )

        for (i in 1..maxFitMate) {
            fitMateRepository.save(FitMate(fitGroup, requestUserId + i, requestUserId + i))
        }

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.registerFitMate(
                registerFitMateRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] fit mate list 조회 - 성공 테스트")
    fun `get fit mate list service success test`() {
        //given

        for (i in 1..maxFitMate) {
            fitMateRepository.save(FitMate(fitGroup, requestUserId + i, requestUserId + i))
        }

        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] fit mate list 조회 empty mate - 성공 테스트")
    fun `get fit mate list service empty mate success test`() {
        //given
        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] fit mate list 조회 empty leader - 성공 테스트")
    fun `get fit mate list service empty leader success test`() {
        //given
        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)

        //when then
        Assertions.assertDoesNotThrow { fitMateService.getFitMateListByFitGroup(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] fit mate list 조회 fit group does not exist - 실패 테스트")
    fun `get fit mate list service fit group does not exist fail test`() {
        //given when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.getFitMateListByFitGroup(
                -1
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] fit mate list 조회 fit group already deleted - 실패 테스트")
    fun `get fit mate list service fit group already deleted fail test`() {
        //given
        fitGroup.delete()
        fitGroupRepository.save(fitGroup)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitMateService.getFitMateListByFitGroup(
                fitGroup.id!!
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit mate - 성공 테스트")
    fun `delete fit mate service success test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        fitMateRepository.save(FitMate(fitGroup, requestUserId, requestUserId))

        //when then
        Assertions.assertDoesNotThrow { fitMateService.deleteFitMate(fitGroup.id!!, deleteMateRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit mate fit group does not exist - 실패 테스트")
    fun `delete fit mate service fit group does not exist fail test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.deleteFitMate(-1, deleteMateRequest)
        }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit mate user are not included in fit group - 실패 테스트")
    fun `delete fit mate service user are not included in fit group test`() {
        //given
        val deleteMateRequest = DeleteMateRequest(requestUserId)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitMateService.deleteFitMate(fitGroup.id!!, deleteMateRequest)
        }
    }
}