package com.fitmate.fitgroupservice.common

class GlobalURI {

    companion object {
        const val ROOT_URI = "/fit-group-service"

        const val BANK_CODE_ROOT = "$ROOT_URI/bank-codes"

        const val GROUP_ROOT = "$ROOT_URI/groups"
        const val FILTER_ROOT = "$GROUP_ROOT/filters"

        const val PATH_VARIABLE_FIT_GROUP_ID = "fit-group-id"
        const val PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_GROUP_ID}"

        const val PATH_VARIABLE_USER_ID = "user-id"
        const val PATH_VARIABLE_USER_ID_WITH_BRACE = "/{$PATH_VARIABLE_USER_ID}"

        const val MATE_ROOT = "$ROOT_URI/mates"

        const val FIT_MANAGEMENT_ROOT = "$ROOT_URI/managements"
        const val FIT_MATE_MANAGEMENT_ROOT = "$FIT_MANAGEMENT_ROOT/mates"
    }
}