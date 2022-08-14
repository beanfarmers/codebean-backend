package com.litsynp.codebean.controller.codesnippet

import com.litsynp.codebean.dto.codesnippet.request.CodeSnippetCreateRequest
import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import com.litsynp.codebean.service.codesnippet.CodeSnippetService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/code-snippets")
class CodeSnippetController(
    private val codeSnippetService: CodeSnippetService,
) {

    @PostMapping
    fun create(@RequestBody dto: CodeSnippetCreateRequest): ResponseEntity<CodeSnippetResponse> {
        val result = codeSnippetService.create(dto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(result)
    }

    @GetMapping
    fun list(
        @PageableDefault(size = 10, sort = ["createdOn"], direction = Sort.Direction.DESC)
        pageable: Pageable,
    ): ResponseEntity<Page<CodeSnippetResponse>> {
        val results = codeSnippetService.find(pageable)
        return ResponseEntity.ok(results)
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long): ResponseEntity<CodeSnippetResponse> {
        val result = codeSnippetService.findById(id)
        return ResponseEntity.ok(result)
    }

}
