package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.common.GlobalStatus
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
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
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
    private lateinit var fitMateRepository: FitMateRepository

    @Mock
    private lateinit var multiMediaEndPointRepository: MultiMediaEndPointRepository

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
    private val maxFitMate = 20
    private val presentFitMateCount = 7
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")
    private lateinit var multiMediaEndPoints: List<MultiMediaEndPoint>
    private lateinit var fitGroup: FitGroup

    private lateinit var fitLeader: FitLeader

    @BeforeEach
    fun setFitGroupAndFitLeader() {
        fitGroup = FitGroup(fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, requestUserId)

        fitLeader = FitLeader(fitGroup, requestUserId, requestUserId)

        fitGroup.id = fitGroupId
        fitLeader.id = fitLeaderId
        multiMediaEndPoints = multiMediaEndPoint.map { MultiMediaEndPoint(fitGroup, it, requestUserId) }
    }

    @Test
    @DisplayName("[단위][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency, maxFitMate, multiMediaEndPoint)

        Mockito.`when`(fitGroupRepository.save(any(fitGroup.javaClass))).thenReturn(fitGroup)
        Mockito.`when`(fitLeaderRepository.save(any(fitLeader.javaClass))).thenReturn(fitLeader)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Register fit group null multi media - 성공 테스트")
    fun `register fit group service null multi media success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency, maxFitMate, null)

        Mockito.`when`(fitGroupRepository.save(any(fitGroup.javaClass))).thenReturn(fitGroup)
        Mockito.`when`(fitLeaderRepository.save(any(fitLeader.javaClass))).thenReturn(fitLeader)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.registerFitGroup(registerFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Register fit group empty multi media - 성공 테스트")
    fun `register fit group service empty multi media success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency, maxFitMate, listOf())

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
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(maxFitMate - 1)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group not found - 실패 테스트")
    fun `update fit group service fit group not found fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit group already deleted - 실패 테스트")
    fun `update fit group service fit group already deleted fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

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
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

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
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

        val notMatchedLeaderUserId = "notMatchedLeaderUserId"

        val notMatchFitLeader = FitLeader(fitGroup, notMatchedLeaderUserId, notMatchedLeaderUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(notMatchFitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Update fit present fit mate count bigger then new max fit mate - 실패 테스트")
    fun `update fit group service present fit mate count bigger then new max fit mate fail test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, multiMediaEndPoint)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(maxFitMate + 1)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data - 성공 테스트")
    fun `get fit group detail service success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(presentFitMateCount)
        Mockito.`when`(multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(multiMediaEndPoints)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data fit mate null count - 성공 테스트")
    fun `get fit group detail service fit mate null count success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(null)
        Mockito.`when`(multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(multiMediaEndPoints)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.getFitGroupDetail(fitGroupId) }
    }

    @Test
    @DisplayName("[단위][Service] Get fit group detail data multi media end point null - 성공 테스트")
    fun `get fit group detail service multi media end point null success test`() {
        //given
        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.countByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(presentFitMateCount)
        Mockito.`when`(multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(null)
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
        val fitMates = listOf(FitMate(fitGroup, requestUserId, requestUserId))

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(fitMates)
        //when then
        Assertions.assertDoesNotThrow { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }

    @Test
    @DisplayName("[단위][Service] Delete fit group fit mate null - 성공 테스트")
    fun `delete fit group service fit mate null success test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(fitLeader))
        Mockito.`when`(fitMateRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(null)
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

        val notMatchFitLeader = FitLeader(fitGroup, notMatchedLeaderUserId, notMatchedLeaderUserId)

        Mockito.`when`(fitGroupRepository.findById(fitGroupId)).thenReturn(Optional.of(fitGroup))
        Mockito.`when`(fitLeaderRepository.findByFitGroupAndState(fitGroup, GlobalStatus.PERSISTENCE_NOT_DELETED)).thenReturn(Optional.of(notMatchFitLeader))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) { fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest) }
    }
}