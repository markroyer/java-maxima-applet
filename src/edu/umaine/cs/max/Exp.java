/**
 * 
 */
package edu.umaine.cs.max;

import static edu.umaine.cs.max.Exp.ExpType.*;

/**
 * Represents a Java mathematical expression.
 * 
 * @author Mark Royer
 * 
 */
public class Exp {

    enum ExpType {
        SIN, COS, ARRAY, MULTIPLY, DIVIDE, ADD, SUBTRACT, PLUS, MINUS, EQUALS, IDENTIFIER, UNKNOWNTYPE, POWER, TAN
    };

    private ExpType type;

    private String identifier;

    // If binary expression both are non-null, only left if unary
    private Exp leftExpression;

    private Exp rightExpression;

    /**
     * Create a new expression element
     * 
     * @param type
     *            The expression type
     * @param left
     *            The left sub-expression
     * @param right
     *            The right sub-expression (if binary)
     * @param identifier
     *            The identifier, if needed
     */
    public Exp(ExpType type, Exp left, Exp right, String identifier) {
        this.type = type;
        this.leftExpression = left;
        this.rightExpression = right;
        this.identifier = identifier;
    }

    /**
     * The representation of this expression
     */
    public String toString() {

        switch (type) {
        case SIN:
            return "Math.sin(" + leftExpression + ")";
        case COS:
            return "Math.cos(" + leftExpression + ")";
        case TAN:
            return "Math.tan(" + leftExpression + ")";
        case ARRAY:
            return identifier + "[" + leftExpression + "]";
        case MULTIPLY:
        case DIVIDE:
        case ADD:
        case SUBTRACT:
        case EQUALS:
            return addParen(leftExpression) + valueOf()
                    + addParen(rightExpression);
        case POWER:
            return "Math.pow(" + leftExpression + "," + rightExpression + ")";
        case PLUS:
        case MINUS:
            return valueOf() + "(" + leftExpression + ")";
        case IDENTIFIER:
            return identifier;
        default:
            return valueOf();
        }

    }

    /**
     * Adds parentheses around the sub-expression if this expression has a
     * higher precedence.
     * 
     * @param subExpression
     *            This expression's sub-expression
     * @return The sub-expression's string representation surrounded by
     *         parentheses if needed
     */
    private String addParen(Exp subExpression) {

        ExpType subType = subExpression.getType();

        if (subType.equals(ADD) || subType.equals(SUBTRACT)) {
            if (type.equals(MULTIPLY) || type.equals(DIVIDE)
                    || type.equals(POWER)) {
                return "(" + subExpression + ")";
            }
        } else if (subType.equals(MULTIPLY)
                && subExpression.equals(this.rightExpression)) {
            if (type.equals(DIVIDE)) {
                return "(" + subExpression + ")";
            }
        }

        return subExpression.toString();
    }

    /**
     * @return The string representation of this expression ignoring the
     *         subexpression
     */
    public String valueOf() {

        switch (type) {
        case SIN:
            return "sin";
        case COS:
            return "cos";
        case TAN:
            return "tan";
        case MULTIPLY:
            return "*";
        case DIVIDE:
            return "/";
        case ADD:
        case PLUS:
            return "+";
        case SUBTRACT:
        case MINUS:
            return "-";
        case EQUALS:
            return "=";
        case POWER:
            return "^";
        case IDENTIFIER:
        case ARRAY:
            return identifier;
        default:
            return "UNKNOWNTYPE";
        }
    }

    /**
     * @return The type of this expression
     */
    public ExpType getType() {
        return type;
    }
}
