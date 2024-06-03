package com.fitmate.fitgroupservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.group.*
import com.fitmate.fitgroupservice.persistence.entity.BankCode
import com.fitmate.fitgroupservice.persistence.entity.FitGroup
import com.fitmate.fitgroupservice.persistence.entity.FitLeader
import com.fitmate.fitgroupservice.persistence.entity.UserForRead
import com.fitmate.fitgroupservice.service.FitGroupService
import com.fitmate.fitgroupservice.utils.SenderUtils
import org.junit.jupiter.api.BeforeEach
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

@WebMvcTest(FitGroupController::class)
@AutoConfigureRestDocs
class FitGroupControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fitGroupService: FitGroupService

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val requestUserId = 11422
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val penaltyAmount = 5000
    private val bankCode = BankCode("090", "카카오뱅크")
    private val penaltyAccount = "3333-03-5367420"
    private val category = 1
    private val introduction = "헬창들은 일주일에 7번은 운동해야한다고 생각합니다 당신도 헬창이 됩시다 근육 휴식따윈 생각도 마십쇼"
    private val cycle = null
    private val frequency = 7
    private val fitGroupId = 1L
    private val fitLeaderId = 3L
    private val maxFitMate = 20
    private val presentFitMateCount = 7
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private val fitGroup = FitGroup(
        fitGroupName,
        penaltyAmount,
        bankCode,
        penaltyAccount,
        category,
        introduction,
        cycle ?: 1,
        frequency,
        maxFitMate,
        requestUserId.toString()
    )

    private val fitLeader = FitLeader(fitGroup, requestUserId, requestUserId.toString())

    private val userForRead = UserForRead(requestUserId, "testUser", "testUser")

    @BeforeEach
    fun setFitGroupFitLeaderId() {
        fitGroup.id = fitGroupId
        fitLeader.id = fitLeaderId
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller success test`() {
        //given
        val registerFitGroupRequest = RegisterFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode.code,
            penaltyAccount,
            category,
            introduction,
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
            .andDo(
                document(
                    "register-fit-group",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit group을 등록하는 User id ( Fit Leader로 등록 )"),
                        fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("Fit group 이름"),
                        fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("운동 미인증 패널티 금액"),
                        fieldWithPath("penaltyAccountBankCode").type(JsonFieldType.STRING)
                            .description("운동 미인증 패널티 입금 은행"),
                        fieldWithPath("penaltyAccountNumber").type(JsonFieldType.STRING)
                            .description("운동 미인증 패널티 입금 계좌"),
                        fieldWithPath("category").type(JsonFieldType.NUMBER)
                            .description("운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )"),
                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("스터디 설명"),
                        fieldWithPath("cycle").type(JsonFieldType.NULL)
                            .description("운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                        fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("주기별 운동 인증 필요 횟수"),
                        fieldWithPath("maxFitMate").type(JsonFieldType.NUMBER).description("fit group의 최대 fit mate 수"),
                        fieldWithPath("multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("멀티 미디어 end point list ( 주어진 index 순으로 return )")
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
        val fitGroupDetailResponse = FitGroupDetailResponse(
            fitLeader,
            fitGroup,
            userForRead,
            presentFitMateCount,
            multiMediaEndPoint
        )

        Mockito.`when`(fitGroupService.getFitGroupDetail(fitGroupId)).thenReturn(fitGroupDetailResponse)
        //when
        val resultActions = mockMvc.perform(
            get("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "get-fit-group-detail",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("조회할 Fit group id")
                    ),
                    responseFields(
                        fieldWithPath("fitGroupId").type(JsonFieldType.NUMBER).description("Fit group id"),
                        fieldWithPath("fitLeaderUserId").type(JsonFieldType.NUMBER).description("Fit Leader User id"),
                        fieldWithPath("fitGroupLeaderUserNickname").type(JsonFieldType.STRING)
                            .description("Fit Leader User nickname"),
                        fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("Fit group 이름"),
                        fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("운동 미인증 패널티 금액"),
                        fieldWithPath("penaltyAccountBankCode").type(JsonFieldType.STRING)
                            .description("운동 미인증 패널티 입금 은행"),
                        fieldWithPath("penaltyAccountNumber").type(JsonFieldType.STRING)
                            .description("운동 미인증 패널티 입금 계좌"),
                        fieldWithPath("category").type(JsonFieldType.NUMBER)
                            .description("운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )"),
                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("스터디 설명"),
                        fieldWithPath("cycle").type(JsonFieldType.NUMBER)
                            .description("운동 인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 )"),
                        fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("주기별 운동 인증 필요 횟수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록 일시"),
                        fieldWithPath("presentFitMateCount").type(JsonFieldType.NUMBER).description("현재 fit mate 수"),
                        fieldWithPath("maxFitMate").type(JsonFieldType.NUMBER).description("fit group의 최대 fit mate 수"),
                        fieldWithPath("multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("멀티 미디어 end point list"),
                        fieldWithPath("state").type(JsonFieldType.BOOLEAN)
                            .description("fit group의 상태 (false: 정상, true: 삭제)"),
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Controller] Fit group 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit group controller success test`() {
        //given
        val updateFitGroupRequest = UpdateFitGroupRequest(
            requestUserId,
            fitGroupName,
            penaltyAmount,
            bankCode.code,
            penaltyAccount,
            category,
            introduction,
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
            .andDo(
                document(
                    "update-fit-group",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("수정할 Fit group id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("수정을 요청한 User id ( Fit Leader여야함 )"),
                        fieldWithPath("fitGroupName").type(JsonFieldType.STRING).description("수정할 Fit group 이름"),
                        fieldWithPath("penaltyAmount").type(JsonFieldType.NUMBER).description("수정할 운동 미인증 패널티 금액"),
                        fieldWithPath("penaltyAccountBankCode").type(JsonFieldType.STRING)
                            .description("수정할 운동 미인증 패널티 입금 은행"),
                        fieldWithPath("penaltyAccountNumber").type(JsonFieldType.STRING)
                            .description("수정할  운동 미인증 패널티 입금 계좌"),
                        fieldWithPath("category").type(JsonFieldType.NUMBER)
                            .description("수정할 운동 category ( 1: 등산, 2: 생활 체육, 3: 웨이트, 4: 수영, 5: 축구, 6: 농구, 7: 야구, 8: 바이킹, 9: 클라이밍, 10: 기타 )"),
                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("수정할 스터디 설명"),
                        fieldWithPath("cycle").type(JsonFieldType.NULL)
                            .description("수정할 운동 인증 주기 ( null시 기본값 일주일 - 1: 일주일, 2: 한달, 3: 일년 )"),
                        fieldWithPath("frequency").type(JsonFieldType.NUMBER).description("수정할 주기별 운동 인증 필요 횟수"),
                        fieldWithPath("maxFitMate").type(JsonFieldType.NUMBER).description("fit group의 최대 fit mate 수"),
                        fieldWithPath("multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("멀티 미디어 end point list ( 기존 기등록 멀티 미디어 list 미포함시 삭제 )")
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

        Mockito.`when`(fitGroupService.deleteFitGroup(fitGroupId, deleteFitGroupRequest))
            .thenReturn(deleteFitGroupResponse)
        //when
        val resultActions = mockMvc.perform(
            delete("${GlobalURI.GROUP_ROOT}${GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE}", fitGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitGroupRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "delete-fit-group",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID).description("삭제할 Fit group id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("삭제를 요청한 User id ( Fit Leader여야함 )")
                    ),
                    responseFields(
                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN).description("삭제 성공 여부")
                    )
                )
            )
    }
}