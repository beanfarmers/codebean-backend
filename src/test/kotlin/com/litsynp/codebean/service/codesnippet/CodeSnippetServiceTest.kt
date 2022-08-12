package com.litsynp.codebean.service.codesnippet

import com.litsynp.codebean.domain.codesnippet.CodeSnippet
import com.litsynp.codebean.domain.codesnippet.CodeSnippetRepository
import com.litsynp.codebean.dto.codesnippet.request.CodeSnippetCreateRequest
import com.litsynp.codebean.dto.codesnippet.response.CodeSnippetResponse
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CodeSnippetServiceTest @Autowired constructor(
    private val codeSnippetService: CodeSnippetService,
    private val codeSnippetRepository: CodeSnippetRepository,
) {

    @AfterEach
    fun clean() {
        codeSnippetRepository.deleteAll()
    }

    @Test
    @DisplayName("코드 스니펫 등록이 정상 동작한다")
    fun createTest() {
        // given
        val request = CodeSnippetCreateRequest(
            description = "C언어로 Hello World 출력하는 코드",
            fileName = "hello_world.c",
            code = """
                #include <stdio.h>
                
                int main(void) {
                    printf("Hello World!\n");
                    return 0;
                }
            """.trimIndent()
        )

        // when
        codeSnippetService.create(request)

        // then
        val results = codeSnippetRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].description).isEqualTo(request.description)
        assertThat(results[0].fileName).isEqualTo(request.fileName)
        assertThat(results[0].code).isEqualTo(request.code)
    }

    @Test
    @DisplayName("코드 스니펫 목록 조회가 정상 동작한다")
    fun findAllTest() {
        // given
        val codeSnippets = listOf(
            CodeSnippet(
                description = "C언어로 Hello World 출력하는 코드",
                fileName = "hello_world.c",
                code = """
                        #include <stdio.h>
                        
                        int main(void) {
                            printf("Hello World!");
                            return 0;
                        }
                    """.trimIndent()
            ),
            CodeSnippet(
                description = "Python으로 Hello World 출력하는 코드",
                fileName = "hello_world.py",
                code = "print(\"Hello World!\")"
            ),
            CodeSnippet(
                description = "Java로 Hello World 출력하는 코드",
                fileName = "HelloWorld.java",
                code = """
                        package com.litsynp.demo;
                        
                        public class HelloWorld {
                            public static void main(String[] args) {
                                System.out.println("Hello World!");
                            }
                        }
                    """.trimIndent()
            )
        )
        codeSnippetRepository.saveAll(codeSnippets)

        // when
        val results = codeSnippetService.findAll()

        // then
        assertThat(results).hasSize(3)
        assertThat(results).extracting("description")
            .containsExactlyInAnyOrder(*codeSnippets.map { it.description }.toTypedArray())
        assertThat(results).extracting("fileName")
            .containsExactlyInAnyOrder(*codeSnippets.map { it.fileName }.toTypedArray())
        assertThat(results).extracting("code")
            .containsExactlyInAnyOrder(*codeSnippets.map { it.code }.toTypedArray())
    }

    @Test
    @DisplayName("코드 스니펫 ID로 조회가 정상 동작한다")
    fun findByIdTest() {
        // given
        val codeSnippet = CodeSnippet(
            description = "C언어로 Hello World 출력하는 코드",
            fileName = "hello_world.c",
            code = """
                        #include <stdio.h>
                        
                        int main(void) {
                            printf("Hello World!");
                            return 0;
                        }
                    """.trimIndent()
        )
        val savedCodeSnippet = codeSnippetRepository.save(codeSnippet)

        // when
        val result = codeSnippetService.findById(savedCodeSnippet.id!!)

        // then
        assertThat(result.description).isEqualTo(codeSnippet.description)
        assertThat(result.fileName).isEqualTo(codeSnippet.fileName)
        assertThat(result.code).isEqualTo(codeSnippet.code)
    }

    @Test
    @DisplayName("없는 ID로 조회를 시도하면, 코드 스니펫을 조회가 실패한다")
    fun findByIdFailTest() {
        // given
        val codeSnippet = CodeSnippet(
            description = "C언어로 Hello World 출력하는 코드",
            fileName = "hello_world.c",
            code = """
                        #include <stdio.h>
                        
                        int main(void) {
                            printf("Hello World!");
                            return 0;
                        }
                    """.trimIndent()
        )
        val savedCodeSnippet = codeSnippetRepository.save(codeSnippet)

        // when & then
        assertThrows<IllegalArgumentException> {
            codeSnippetService.findById(20L)
        }.apply {
            assertThat(message).isEqualTo("Code snippet not found with id = 20")
        }
    }

}
