/**
 * 
 */
package edu.umaine.cs.max;

import static edu.umaine.cs.max.MaximaScanner.TokenType.*;

import java.text.ParseException;

import edu.umaine.cs.max.Exp.ExpType;
import edu.umaine.cs.max.MaximaScanner.TokenType;

/**
 * @author Mark Royer
 * 
 */
public class Maxima {

    /**
     * Converts a Maxima expression and returns its equivalent expression in
     * Java.
     * 
     * @param maximaExpression
     *            An expression in Maxima syntax
     * @return The Java equivalent version of the given expression
     * @throws ParseException
     *             Thrown if there is a syntactical error or if the expression
     *             contains symbols that are not part of the subset this parser
     *             recognizes.
     */
    public static String toJava(String maximaExpression) throws ParseException {
        return new Maxima(maximaExpression).parsedExpression.toString();
    }

    /**
     * Used to scan the Maxima expression for tokens.
     */
    private MaximaScanner scanner;

    /**
     * Used to store the expression after it has been parsed.
     */
    private Exp parsedExpression;

    /**
     * Create a new Maxima object that contains the parsed expression.
     * 
     * @param exp
     *            A Maxima expression
     * @throws ParseException
     *             Thrown if there is a problem parsing the given expression
     */
    Maxima(String exp) throws ParseException {
        scanner = new MaximaScanner(exp);

        if (scanner.peak() == TokenType.DONE) {
            throw new ParseException("No input", scanner.getPos());
        }

        parsedExpression = parseEqExp();

        if (scanner.peak() != TokenType.DONE) {
            scanner.nextToken();
            throw new ParseException("Extra token? "
                    + scanner.currentTokenString(), scanner.getPos());
        }
    }

    private Exp parseEqExp() throws ParseException {

        Exp left = parseAddExp();

        Exp right;

        TokenType nextToken = scanner.peak();

        if (EQUALS.equals(nextToken)) {
            scanner.nextToken(); // '='
            right = parseAddExp();
            return new Exp(ExpType.EQUALS, left, right, null);
        }

        return left;
    }

    private Exp parseAddExp() throws ParseException {

        Exp left = parseMulExp();

        Exp right;

        TokenType nextToken = scanner.peak();

        while (nextToken.equals(PLUS) || nextToken.equals(MINUS)) {
            if (PLUS.equals(nextToken)) {
                scanner.nextToken(); // '+'
                right = parseMulExp();
                left = new Exp(ExpType.ADD, left, right, null);
            } else if (MINUS.equals(nextToken)) {
                scanner.nextToken(); // '-'
                right = parseMulExp();
                left = new Exp(ExpType.SUBTRACT, left, right, null);
            }

            nextToken = scanner.peak();
        }

        return left;
    }

    private Exp parseMulExp() throws ParseException {

        Exp left = parsePowExp();

        Exp right;

        TokenType nextToken = scanner.peak();

        while (ASTERISK.equals(nextToken) || SLASH.equals(nextToken)
                || HAT.equals(nextToken)) {

            if (ASTERISK.equals(nextToken)) {
                scanner.nextToken(); // '*'
                right = parsePowExp();
                left = new Exp(ExpType.MULTIPLY, left, right, null);
            } else if (SLASH.equals(nextToken)) {
                scanner.nextToken(); // '/'
                right = parsePowExp();
                left = new Exp(ExpType.DIVIDE, left, right, null);
            }

            nextToken = scanner.peak();

        }

        return left;

    }

    private Exp parsePowExp() throws ParseException {

        Exp left = parseUnaryExp();

        Exp right;

        TokenType nextToken = scanner.peak();

        while (HAT.equals(nextToken)) {

            if (HAT.equals(nextToken)) {
                scanner.nextToken(); // '^'
                right = parseUnaryExp();
                left = new Exp(ExpType.POWER, left, right, null);
            }

            nextToken = scanner.peak();

        }

        return left;

    }

    private Exp parseUnaryExp() throws ParseException {

        TokenType nextType = scanner.peak();

        if (DONE.equals(scanner.peak())) {
            return null;
        }

        if (PLUS.equals(nextType)) {
            scanner.nextToken();// '+'
            return new Exp(ExpType.PLUS, parseFunExp(), null, null);
        } else if (MINUS.equals(nextType)) {
            scanner.nextToken();// '-'
            return new Exp(ExpType.MINUS, parseFunExp(), null, null);
        } else {
            return parseFunExp();
        }
    }

    private Exp parseFunExp() throws ParseException {

        TokenType nextType = scanner.peak();

        Exp left;

        if (LPAREN.equals(nextType)) {
            scanner.nextToken(); // read '('
            left = parseAddExp();
            scanner.nextToken(); // read ')'
            return left;
        }

        if (IDENTIFIER.equals(nextType)) {
            scanner.nextToken();
            String ident = scanner.currentTokenString();

            nextType = scanner.peak();

            if (LPAREN.equals(nextType)) {
                nextType = scanner.nextToken(); // '('
                if (ident.equals("cos")) {
                    left = new Exp(ExpType.COS, parseAddExp(), null, null);
                    scanner.nextToken(); // ')'
                    return left;
                } else if (ident.equals("sin")) {
                    left = new Exp(ExpType.SIN, parseAddExp(), null, null);
                    scanner.nextToken(); // ')'
                    return left;
                } else if (ident.equals("tan")) {
                    left = new Exp(ExpType.TAN, parseAddExp(), null, null);
                    scanner.nextToken(); // ')'
                    return left;
                } else {
                    throw new ParseException("function name? " + ident, scanner
                            .getPos());
                }

            } else if (LBRACE.equals(nextType)) {
                nextType = scanner.nextToken(); // '['
                left = new Exp(ExpType.ARRAY, parseAddExp(), null, ident);
                scanner.nextToken(); // ']'
                return left;

            } else {
                return new Exp(ExpType.IDENTIFIER, null, null, ident);
            }
        }

        scanner.nextToken();
        throw new ParseException("Identifier expected, but found "
                + scanner.currentTokenString(), scanner.getPos());
    }
}
