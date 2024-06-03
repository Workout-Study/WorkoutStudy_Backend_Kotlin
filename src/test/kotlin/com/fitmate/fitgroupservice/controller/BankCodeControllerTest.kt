package com.fitmate.fitgroupservice.controller

import com.fitmate.fitgroupservice.common.GlobalURI
import com.fitmate.fitgroupservice.dto.bank.BankCodeDetailDto
import com.fitmate.fitgroupservice.dto.bank.BankCodesResponse
import com.fitmate.fitgroupservice.service.BankCodeService
import com.fitmate.fitgroupservice.utils.SenderUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BankCodeController::class)
@AutoConfigureRestDocs
class BankCodeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bankCodeService: BankCodeService

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val kakaoBankCodeDetailDto = BankCodeDetailDto("090", "카카오뱅크")
    private val kBankCodeDetailDto = BankCodeDetailDto("089", "케이뱅크")

    private val bankCodesResponse = BankCodesResponse(listOf(kakaoBankCodeDetailDto, kBankCodeDetailDto))

    @Test
    @DisplayName("[단위][Controller] 뱅크 코드 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit group controller success test`() {
        //given
        Mockito.`when`(bankCodeService.getBankCodes()).thenReturn(bankCodesResponse)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.BANK_CODE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "get-bank-code-list",
                    responseFields(
                        fieldWithPath("bankCodeDetails[]").type(JsonFieldType.ARRAY)
                            .description("은행 코드 목록"),
                        fieldWithPath("bankCodeDetails[].code").type(JsonFieldType.STRING)
                            .description("은행 코드"),
                        fieldWithPath("bankCodeDetails[].codeName").type(JsonFieldType.STRING)
                            .description("은행명")
                    )
                )
            )
    }
}