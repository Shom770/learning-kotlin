class Lexer(text: String) {
    val text = text
    var position = -1
    var current_char: Char? = null

    private fun advance(occurrences: Int = 1) {
        try {
            for (i in 0 until occurrences) {
                this.position += 1
                this.current_char = text[position]
            }
        } catch (e: IndexOutOfBoundsException) {
            this.current_char = null
        }
    }

    fun lex() {
        this.advance()
    }
}

