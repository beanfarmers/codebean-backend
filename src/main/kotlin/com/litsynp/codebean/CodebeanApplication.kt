package com.litsynp.codebean

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodebeanApplication

fun main(args: Array<String>) {
	runApplication<CodebeanApplication>(*args)
}
