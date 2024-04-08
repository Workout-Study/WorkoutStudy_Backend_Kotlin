package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.service.FitGroupService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(FitGroupController::class)
class FitGroupControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitGroupService: FitGroupService

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

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 등록 request user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `register fit group controller request user id validation fail test`(testRequestUserId: String) {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            testRequestUserId,
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

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.GROUP_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 등록 fit group name - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `register fit group controller fit group name validation fail test`(testFitGroupName: String) {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            testFitGroupName,
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

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.GROUP_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 등록 penalty bank code - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `register fit group controller penalty bank code validation fail test`(testPenaltyAccountBankCode: String) {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            testPenaltyAccountBankCode,
            penaltyAccount,
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
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 등록 penalty account number - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `register fit group controller penalty account number validation fail test`(testPenaltyAccountNumber: String) {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            testPenaltyAccountNumber,
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
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Controller] Fit group 등록 introduction - Validation 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller introduction validation success test`(testIntroduction: String?) {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            testIntroduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        val registerFitGroupResponse = RegisterFitGroupResponse(true)

        Mockito.`when`(fitGroupService.registerFitGroup(registerFitGroupRequest)).thenReturn(registerFitGroupResponse)
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

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Controller] Fit group 등록 multiMediaEndPoint - Validation 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller multiMediaEndPoint validation success test`(testMultiMediaEndPoint: List<String>?) {
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
            testMultiMediaEndPoint
        )

        val registerFitGroupResponse = RegisterFitGroupResponse(true)

        Mockito.`when`(fitGroupService.registerFitGroup(registerFitGroupRequest)).thenReturn(registerFitGroupResponse)
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
    @DisplayName("[단위][Controller] Fit group 상세정보 조회 fit group id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `get detail data about fit group controller fit group id validation fail test`() {
        //given
        val testFitGroupId = "failFitGroupId"

        //when
        val resultActions = mockMvc.perform(
            get("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", testFitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 request user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `update fit group controller request user id validation fail test`(testRequestUserId: String) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            testRequestUserId,
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

        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 fit group name - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `update fit group controller fit group name validation fail test`(testFitGroupName: String) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            testFitGroupName,
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

        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 penalty bank code - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `update fit group controller penalty bank code validation fail test`(testPenaltyBankCode: String) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            testPenaltyBankCode,
            penaltyAccount,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 penalty account number - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `update fit group controller penalty account number validation fail test`(testPenaltyAccountNumber: String) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            testPenaltyAccountNumber,
            category,
            introduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 introduction - Validation 성공 테스트")
    @Throws(Exception::class)
    fun `update fit group controller introduction validation success test`(testIntroduction: String) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode,
            penaltyAccount,
            category,
            testIntroduction,
            cycle,
            frequency,
            maxFitMate,
            multiMediaEndPoint
        )

        val updateFitGroupResponse = UpdateFitGroupResponse(true)

        Mockito.`when`(fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest))
            .thenReturn(updateFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 수정 multiMediaEndPoint - Validation 성공 테스트")
    @Throws(Exception::class)
    fun `update fit group controller multiMediaEndPoint validation success test`(testMultiMediaEndPoint: List<String>?) {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
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
            testMultiMediaEndPoint
        )

        val updateFitGroupResponse = UpdateFitGroupResponse(true)

        Mockito.`when`(fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest))
            .thenReturn(updateFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
            put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 삭제 fit group id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit group controller fit group id validation fail test`() {
        //given
        val testFitGroupId = "failFitGroupId"

        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", testFitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Controller] Fit group 삭제 request user id - Validation 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit group controller request user id validation fail test`(testRequestUserId: String) {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(testRequestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}