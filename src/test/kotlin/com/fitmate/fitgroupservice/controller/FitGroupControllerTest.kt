package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.service.FitGroupService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(FitGroupController::class)
@AutoConfigureRestDocs
class FitGroupControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitGroupService: FitGroupService

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L

    @Test
    @DisplayName("[단위][Controller] Fit group 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency)
        val registerFitGroupResponse = RegisterFitGroupResponse(true)

        Mockito.`when`(fitGroupService.registerFitGroup(registerFitGroupRequest)).thenReturn(registerFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
                post(GlobalURI.GROUP_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerFitGroupRequest))
                        .accept(MediaType.APPLICATION_JSON))
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("register-fit-group",
                        requestFields(
                                fieldWithPath("requestUserId").type(JsonFieldType.STRING).description("Fit group을 등록하는 User id ( Fit Leader로 등록 )"),
                                fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("Fit group 이름"),
                                fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("운동 미인증 패널티 금액"),
                                fieldWithPath("category").type(JsonFieldType.NUMBER).description("운동 category ( 1:헬스, 2:축구, 3:농구, 4:야구, 5: 클라이밍, 6: 배드민턴, 7: 필라테스, 10: 기타 )"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).description("스터디 설명"),
                                fieldWithPath("cycle").type(JsonFieldType.NULL).description("운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                                fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("주기별 운동 인증 필요 횟수")
                        ),
                        responseFields(
                                fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN).description("등록 성공 여부")
                        )
                )
                )
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 상세정보 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get detail data about fit group success test`() {
        //given
        val fitGroupDetailResponse = FitGroupDetailResponse(fitGroupId, requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle
                ?: 1, frequency, Instant.now())

        Mockito.`when`(fitGroupService.getFitGroupDetail(fitGroupId)).thenReturn(fitGroupDetailResponse)
        //when
        val resultActions = mockMvc.perform(
                get("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-fit-group-detail",
                        pathParameters(
                                parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("조회할 Fit group id")
                        ),
                        responseFields(
                                fieldWithPath("fitGroupId").type(JsonFieldType.NUMBER).description("Fit group id"),
                                fieldWithPath("fitLeaderUserId").type(JsonFieldType.STRING).description("Fit Leader User id"),
                                fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("Fit group 이름"),
                                fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("운동 미인증 패널티 금액"),
                                fieldWithPath("category").type(JsonFieldType.NUMBER).description("운동 category ( 1:헬스, 2:축구, 3:농구, 4:야구, 5: 클라이밍, 6: 배드민턴, 7: 필라테스, 10: 기타 )"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).description("스터디 설명"),
                                fieldWithPath("cycle").type(JsonFieldType.NUMBER).description("운동 인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 )"),
                                fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("주기별 운동 인증 필요 횟수"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일시")
                        )
                )
                )
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit group controller success test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(requestUserId, fitGroupName, penaltyAmount, category, introduction, cycle, frequency)
        val updateFitGroupResponse = UpdateFitGroupResponse(true)

        Mockito.`when`(fitGroupService.updateFitGroup(fitGroupId, updateFitGroupRequest)).thenReturn(updateFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
                put("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateFitGroupRequest))
                        .accept(MediaType.APPLICATION_JSON))
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-fit-group",
                        pathParameters(
                                parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("수정할 Fit group id")
                        ),
                        requestFields(
                                fieldWithPath("requestUserId").type(JsonFieldType.STRING).description("수정을 요청한 User id ( Fit Leader여야함 )"),
                                fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("수정할 Fit group 이름"),
                                fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("수정할 운동 미인증 패널티 금액"),
                                fieldWithPath("category").type(JsonFieldType.NUMBER).description("수정할 운동 category ( 1:헬스, 2:축구, 3:농구, 4:야구, 5: 클라이밍, 6: 배드민턴, 7: 필라테스, 10: 기타 )"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).description("수정할 스터디 설명"),
                                fieldWithPath("cycle").type(JsonFieldType.NULL).description("수정할 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                                fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("수정할 주기별 운동 인증 필요 횟수")
                        ),
                        responseFields(
                                fieldWithPath("isUpdateSuccess").type(JsonFieldType.BOOLEAN).description("수정 성공 여부")
                        )
                )
                )
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit group controller success test`() {
        //given
        val deleteFitGroupRequest = DeleteFitGroupRequest(requestUserId)
        val deleteFitGroupResponse = DeleteFitGroupResponse(true)

        Mockito.`when`(fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest)).thenReturn(deleteFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
                delete("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteFitGroupRequest))
                        .accept(MediaType.APPLICATION_JSON))
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-fit-group",
                        pathParameters(
                                parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("삭제할 Fit group id")
                        ),
                        requestFields(
                                fieldWithPath("requestUserId").type(JsonFieldType.STRING).description("삭제를 요청한 User id ( Fit Leader여야함 )")
                        ),
                        responseFields(
                                fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN).description("삭제 성공 여부")
                        )
                )
                )
    }
}