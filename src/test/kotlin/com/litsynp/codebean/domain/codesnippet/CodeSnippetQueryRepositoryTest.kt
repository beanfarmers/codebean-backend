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
            CodeSnippet.fixture(fileName = "hello1.c", code = ""),
            CodeSnippet.fixture(fileName = "hello2.c", code = ""),
            CodeSnippet.fixture(fileName = "hello3.c", code = ""),
            CodeSnippet.fixture(fileName = "hello4.c", code = ""),
            CodeSnippet.fixture(fileName = "hello5.c", code = ""),
            CodeSnippet.fixture(fileName = "hello6.c", code = ""),
            CodeSnippet.fixture(fileName = "hello7.c", code = ""),
            CodeSnippet.fixture(fileName = "hello8.c", code = ""),
            CodeSnippet.fixture(fileName = "hello9.c", code = ""),
            CodeSnippet.fixture(fileName = "hello10.c", code = ""),
        )

        codeSnippetRepository.saveAll(codeSnippets)
    }

    @Test
    @DisplayName("페이지로 나눠서 조회할 수 있다")
    fun findPaginationTest() {
        // given
        val pageRequest = PageRequest.of(0, 5)

        // when
        // ID 순으로 10번부터 4번까지 읽었을 때, 최신 데이터 5개 조회 시도
        val results = codeSnippetQueryRepository.find(pageRequest, 4)

        // then
        assertThat(results).isInstanceOf(Page::class.java)
        assertThat(results.content).hasSize(3)  // 3, 2, 1번만 존재
        assertThat(results.content)
            .containsExactlyElementsOf(
                codeSnippets.sortedByDescending { it.id }
                    .map { CodeSnippetResponse.of(it) }.subList(7, 10)  // 3, 2, 1 (ID 역순)
            )
    }

}
