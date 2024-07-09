package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.UserForReadRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FitGroupControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var userForReadRepository: UserForReadRepository

    private val requestUserId = 11422
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val maxFitMate = 20
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private lateinit var fitGroup: FitGroup

    @BeforeEach
    fun createTestFitGroup() {
        val fitGroup = FitGroup(
            fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, maxFitMate, requestUserId.toString()
        )

        val savedFitGroup = fitGroupRepository.save(fitGroup)

        val fitLeader = FitLeader(savedFitGroup, requestUserId, requestUserId.toString())

        fitLeaderRepository.save(fitLeader)

        val userForRead = UserForRead(fitLeader.fitLeaderUserId, "testFitLeader", "testFitLeader")

        userForReadRepository.save(userForRead)

        this.fitGroup = savedFitGroup
    }

    @Test
    @DisplayName("[통합][Controller] Fit group 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller success boot test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.GROUP_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit group 상세정보 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get detail data about fit group success boot test`() {
        //given
        //when
        val resultActions = mockMvc.perform(
            get("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroup.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit group 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit group controller success boot test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroup.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit group 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit group controller success boot test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroup.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}