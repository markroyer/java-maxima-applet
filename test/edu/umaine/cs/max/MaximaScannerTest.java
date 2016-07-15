package edu.umaine.cs.max;

import org.junit.Test;

import edu.umaine.cs.max.MaximaScanner;

import static edu.umaine.cs.max.MaximaScanner.TokenType.*;
import static org.junit.Assert.*;

/**
 * A collection of tests to make sure that the Maxima scanner is working
 * correctly.
 * 
 * @author Mark Royer
 * 
 */
public class MaximaScannerTest {

    /**
     * Make sure that the next token operator works.
     */
    @Test
    public void testNextToken() {

        MaximaScanner scan = new MaximaScanner(
                "f[2] = -(x[4]^3*(x[2]*cos(x[3])*sin(x[3])*(4*cos(x[1])^2*l1*l2*m2^2-2*l1*l2*m2^2)-4*x[2]*cos(x[1])*sin(x[1])*cos(x[3])^2*l1*l2*m2^2+2*x[2]*cos(x[1])*sin(x[1])*l1*l2*m2^2)+x[4]^2*(sin(x[3])*(2*cos(x[1])*cos(x[3])*g*l2*m2^2+x[2]^2*cos(x[1])*l1^2*m2^2+2*cos(x[1])^2*g*l1*m2^2)+cos(x[3])*(-x[2]^2*sin(x[1])*l1^2*m2^2-2*cos(x[1])*sin(x[1])*g*l1*m2^2)-2*sin(x[1])*cos(x[3])^2*g*l2*m2^2)+x[4]^4*(cos(x[1])*sin(x[3])*l2^2*m2^2-sin(x[1])*cos(x[3])*l2^2*m2^2)+cos(x[3])*sin(x[3])*(x[2]^4*(2*cos(x[1])^2*l1^2*m1*m2-l1^2*m1*m2)+x[2]^2*(4*cos(x[1])^3*g*l1*m1*m2-2*cos(x[1])*g*l1*m1*m2)+2*cos(x[1])*g*m2)+cos(x[3])^2*(-2*x[2]^4*cos(x[1])*sin(x[1])*l1^2*m1*m2-4*x[2]^2*cos(x[1])^2*sin(x[1])*g*l1*m1*m2-2*sin(x[1])*g*m2)+x[2]^4*cos(x[1])*sin(x[1])*l1^2*m1*m2+2*x[2]^2*cos(x[1])^2*sin(x[1])*g*l1*m1*m2-2*sin(x[1])*g*m1)/(cos(x[3])^2*(x[2]^2*(2*cos(x[1])^2*l1^2*m1*m2-l1^2*m1*m2)+4*cos(x[1])^3*g*l1*m1*m2-2*cos(x[1])*g*l1*m1*m2)+cos(x[3])*sin(x[3])*(2*x[2]^2*cos(x[1])*sin(x[1])*l1^2*m1*m2+4*cos(x[1])^2*sin(x[1])*g*l1*m1*m2)+x[2]^2*(-cos(x[1])^2*l1^2*m1*m2-l1^2*m1^2)-2*cos(x[1])^3*g*l1*m1*m2-2*cos(x[1])*g*l1*m1^2)");

        assertEquals(IDENTIFIER, scan.nextToken());
        assertEquals("f", scan.currentTokenString());
        assertEquals(LBRACE, scan.peak());

        assertEquals(LBRACE, scan.nextToken());
        assertEquals("[", scan.currentTokenString());
        assertEquals(IDENTIFIER, scan.peak());

        assertEquals(IDENTIFIER, scan.nextToken());
        assertEquals("2", scan.currentTokenString());
        assertEquals(RBRACE, scan.peak());

        assertEquals(RBRACE, scan.nextToken());
        assertEquals("]", scan.currentTokenString());
        assertEquals(EQUALS, scan.peak());

        assertEquals(EQUALS, scan.nextToken());
        assertEquals("=", scan.currentTokenString());
        assertEquals(MINUS, scan.peak());

        assertEquals(MINUS, scan.nextToken());
        assertEquals("-", scan.currentTokenString());
        assertEquals(LPAREN, scan.peak());

        assertEquals(LPAREN, scan.nextToken());
        assertEquals("(", scan.currentTokenString());
        assertEquals(IDENTIFIER, scan.peak());

        assertEquals(IDENTIFIER, scan.nextToken());
        assertEquals("x", scan.currentTokenString());
        assertEquals(LBRACE, scan.peak());

        assertEquals(LBRACE, scan.nextToken());
        assertEquals("[", scan.currentTokenString());
        assertEquals(IDENTIFIER, scan.peak());

        assertEquals(IDENTIFIER, scan.nextToken());
        assertEquals("4", scan.currentTokenString());
        assertEquals(RBRACE, scan.peak());

        assertEquals(RBRACE, scan.nextToken());
        assertEquals("]", scan.currentTokenString());
        assertEquals(HAT, scan.peak());

        assertEquals(HAT, scan.nextToken());
        assertEquals("^", scan.currentTokenString());
        assertEquals(IDENTIFIER, scan.peak());

        assertEquals(IDENTIFIER, scan.nextToken());
        assertEquals("3", scan.currentTokenString());
        assertEquals(ASTERISK, scan.peak());

    }

}
