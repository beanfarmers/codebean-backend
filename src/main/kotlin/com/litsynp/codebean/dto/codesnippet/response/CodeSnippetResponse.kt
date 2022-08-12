package com.litsynp.codebean.dto.codesnippet.response

import com.litsynp.codebean.domain.codesnippet.CodeSnippet

data class CodeSnippetResponse(
    val id: Long,
    val description: String,
    val fileName: String,
    val code: String,
) {

    companion object {
        fun of(codeSnippet: CodeSnippet): CodeSnippetResponse {
            return CodeSnippetResponse(
                id = codeSnippet.id!!,
                description = codeSnippet.description,
                fileName = codeSnippet.fileName,
                code = codeSnippet.code
            )
        }
    }

}
