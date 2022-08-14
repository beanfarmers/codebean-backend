package com.litsynp.codebean.domain.codesnippet

import com.litsynp.codebean.domain.codesnippet.QCodeSnippet.codeSnippet
import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import com.litsynp.codebean.dto.codesnippet.response.QCodeSnippetResponse
import com.litsynp.codebean.global.util.getAllOrderSpecifiers
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository


@Repository
class CodeSnippetQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {

    companion object {
        private val ORDERING_FIELD_NAMES = listOf("id", "fileName", "createdOn")
    }

    fun find(pageable: Pageable): Page<CodeSnippetResponse> {
        val orderSpecifiers = getAllOrderSpecifiers(pageable, ORDERING_FIELD_NAMES).toTypedArray()
        val content = getContent(pageable, orderSpecifiers)
        return PageableExecutionUtils.getPage(content, pageable) { getCount() }
    }

    private fun getContent(
        pageable: Pageable,
        orderSpecifiers: Array<OrderSpecifier<*>>,
    ) = queryFactory
        .select(
            QCodeSnippetResponse(
                codeSnippet.id,
                codeSnippet.description,
                codeSnippet.fileName,
                codeSnippet.code,
                codeSnippet.createdOn,
                codeSnippet.updatedOn
            )
        )
        .from(codeSnippet)
        .orderBy(*orderSpecifiers)
        .offset(pageable.offset)
        .limit(pageable.pageSize.toLong())
        .fetch()

    private fun getCount(): Long {
        return queryFactory
            .select(codeSnippet.count())
            .from(codeSnippet)
            .fetchOne() ?: 0
    }

}
