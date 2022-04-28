package tokens


enum class TokenType {
    INT,
    FLOAT,
    PLUS,
    MINUS,
    DIV,
    MUL,
    POW
}

data class Token(val tokType: TokenType, val tokValue: String)
