package com.fitmate.fitgroupservice.common

class GlobalURI {

    companion object {
        const val ROOT_URI: String = "/fit-group-service"
        const val GROUP_ROOT: String = "$ROOT_URI/groups"

        const val PATH_VARIABLE_FIT_GROUP_ID: String = "fit-group-id"
        const val PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE: String = "/{$PATH_VARIABLE_FIT_GROUP_ID}"
    }
}