package com.litsynp.codebean.service.codesnippet

import com.litsynp.codebean.domain.codesnippet.CodeSnippet
import com.litsynp.codebean.domain.codesnippet.CodeSnippetRepository
import com.litsynp.codebean.dto.codesnippet.request.CodeSnippetCreateRequest
import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CodeSnippetService(
    private val codeSnippetRepository: CodeSnippetRepository,
) {

    @Transactional
    fun create(dto: CodeSnippetCreateRequest): CodeSnippetResponse {
        val newCodeSnippet = codeSnippetRepository.save(
            CodeSnippet(
                description = dto.description,
                fileName = dto.fileName,
                code = dto.code
            )
        )
        return CodeSnippetResponse.of(newCodeSnippet)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<CodeSnippetResponse> {
        return codeSnippetRepository.findAll().map(CodeSnippetResponse::of)
    }

    fun findById(id: Long): CodeSnippetResponse {
        val codeSnippet = codeSnippetRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Code snippet not found with id = ${id}") }
        return CodeSnippetResponse.of(codeSnippet)
    }

}
