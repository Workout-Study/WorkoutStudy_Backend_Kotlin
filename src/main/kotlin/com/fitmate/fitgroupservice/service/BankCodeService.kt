package com.fitmate.fitgroupservice.service

import com.fitmate.fitgroupservice.dto.bank.BankCodesResponse

interface BankCodeService {

    /**
     * Get Bank code list and code name service
     *
     * @return bank code and code name list
     */
    fun getBankCodes(): BankCodesResponse
}