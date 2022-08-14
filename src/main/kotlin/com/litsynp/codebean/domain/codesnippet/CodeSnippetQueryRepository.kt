package com.litsynp.codebean.domain.codesnippet

import com.litsynp.codebean.domain.codesnippet.QCodeSnippet.codeSnippet
import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import com.litsynp.codebean.dto.codesnippet.response.QCodeSnippetResponse
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository


@Repository
class CodeSnippetQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {

    /**
     * Query with no offset method
     */
    fun find(pageable: Pageable, codeSnippetId: Long?): Page<CodeSnippetResponse> {
        val content = getContent(codeSnippetId, pageable)
        return PageableExecutionUtils.getPage(content, pageable) { getCount() }
    }

    private fun getContent(
        codeSnippetId: Long?,
        pageable: Pageable,
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
        .where(ltCodeSnippetId(codeSnippetId))
        .orderBy(codeSnippet.id.desc())
        .offset(pageable.offset)
        .limit(pageable.pageSize.toLong())
        .fetch()

    private fun ltCodeSnippetId(codeSnippetId: Long?): BooleanExpression? {
        return if (codeSnippetId != null) codeSnippet.id.lt(codeSnippetId) else null
    }

    private fun getCount(): Long {
        return queryFactory
            .select(codeSnippet.count())
            .from(codeSnippet)
            .fetchOne() ?: 0
    }

}
