package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
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
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class FitGroupServiceTest {

    @InjectMocks
    private lateinit var fitGroupService: FitGroupServiceImpl

    @Mock
    private lateinit var fitGroupRepository: FitGroupRepository

    @Mock
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L
    private val fitLeaderId = 3L

    private lateinit var fitGroup: FitGroup

    private lateinit var fitLeader: FitLeader

    @BeforeEach
    fun setFitGroupAndFitLeader() {
        fitGroup = FitGroup(fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, false, Instant.now(), requestUserId)

        fitLeader = FitLeader(fitGroup, requestUserId, false, Instant.now(), requestUserId)

        fitGroup.id = fitGroupId
        fitLeader.id = fitLeaderId
    }

    @Test
    @DisplayName("[단위][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency)

        Mockito.`when`(fitGroupRepository.save(any(fitGroup.javaClass))).thenReturn(fitGroup)
        Mockito.`when`(fitLeaderRepository.save(any(fitLeader.javaClass))).thenReturn(fitLeader)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group - 성공 테스트")
    fun `update fit group service success test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group not found - 실패 테스트")
    fun `update fit group service fit group not found fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group already deleted - 실패 테스트")
    fun `update fit group service fit group already deleted fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit leader does not exist - 실패 테스트")
    fun `update fit group service fit leader does not exist fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.empty())
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

        val notMatchFitLeader = FitLeader(fitGroup, notMatchedLeaderUserId, false, fitLeader.createdAt, notMatchedLeaderUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(notMatchFitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data - 성공 테스트")
    fun `get fit group detail service success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit group does not exist - 실패 테스트")
    fun `get fit group detail service fit group does not exist fail test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit group already deleted - 실패 테스트")
    fun `get fit group detail service fit group already deleted fail test`() {
        //given
        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit leader does not exist - 실패 테스트")
    fun `get fit group detail service fit leader does not exist fail test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group - 성공 테스트")
    fun `delete fit group service success test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group fit group does not exist - 실패 테스트")
    fun `delete fit group service fit group does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group already deleted - 실패 테스트")
    fun `delete fit group service fit group already deleted fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitGroup.delete()

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit leader does not exist - 실패 테스트")
    fun `delete fit group service fit leader does not exist fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit leader does not match - 실패 테스트")
    fun `delete fit group service fit leader does not match fail test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val notMatchFitLeader = FitLeader(fitGroup, notMatchedLeaderUserId, false, fitLeader.createdAt, notMatchedLeaderUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(notMatchFitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }
}