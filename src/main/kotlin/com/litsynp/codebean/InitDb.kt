package com.litsynp.codebean

import com.litsynp.codebean.domain.codesnippet.CodeSnippet
import com.litsynp.codebean.domain.codesnippet.CodeSnippetRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Profile("init-db")
@Component
class InitDb(
    private val initService: InitService,
) {

    @PostConstruct
    fun init() {
        initService.initCodeSnippets()
    }

    @Component
    @Transactional
    class InitService(
        private val codeSnippetRepository: CodeSnippetRepository,
    ) {

        fun initCodeSnippets() {
            val codeSnippets = listOf(
                CodeSnippet.fixture(fileName="test code 1"),
                CodeSnippet.fixture(fileName="test code 2"),
                CodeSnippet.fixture(fileName="test code 3"),
                CodeSnippet.fixture(fileName="test code 4"),
                CodeSnippet.fixture(fileName="test code 5"),
                CodeSnippet.fixture(fileName="test code 6"),
                CodeSnippet.fixture(fileName="test code 7"),
                CodeSnippet.fixture(fileName="test code 8"),
                CodeSnippet.fixture(fileName="test code 9"),
                CodeSnippet.fixture(fileName="test code 10"),
                CodeSnippet.fixture(fileName="test code 11"),
                CodeSnippet.fixture(fileName="test code 12"),
                CodeSnippet.fixture(fileName="test code 13"),
                CodeSnippet.fixture(fileName="test code 14"),
                CodeSnippet.fixture(fileName="test code 15"),
                CodeSnippet.fixture(fileName="test code 16"),
                CodeSnippet.fixture(fileName="test code 17"),
                CodeSnippet.fixture(fileName="test code 18"),
                CodeSnippet.fixture(fileName="test code 19"),
                CodeSnippet.fixture(fileName="test code 20"),
                CodeSnippet.fixture(
                    description = "Python으로 Hello World 출력하는 코드",
                    fileName = "hello_world.py",
                    code = "print(\"Hello World!\")"
                ),
                CodeSnippet.fixture(
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
        }

    }

}
