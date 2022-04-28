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

data class Token(val tokType: TokenType, val tokValue: String)

// Nodes
data class BinOpNode(val leftNode: Token, val opTok: Token, val rightNode: Token)
data class UnaryNode(val opTok: Token, val node: Token)
data class NumberNode(val numTok: Token)

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

    private fun make_int_or_float() : Token {
        var tok_value = ""
        while (
            ((this.current_char.toString().toDoubleOrNull() != null) || this.current_char == '.')
            && (this.current_char != null)
        ) {
            tok_value += this.current_char.toString()
            this.advance()
        }

        return if ("." !in tok_value) Token(TokenType.INT, tok_value) else Token(TokenType.FLOAT, tok_value)
    }

    fun lex() : MutableList<Token> {
        this.advance()
        var tokens: MutableList<Token> = mutableListOf()

        while (this.current_char != null) {
            // Skip whitespace
            if (this.current_char == ' ') {
                this.advance()
                continue
            }
            // Basic one-letter tokens
            var current_token = when (this.current_char) {
                '+' -> Token(TokenType.PLUS, "+")
                '-' -> Token(TokenType.MINUS, "-")
                '/' -> Token(TokenType.DIV, "/")
                '*' -> Token(TokenType.MUL, "*")
                '^' -> Token(TokenType.POW, "^")
                '(' -> Token(TokenType.LPAREN, "(")
                ')' -> Token(TokenType.RPAREN, ")")
                else -> null
            }

            if (current_token == null) {
                current_token = this.make_int_or_float()
            }

            this.advance()
            tokens.add(current_token)
        }

        return tokens
    }
}
