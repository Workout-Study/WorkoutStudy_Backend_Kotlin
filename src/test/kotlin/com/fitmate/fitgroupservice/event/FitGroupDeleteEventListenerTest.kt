package com.fitmate.fitgroupservice.event

import com.fitmate.fitgroupservice.common.GlobalStatus
import com.fitmate.fitgroupservice.dto.group.DeleteFitGroupRequest
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.repository.FitGroupHistoryRepository
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.MultiMediaEndPointRepository
import com.fitmate.fitgroupservice.service.FitGroupService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class FitGroupDeleteEventListenerTest {

    @Autowired
    private lateinit var fitGroupService: FitGroupService

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var fitGroupHistoryRepository: FitGroupHistoryRepository

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
    private val maxFitMate = 20

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
    @Transactional
    @DisplayName("[통합][Event] Delete fit group register fit group history - 성공 테스트")
    fun `delete fit group register fit group history success test`() {
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest)

        Assertions.assertTrue(fitGroupHistoryRepository.findByFitGroupId(fitGroup.id!!).isNullOrEmpty().not())
    }

    @Test
    @Transactional
    @DisplayName("[통합][Event] Delete fit group delete multi media - 성공 테스트")
    fun `delete fit group delete multi media success test`() {
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        fitGroupService.deleteFitGroup(fitGroup.id!!, deleteFitGroupRequest)

        Assertions.assertTrue(
            multiMediaEndPointRepository.findByFitGroupAndStateOrderByIdAsc(
                fitGroup,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            ).isNullOrEmpty()
        )
    }
}