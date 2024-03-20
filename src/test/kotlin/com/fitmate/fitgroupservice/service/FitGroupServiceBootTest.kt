package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.group.DeleteFitGroupRequest
import com.fitmate.fitgroupservice.dto.group.RegisterFitGroupRequest
import com.fitmate.fitgroupservice.dto.group.UpdateFitGroupRequest
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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class FitGroupServiceBootTest {

    @Autowired
    private lateinit var fitGroupService: FitGroupService

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var fitMateRepository: FitMateRepository

    @Autowired
    private lateinit var multiMediaEndPointRepository: MultiMediaEndPointRepository

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val bankCode = "090"
    private val penaltyAccount = "3333-03-5367420"
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L
    private val maxFitMate = 20
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private lateinit var fitGroup: FitGroup

    private lateinit var fitLeader: FitLeader

    @BeforeEach
    fun createTestFitGroup() {
        val fitGroup = FitGroup(
            fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, "test"
        )

        val savedFitGroup = fitGroupRepository.save(fitGroup)

        val fitLeader = FitLeader(savedFitGroup, requestUserId, "test")

        this.fitLeader = fitLeaderRepository.save(fitLeader)

        this.fitGroup = savedFitGroup
    }

    @Test
    @DisplayName("[통합][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Register fit group null multi media - 성공 테스트")
    fun `register fit group service null multi media success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            null
        )

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Register fit group empty multi media - 성공 테스트")
    fun `register fit group service empty multi media success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            listOf()
        )

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Register fit group no resource bank code - 실패 테스트")
    fun `register fit group service no resource bank code fail test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            "wrongBankCode",
            penaltyAccount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.registerFitGroup(
                registerFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit group - 성공 테스트")
    fun `update fit group service success test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.updateFitGroup(fitGroup.id!!, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Update fit group not found - 실패 테스트")
    fun `update fit group service fit group not found fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.updateFitGroup(
                -1,
                updateFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit group already deleted - 실패 테스트")
    fun `update fit group service fit group already deleted fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        fitGroup.delete()
        fitGroupRepository.save(fitGroup)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitGroupService.updateFitGroup(
                fitGroup.id!!,
                updateFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit leader does not exist - 실패 테스트")
    fun `update fit group service fit leader does not exist fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.updateFitGroup(
                fitGroupId,
                updateFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit leader does not match - 실패 테스트")
    fun `update fit group service fit leader does not match fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val newFitGroup = fitGroupRepository.save(
            FitGroup(
                fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                    ?: 1, frequency, maxFitMate, notMatchedLeaderUserId
            )
        )
        val notMatchFitLeader = FitLeader(newFitGroup, notMatchedLeaderUserId, notMatchedLeaderUserId)
        fitLeaderRepository.save(notMatchFitLeader)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitGroupService.updateFitGroup(
                newFitGroup.id!!,
                updateFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit present fit mate count bigger then new max fit mate - 실패 테스트")
    fun `update fit group service present fit mate count bigger then new max fit mate fail test`() {
        //given
        val newMaxFitMate = 2

        for (i in 0..newMaxFitMate) {
            fitMateRepository.save(FitMate(fitGroup, requestUserId + i, requestUserId + i))
        }

        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                ?: 1, frequency, newMaxFitMate, multiMediaEndPoint
        )

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitGroupService.updateFitGroup(
                fitGroup.id!!,
                updateFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Update fit group no resource bank code - 실패 테스트")
    fun `update fit group service no resource bank code fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId, fitGroupName, penaltyAmount, "wrongBankCode", penaltyAccount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.updateFitGroup(
                fitGroup.id!!,
                updateFitGroupRequest
            )
        }
    }


    @Test
    @DisplayName("[통합][Service] Get fit group detail data - 성공 테스트")
    fun `get fit group detail service success test`() {
        //given
        fitMateRepository.save(FitMate(fitGroup, requestUserId, requestUserId))
        multiMediaEndPointRepository.save(MultiMediaEndPoint(fitGroup, requestUserId, requestUserId))

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] Get fit group detail data fit mate null count - 성공 테스트")
    fun `get fit group detail service fit mate null count success test`() {
        //given
        multiMediaEndPointRepository.save(MultiMediaEndPoint(fitGroup, requestUserId, requestUserId))

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] Get fit group detail data multi media end point null - 성공 테스트")
    fun `get fit group detail service multi media end point null success test`() {
        //given
        fitMateRepository.save(FitMate(fitGroup, requestUserId, requestUserId))

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] Get fit group detail data fit group does not exist - 실패 테스트")
    fun `get fit group detail service fit group does not exist fail test`() {
        //given when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(-1) }
    }

    @Test
    @DisplayName("[통합][Service] Get fit group detail data fit group already deleted - 실패 테스트")
    fun `get fit group detail service fit group already deleted fail test`() {
        //given
        fitGroup.delete()
        fitGroupRepository.save(fitGroup)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[통합][Service] Get fit group detail data fit leader does not exist - 실패 테스트")
    fun `get fit group detail service fit leader does not exist fail test`() {
        //given
        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit group - 성공 테스트")
    fun `delete fit group service success test`() {
        //given
        fitMateRepository.save(FitMate(fitGroup, requestUserId, requestUserId))

        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit group fit mate null - 성공 테스트")
    fun `delete fit group service fit mate null success test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit group fit group does not exist - 실패 테스트")
    fun `delete fit group service fit group does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.deleteFitGroup(
                -1,
                deleteFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit group already deleted - 실패 테스트")
    fun `delete fit group service fit group already deleted fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitGroup.delete()
        fitGroupRepository.save(fitGroup)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitGroupService.deleteFitGroup(
                fitGroup.id!!,
                deleteFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit leader does not exist - 실패 테스트")
    fun `delete fit group service fit leader does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            fitGroupService.deleteFitGroup(
                fitGroupId,
                deleteFitGroupRequest
            )
        }
    }

    @Test
    @DisplayName("[통합][Service] Delete fit leader does not match - 실패 테스트")
    fun `delete fit group service fit leader does not match fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val newFitGroup = fitGroupRepository.save(
            FitGroup(
                fitGroupName, penaltyAmount, bankCode, penaltyAccount, category, introduction, cycle
                    ?: 1, frequency, maxFitMate, notMatchedLeaderUserId
            )
        )
        val notMatchFitLeader = FitLeader(newFitGroup, notMatchedLeaderUserId, notMatchedLeaderUserId)
        fitLeaderRepository.save(notMatchFitLeader)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            fitGroupService.deleteFitGroup(
                newFitGroup.id!!,
                deleteFitGroupRequest
            )
        }
    }
}