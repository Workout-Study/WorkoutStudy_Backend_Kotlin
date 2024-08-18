package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.mate.*
import com.fitmate.fitgroupservice.persistence.entity.*
import com.fitmate.fitgroupservice.persistence.repository.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
class FitMateControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitMateRepository: FitMateRepository

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

        val userForRead = UserForRead(fitLeader.fitLeaderUserId, "testFitLeader", "imageUrl", "testFitLeader")

        userForReadRepository.save(userForRead)

        this.fitGroup = savedFitGroup

        for (i in 1..3) {
            fitMateRepository.save(FitMate(fitGroup, requestUserId + i, (requestUserId + i).toString()))

            val fitMateUserForRead = UserForRead(requestUserId + i, "testFitMate" + i, "imageUrl", "testFitMate" + i)

            userForReadRepository.save(fitMateUserForRead)
        }
    }

    @Test
    @DisplayName("[통합][Controller] Fit mate 등록 - 성공 테스트")
    @Throws(Exception::class)
    @Transactional
    fun `register fit mate controller success test`() {
        //given
        val registerFitMateRequest = RegisterMateRequest(741, fitGroup.id!!)

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.MATE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit mate 목록 조회 - 성공 테스트")
    @Throws(Exception::class)
    @Transactional
    fun `get fit mate list controller success test`() {
        //given when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroup.id!!)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit mate 탈퇴 - 성공 테스트")
    @Throws(Exception::class)
    @Transactional
    fun `delete fit mate controller success test`() {
        //given
        val fitMateUserId = requestUserId % 2

        fitMateRepository.save(FitMate(fitGroup, fitMateUserId, fitMateUserId.toString()))

        val deleteMateRequest = DeleteMateRequest(fitMateUserId)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.MATE_ROOT + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroup.id!!)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteMateRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}