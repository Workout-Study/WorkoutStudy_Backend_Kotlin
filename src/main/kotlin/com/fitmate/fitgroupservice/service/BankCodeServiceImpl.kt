package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.bank.BankCodeDetailDto
import com.fitmate.fitgroupservice.dto.bank.BankCodesResponse
import com.fitmate.fitgroupservice.persistence.repository.BankCodeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BankCodeServiceImpl(private val bankCodeRepository: BankCodeRepository) : BankCodeService {

    /**
     * Get Bank code list and code name service
     *
     * @return bank code and code name list
     */
    @Transactional(readOnly = true)
    override fun getBankCodes(): BankCodesResponse {
        val bankCodes = bankCodeRepository.findAll()
        return BankCodesResponse(bankCodes.map { BankCodeDetailDto(it.code, it.codeName) })
    }
}