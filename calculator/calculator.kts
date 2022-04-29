import java.math.BigInteger

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

data class BinOpNode(val leftNode: Any, val opTok: Token?, val rightNode: Any)
data class UnaryNode(val opTok: Token?, val num: String?)
data class NumberNode(val num: String?)

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

// Parser
class Parser(tokens: MutableList<Token>) {
    val tokens = tokens
    var position = -1
    var current_tok: Token? = null

    private fun advance(occurrences: Int = 1) {
        try {
            for (i in 0 until occurrences) {
                this.position += 1
                this.current_tok = tokens[position]
            }
        } catch (e: IndexOutOfBoundsException) {
            this.current_tok = null
        }
    }

    // Parses numbers (negative and positive)
    private fun factor(): Any {
        var token = this.current_tok
        this.advance()
        var next_token = this.current_tok

        return when (token?.tokValue) {
            "-" -> {
                this.advance()
                UnaryNode(opTok=token, num=next_token?.tokValue)
            }
            "+" -> {
                this.advance()
                UnaryNode(opTok=token, num=next_token?.tokValue)
            }
            else -> NumberNode(num=token?.tokValue)
        }
    }

    // Parses exponents
    private fun pow(): Any {
        var result = this.factor()

        while (this.current_tok?.tokType == TokenType.POW) {
            var symbol = this.current_tok
            this.advance()
            result = BinOpNode(result, symbol, this.factor())
        }

        return result
    }

    // Parses multiplication and division
    private fun term(): Any {
        var result = this.pow()

        while (this.current_tok?.tokType in listOf(TokenType.MUL, TokenType.DIV)) {
            var symbol = this.current_tok
            this.advance()
            result = BinOpNode(result, symbol, this.pow())
        }

        return result
    }

    // Parses addition and subtraction
    private fun expr(): Any {
        var result = this.term()

        while (this.current_tok?.tokType in listOf(TokenType.PLUS, TokenType.MINUS)) {
            var symbol = this.current_tok
            this.advance()
            result = BinOpNode(result, symbol, this.term())
        }

        return result
    }

    fun parse(): Any {
        this.advance()

        return this.expr()
    }
}

// Interpreter
class Interpreter(val ast: Any) {
    protected val functionHashmap = hashMapOf(
        "BinOpNode" to ::binOpNode,
        "UnaryNode" to ::unaryNode,
        "NumberNode" to ::numberNode
    )

    fun walk(node: Any = this.ast) {
        println(node::class.simpleName)
    }

    private fun binOpNode(node: BinOpNode) {
        println(node)
    }

    private fun unaryNode(node: BinOpNode) {
        println(node)
    }

    private fun numberNode(node: BinOpNode) {
        println(node)
    }
}

var interpreter = Interpreter(
    Parser(
        Lexer("1.2 + 3").lex()
    ).parse()
)

interpreter.walk()