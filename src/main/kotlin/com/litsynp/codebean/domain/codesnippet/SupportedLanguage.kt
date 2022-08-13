package com.litsynp.codebean.domain.codesnippet

enum class SupportedLanguage(
    val codeName: String,
    val fileExtensions: List<String>,
) {

    ACTION_SCRIPT("actionscript", listOf("as")),
    ADA("ada", listOf("ada")),
    ARDUINO("arduino", listOf("ino")),
    C("c", listOf("c")),
    CLOJURE("clojure", listOf("clj")),
    C_PP("cpp", listOf("cpp")),
    C_SHARP("csharp", listOf("csharp")),
    CSS("css", listOf("css")),
    DART("dart", listOf("dart")),
    GO("go", listOf("go")),
    GRAPH_QL("graphql", listOf("sdl")),
    GROOVY(("groovy"), listOf("groovy", "gvy", "gy", "gsh")),
    HTML("html", listOf("html")),
    JAVA("java", listOf("java")),
    JAVASCRIPT("javascript", listOf("js")),
    JSON("json", listOf("json")),
    JAVASCRIPT_XML("jsx", listOf("jsx")),
    KOTLIN("kotlin", listOf("kt", "kts", "ktm")),
    LATEX("latex", listOf("tex")),
    LISP("lisp", listOf("lisp")),
    LUA("lua", listOf("lua")),
    MAKEFILE("makefile", listOf("makefile")),
    MATLAB("matlab", listOf("m", "mat")),
    OBJECTIVE_C("objectivec", listOf("h", "m")),
    PHP("php", listOf("php")),
    POWERSHELL("powershell", listOf("ps")),
    PYTHON("python", listOf("py")),
    R("r", listOf("r")),
    RUBY("ruby", listOf("rb")),
    RUST("rust", listOf("rs")),
    SASS("sass", listOf("sass", "scss")),
    SCALA("scala", listOf("scala", "sc")),
    SHELL("shell", listOf("sh", "csh", "bash", "zsh")),
    SQL("sql", listOf("sql")),
    SWIFT("swift", listOf("swift")),
    TEXT("text", listOf("text", "txt")),
    TYPESCRIPT_XML("tsx", listOf("tsx")),
    TYPESCRIPT("typescript", listOf("ts")),
    YAML("yaml", listOf("yml", "yaml")),
    NOT_SUPPORTED("text", listOf()),
    ;

    private class SupportedLanguageWithValue(val supportedLanguage: SupportedLanguage, val fileExtension: String)

    companion object {
        private val codeNameMap = SupportedLanguage.values().associateBy(SupportedLanguage::codeName)
        private val fileExtensionMap: Map<String, SupportedLanguage> = values()
            .flatMap { sl ->
                sl.fileExtensions.map { symbol ->
                    SupportedLanguageWithValue(sl, symbol)
                }
            }.associate { slwv ->
                slwv.fileExtension to slwv.supportedLanguage
            }

        fun fromCodeName(codeName: String) = codeNameMap[codeName] ?: NOT_SUPPORTED
        fun fromFileExtension(fileExtension: String) = fileExtensionMap[fileExtension] ?: NOT_SUPPORTED
    }

}
