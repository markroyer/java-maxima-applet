/**
 * 
 */
package edu.umaine.cs.max;

import static edu.umaine.cs.max.MaximaScanner.TokenType.ASTERISK;
import static edu.umaine.cs.max.MaximaScanner.TokenType.DONE;
import static edu.umaine.cs.max.MaximaScanner.TokenType.EQUALS;
import static edu.umaine.cs.max.MaximaScanner.TokenType.HAT;
import static edu.umaine.cs.max.MaximaScanner.TokenType.IDENTIFIER;
import static edu.umaine.cs.max.MaximaScanner.TokenType.LBRACE;
import static edu.umaine.cs.max.MaximaScanner.TokenType.LPAREN;
import static edu.umaine.cs.max.MaximaScanner.TokenType.MINUS;
import static edu.umaine.cs.max.MaximaScanner.TokenType.PLUS;
import static edu.umaine.cs.max.MaximaScanner.TokenType.RBRACE;
import static edu.umaine.cs.max.MaximaScanner.TokenType.RPAREN;
import static edu.umaine.cs.max.MaximaScanner.TokenType.SLASH;
import static edu.umaine.cs.max.MaximaScanner.TokenType.UNKNOWNTYPE;

/**
 * <code>MaximaScanner</code> is used to find and return tokens in Maxima
 * expressions.
 * 
 * @author Mark Royer
 * 
 */
public class MaximaScanner {

    enum TokenType {
        LPAREN, RPAREN, LBRACE, RBRACE, IDENTIFIER, ASTERISK, SLASH, PLUS, MINUS, HAT, EQUALS, UNKNOWNTYPE, DONE
    };

    private String exp;

    private TokenType currentToken;

    private String identifier;

    private int pos;

    /**
     * Create a new scanner that will work on the given expression.
     * 
     * @param exp
     *            The expression to scan
     */
    public MaximaScanner(String exp) {
        this.exp = exp;
    }

    /**
     * Read in the next token.
     * 
     * @return The next token's type
     */
    public TokenType nextToken() {

        pos = eatWhiteSpace(pos);

        if (pos >= exp.length())
            return DONE;

        char c = exp.charAt(pos);

        if (isSymbol(c)) {

            pos++;

            if (c == '(') {
                currentToken = LPAREN;
            } else if (c == ')') {
                currentToken = RPAREN;
            } else if (c == '[') {
                currentToken = LBRACE;
            } else if (c == ']') {
                currentToken = RBRACE;
            } else if (c == '*') {
                currentToken = ASTERISK;
            } else if (c == '/') {
                currentToken = SLASH;
            } else if (c == '+') {
                currentToken = PLUS;
            } else if (c == '-') {
                currentToken = MINUS;
            } else if (c == '^') {
                currentToken = HAT;
            } else if (c == '=') {
                currentToken = EQUALS;
            } else {
                currentToken = UNKNOWNTYPE;
            }

        } else { // assume it's an identifier

            StringBuffer str = new StringBuffer();

            while (pos < exp.length() && !isSymbol(c)
                    && !Character.isWhitespace(c)) {
                str.append(c);
                pos++;
                if (pos < exp.length())
                    c = exp.charAt(pos);
            }

            currentToken = IDENTIFIER;
            identifier = str.toString();
        }

        pos = eatWhiteSpace(pos);

        return currentToken;
    }

    /**
     * Look at the next token's type, but do not actually read it in.
     * 
     * @return The next token's type
     */
    public TokenType peak() {

        int tmpPos = pos;
        tmpPos = eatWhiteSpace(tmpPos);

        if (pos >= exp.length())
            return DONE;

        char c = exp.charAt(tmpPos);

        if (c == '(') {
            return LPAREN;
        } else if (c == ')') {
            return RPAREN;
        } else if (c == '[') {
            return LBRACE;
        } else if (c == ']') {
            return RBRACE;
        } else if (c == '*') {
            return ASTERISK;
        } else if (c == '/') {
            return SLASH;
        } else if (c == '+') {
            return PLUS;
        } else if (c == '-') {
            return MINUS;
        } else if (c == '^') {
            return HAT;
        } else if (c == '=') {
            return EQUALS;
        } else {
            return IDENTIFIER;
        }
    }

    /**
     * Read in white space until a non-whitespace character is met. Return the
     * position of the non-whitespace character.
     * 
     * @param pos
     *            The current position
     * @return The current position
     */
    private int eatWhiteSpace(int pos) {
        while (pos < exp.length() && Character.isWhitespace(exp.charAt(pos))) {
            pos++;
        }

        return pos;
    }

    /**
     * The last token that was read.
     * 
     * @return The string representation of the current token
     */
    public String currentTokenString() {

        switch (currentToken) {
        case LPAREN:
            return "(";
        case RPAREN:
            return ")";
        case LBRACE:
            return "[";
        case RBRACE:
            return "]";
        case IDENTIFIER:
            return identifier;
        case ASTERISK:
            return "*";
        case SLASH:
            return "/";
        case PLUS:
            return "+";
        case MINUS:
            return "-";
        case HAT:
            return "^";
        case EQUALS:
            return "=";
        default: // UNKNOWNTYPE
            return "UNKNOWNTYPE";
        }
    }

    /**
     * @param c
     *            A character to test
     * @return True iff the character is symbolic
     */
    public boolean isSymbol(char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '*'
                || c == '/' || c == '+' || c == '-' || c == '^' || c == '=';
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "MacsymaScanner: " + this.exp;
    }

    /**
     * @return The current position in the given string expression
     */
    public int getPos() {
        return pos;
    }

    /**
     * @return The current token's type
     */
    public TokenType currentToken() {
        return currentToken;
    }
}
