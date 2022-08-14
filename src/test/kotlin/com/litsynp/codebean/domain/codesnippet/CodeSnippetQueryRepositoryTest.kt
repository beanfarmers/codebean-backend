package com.litsynp.codebean.domain.codesnippet

import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@SpringBootTest
@TestInstance(PER_CLASS)
class CodeSnippetQueryRepositoryTest @Autowired constructor(
    val codeSnippetRepository: CodeSnippetRepository,
    val codeSnippetQueryRepository: CodeSnippetQueryRepository,
) {

    private lateinit var codeSnippets: List<CodeSnippet>

    @BeforeAll
    fun init() {
        codeSnippets = listOf(
            CodeSnippet.fixture(fileName = "hello1.c"),
            CodeSnippet.fixture(fileName = "hello2.c"),
            CodeSnippet.fixture(fileName = "hello3.c"),
            CodeSnippet.fixture(fileName = "hello4.c"),
            CodeSnippet.fixture(fileName = "hello5.c"),
            CodeSnippet.fixture(fileName = "hello6.c"),
            CodeSnippet.fixture(fileName = "hello7.c"),
            CodeSnippet.fixture(fileName = "hello8.c"),
            CodeSnippet.fixture(fileName = "hello9.c"),
            CodeSnippet.fixture(fileName = "hello10.c"),
        )

        codeSnippetRepository.saveAll(codeSnippets)
    }

    @Test
    @DisplayName("ID 순서로 조회할 수 있다")
    fun findSortedByIdTest() {
        // given
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").descending())

        // when
        val results = codeSnippetQueryRepository.find(pageRequest)

        // then
        assertThat(results).isInstanceOf(Page::class.java)
        assertThat(results.content).extracting("id")
            .containsExactlyElementsOf(
                codeSnippets.sortedByDescending { it.id }
                    .map { CodeSnippetResponse.of(it).id })
    }

    @Test
    @DisplayName("생성 시간 순서로 조회할 수 있다")
    fun findSortedByCreatedOnTest() {
        // given
        val pageRequest = PageRequest.of(0, 10, Sort.by("createdOn").descending())

        // when
        val results = codeSnippetQueryRepository.find(pageRequest)

        // then
        assertThat(results).isInstanceOf(Page::class.java)
        assertThat(results.content).extracting("createdOn")
            .containsExactlyElementsOf(
                codeSnippets.sortedByDescending { it.createdOn }
                    .map { CodeSnippetResponse.of(it).createdOn })
    }

    @Test
    @DisplayName("페이지로 나눠서 조회할 수 있다")
    fun findPaginationTest() {
        // given
        val pageRequest = PageRequest.of(1, 5, Sort.by("createdOn").descending())

        // when
        val results = codeSnippetQueryRepository.find(pageRequest)

        // then
        assertThat(results).isInstanceOf(Page::class.java)
        assertThat(results.content)
            .containsExactlyElementsOf(
                codeSnippets.sortedByDescending { it.createdOn }
                    .map { CodeSnippetResponse.of(it) }.subList(5, 10)
            )
    }

}
