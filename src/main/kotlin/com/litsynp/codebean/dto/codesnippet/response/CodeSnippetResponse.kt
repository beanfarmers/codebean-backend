package com.litsynp.codebean.dto.codesnippet.response

import com.litsynp.codebean.domain.codesnippet.CodeSnippet
import com.litsynp.codebean.domain.codesnippet.SupportedLanguage
import java.io.File

data class CodeSnippetResponse(
    val id: Long,
    val description: String,
    val fileName: String,
    val code: String,
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
                code = codeSnippet.code
            )
        }
    }

}
