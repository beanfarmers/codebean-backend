package com.litsynp.codebean.dto.codesnippet.response

import com.litsynp.codebean.domain.codesnippet.CodeSnippet
import com.litsynp.codebean.domain.codesnippet.SupportedLanguage
import com.querydsl.core.annotations.QueryProjection
import java.io.File
import java.time.LocalDateTime

data class CodeSnippetResponse @QueryProjection constructor(
    val id: Long,
    val description: String,
    val fileName: String,
    val code: String,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
) {

    val languageCodeName: String

    init {
        this.languageCodeName = SupportedLanguage.fromFileExtension(fileExtension).codeName
    }

    private val fileExtension
        get() = File(fileName).extension

    companion object {
        fun of(codeSnippet: CodeSnippet): CodeSnippetResponse {
            return CodeSnippetResponse(
                id = codeSnippet.id!!,
                description = codeSnippet.description,
                fileName = codeSnippet.fileName,
                code = codeSnippet.code,
                createdOn = codeSnippet.createdOn,
                updatedOn = codeSnippet.updatedOn
            )
        }
    }

}
