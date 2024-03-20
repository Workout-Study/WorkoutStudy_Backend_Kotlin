package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.persistence.entity.BankCode
import com.fitmate.fitgroupservice.persistence.repository.BankCodeRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class BankCodeServiceTest {

    @InjectMocks
    private lateinit var bankCodeService: BankCodeServiceImpl

    @Mock
    private lateinit var bankCodeRepository: BankCodeRepository

    private val kakaoBankCode = BankCode("090", "카카오뱅크")
    private val kBankCode = BankCode("089", "케이뱅크")

    @Test
    @DisplayName("[단위][Service] Register fit group - 성공 테스트")
    fun `register fit group service success test`() {
        //given
        Mockito.`when`(bankCodeRepository.findAll()).thenReturn(listOf(kakaoBankCode, kBankCode))
        //when then
        Assertions.assertDoesNotThrow { bankCodeService.getBankCodes() }
    }
}