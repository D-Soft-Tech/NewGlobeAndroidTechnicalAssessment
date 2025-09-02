package com.bridge.androidtechnicaltest.domain.models.enums

enum class RequiredModificationAction(val actionName: String) {
    NONE_REQUIRED("none_required"),
    SHOULD_BE_CREATED("should_be_created"),
    SHOULD_BE_UPDATED("should_be_updated"),
    SHOULD_BE_DELETED("should_be_deleted")
}