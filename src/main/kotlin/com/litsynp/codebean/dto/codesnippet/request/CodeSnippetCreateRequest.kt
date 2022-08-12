package com.litsynp.codebean.dto.codesnippet.request

data class CodeSnippetCreateRequest(
    val description: String,
    val fileName: String,
    val code: String,
)
