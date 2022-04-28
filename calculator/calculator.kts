// Tokens

enum class TokenType {
    INT,
    FLOAT,
    PLUS,
    MINUS,
    DIV,
    MUL,
    POW,
    LPAREN,
    RPAREN
}

data class Token(val tokType: TokenType, val tokValue: Char)

// Lexer

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
        var tokens: MutableList<tokens.Token> = mutableListOf()

        while (this.current_char != null) {
            // Skip whitespace
            if (this.current_char == ' ') {
                continue
            }
            // Basic one-letter tokens
            tokens.push(when (this.current_char) {
                "+" -> tokens.Token(tokens.TokenType.PLUS, "+")
                "-" -> tokens.Token(tokens.TokenType.MINUS, "-")
                "/" -> tokens.Token(tokens.TokenType.DIV, "/")
                "*" -> tokens.Token(tokens.TokenType.MUL, "*")
                "^" -> tokens.Token(tokens.TokenType.POW, "^")
            })

            if ((this.current_char as String).toDoubleOrNull() != null) {
                println(this.current_char)
            }
        }
    }
}

var lexer = Lexer("1.2")

lexer.lex()