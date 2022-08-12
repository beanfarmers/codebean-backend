package com.litsynp.codebean.domain.codesnippet

import com.litsynp.codebean.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class CodeSnippet(
    var description: String,

    var fileName: String,

    @Lob
    var code: String,
) : BaseEntity() {

    fun updateDescription(description: String) {
        this.description = description
    }

    fun updateFileName(fileName: String) {
        this.fileName = fileName
    }

    fun updateCode(code: String) {
        this.code = code
    }

    companion object {
        fun fixture(
            description: String = "C언어로 Hello World 출력하는 코드",
            fileName: String = "hello_world.c",
            code: String = """
                #include <stdio.h>
                
                int main(void) {
                    printf("Hello World!");
                    return 0;
                }
                """.trimIndent(),
        ): CodeSnippet {
            return CodeSnippet(description, fileName, code)
        }
    }

}
