package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.filter.FitGroupFilterRequest
import com.fitmate.fitgroupservice.persistence.entity.BankCode
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.FitMate
import com.fitmate.fitgroupservice.persistence.repository.BankCodeRepository
import com.fitmate.fitgroupservice.persistence.repository.FitGroupRepository
import com.fitmate.fitgroupservice.persistence.repository.FitLeaderRepository
import com.fitmate.fitgroupservice.persistence.repository.FitMateRepository
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FitGroupFilterControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var fitGroupRepository: FitGroupRepository

    @Autowired
    private lateinit var fitMateRepository: FitMateRepository

    @Autowired
    private lateinit var fitLeaderRepository: FitLeaderRepository

    @Autowired
    private lateinit var bankCodeRepository: BankCodeRepository

    private val withMaxGroup = false
    private val category = 1
    private val pageNumber = 1
    private val pageSize = 5

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val penaltyAccountBankCode = "090"
    private val penaltyAccount = "3333-03-5367420"
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val maxFitMate = 20

    private lateinit var bankCode: BankCode
    private lateinit var maxFitMateGroup: FitGroup
    private lateinit var withOutLeaderFitGroup: FitGroup

    @BeforeEach
    fun makeDefaultFitGroupData() {
        bankCode = bankCodeRepository.findByCode(penaltyAccountBankCode).get()

        for (i in 1..pageSize * (pageNumber)) {
            val fitGroup = FitGroup(
                fitGroupName + i,
                penaltyAmount + i,
                bankCode,
                penaltyAccount + i,
                category,
                introduction + i,
                cycle ?: 1,
                frequency,
                maxFitMate + i,
                requestUserId + i
            )

            val savedFitGroup = fitGroupRepository.save(fitGroup)

            fitLeaderRepository.save(FitLeader(savedFitGroup, requestUserId + i, requestUserId + i))

            for (j in i..maxFitMate) {
                val fitMate = FitMate(savedFitGroup, j.toString(), j.toString())
                fitMateRepository.save(fitMate)
            }
        }

        val fitGroup = FitGroup(
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            introduction,
            cycle ?: 1,
            frequency,
            maxFitMate,
            requestUserId
        )

        maxFitMateGroup = fitGroupRepository.save(fitGroup)

        fitLeaderRepository.save(FitLeader(maxFitMateGroup, requestUserId, requestUserId))

        for (i in 1..<maxFitMate) {
            val fitMate = FitMate(maxFitMateGroup, i.toString(), i.toString())
            fitMateRepository.save(fitMate)
        }

        val otherFitGroup = FitGroup(
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            introduction,
            cycle ?: 1,
            frequency,
            maxFitMate,
            requestUserId
        )

        withOutLeaderFitGroup = fitGroupRepository.save(otherFitGroup)

        for (i in 1..maxFitMate) {
            val fitMate = FitMate(withOutLeaderFitGroup, i.toString(), i.toString())
            fitMateRepository.save(fitMate)
        }
    }

    @Test
    @DisplayName("[통합][Controller] Fit group filter 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `fit group filter controller empty condition success test`() {
        //given when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FILTER_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Controller] Fit group filter full condition 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `fit group filter controller full condition success test`() {
        //given
        val fitGroupFilterRequest = FitGroupFilterRequest(withMaxGroup, category, pageNumber, pageSize)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("withMaxGroup", fitGroupFilterRequest.withMaxGroup)
            .queryParam("category", fitGroupFilterRequest.category)
            .queryParam("pageNumber", fitGroupFilterRequest.pageNumber)
            .queryParam("pageSize", fitGroupFilterRequest.pageSize)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FILTER_ROOT + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}