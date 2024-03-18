package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.group.DeleteFitGroupRequest
import com.fitmate.fitgroupservice.dto.group.RegisterFitGroupRequest
import com.fitmate.fitgroupservice.dto.group.UpdateFitGroupRequest
import com.fitmate.fitgroupservice.exception.BadRequestException
import com.fitmate.fitgroupservice.exception.ResourceNotFoundException
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
class FitGroupServiceBootTest {

    @Autowired
    private lateinit var fitGroupService: FitGroupService

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L

    private lateinit var fitGroup: FitGroup

    private lateinit var fitLeader: FitLeader

    @BeforeEach
    fun createTestFitGroup() {
        val fitGroup = FitGroup(fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, false, Instant.now(), "test")

        val savedFitGroup = fitGroupRepository.save(fitGroup)

        val fitLeader = FitLeader(savedFitGroup, requestUserId, false, Instant.now(), "test")

        this.fitLeader = fitLeaderRepository.save(fitLeader)

        this.fitGroup = savedFitGroup
    }

    @Test
    @DisplayName("[단위][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency)

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group - 성공 테스트")
    fun `update fit group service success test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.updateFitGroup(fitGroup.id!!, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group not found - 실패 테스트")
    fun `update fit group service fit group not found fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.updateFitGroup(-1, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group already deleted - 실패 테스트")
    fun `update fit group service fit group already deleted fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        fitGroup.delete()
        fitGroupRepository.save(fitGroup)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(fitGroup.id!!, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit leader does not exist - 실패 테스트")
    fun `update fit group service fit leader does not exist fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit leader does not match - 실패 테스트")
    fun `update fit group service fit leader does not match fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val newFitGroup = fitGroupRepository.save(FitGroup(fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, false, Instant.now(), notMatchedLeaderUserId))
        val notMatchFitLeader = FitLeader(newFitGroup, notMatchedLeaderUserId, false, fitLeader.createdAt, notMatchedLeaderUserId)
        fitLeaderRepository.save(notMatchFitLeader)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(newFitGroup.id!!, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data - 성공 테스트")
    fun `get fit group detail service success test`() {
        //given when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit group does not exist - 실패 테스트")
    fun `get fit group detail service fit group does not exist fail test`() {
        //given when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(-1) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit group already deleted - 실패 테스트")
    fun `get fit group detail service fit group already deleted fail test`() {
        //given
        fitGroup.delete()
        fitGroupRepository.save(fitGroup)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.getFitGroupDetail(fitGroup.id!!) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit leader does not exist - 실패 테스트")
    fun `get fit group detail service fit leader does not exist fail test`() {
        //given
        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group - 성공 테스트")
    fun `delete fit group service success test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when then
        Assertions.assertDoesNotThrow { fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group fit group does not exist - 실패 테스트")
    fun `delete fit group service fit group does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.deleteFitGroup(-1, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group already deleted - 실패 테스트")
    fun `delete fit group service fit group already deleted fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitGroup.delete()
        fitGroupRepository.save(fitGroup)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit leader does not exist - 실패 테스트")
    fun `delete fit group service fit leader does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitLeader.delete()
        fitLeaderRepository.save(fitLeader)
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit leader does not match - 실패 테스트")
    fun `delete fit group service fit leader does not match fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val newFitGroup = fitGroupRepository.save(FitGroup(fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, false, Instant.now(), notMatchedLeaderUserId))
        val notMatchFitLeader = FitLeader(newFitGroup, notMatchedLeaderUserId, false, fitLeader.createdAt, notMatchedLeaderUserId)
        fitLeaderRepository.save(notMatchFitLeader)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.deleteFitGroup(newFitGroup.id!!, deleteFitGroupRequest) }
    }
}