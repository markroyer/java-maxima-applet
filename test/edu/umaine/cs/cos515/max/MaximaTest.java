package edu.umaine.cs.cos515.max;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import edu.umaine.cs.cos515.max.Maxima;

/**
 * A collection of test to make sure the Maxima parser is working correctly.
 * 
 * @author Mark Royer
 * 
 */
public class MaximaTest {

    /**
     * Tests to make sure the expressions are correctly converted.
     */
    @Test
    public void testToJava() {

        try {
            assertEquals("Math.cos(exp)", Maxima.toJava("cos(exp)"));
            assertEquals("Math.sin(exp)", Maxima.toJava("sin(exp)"));
            assertEquals("Math.pow(exp,2)", Maxima.toJava("exp^2"));
            assertEquals("1+3*2-3", Maxima.toJava("1+3*2-3"));
            assertEquals("1+3*(2-3)", Maxima.toJava("1+3*(2-3)"));
            assertEquals("3/(4*3)", Maxima.toJava("3/(4*3)"));

            assertEquals("-(g)*Math.sin(theta)/r", Maxima
                    .toJava("-g*sin(theta)/r"));

            String eq1 = "f[5]=(Math.sin(x[2])*Math.sin(x[4])*(k2*r2-k2*r02)+Math.cos(x[2])*Math.cos(x[4])*(k2*r2-k2*r02)+Math.pow(x[6],2)*m1*r1-k1*r1+k1*r01+Math.cos(x[2])*g*m1)/m1";

            assertEquals(
                    eq1,
                    Maxima
                            .toJava("f[5] = (sin(x[2])*sin(x[4])*(k2*r2-k2*r02)+cos(x[2])*cos(x[4])*(k2*r2-k2*r02)+x[6]^2*m1*r1-k1*r1+k1*r01+cos(x[2])*g*m1)/m1"));

            String eq2 = "f[6]=(Math.cos(x[2])*Math.sin(x[4])*(k2*r2-k2*r02)+Math.sin(x[2])*Math.cos(x[4])*(k2*r02-k2*r2)-Math.sin(x[2])*g*m1-2*x[5]*x[6]*m1)/(m1*r1)";

            assertEquals(
                    eq2,
                    Maxima
                            .toJava("f[6] = (cos(x[2])*sin(x[4])*(k2*r2-k2*r02)+sin(x[2])*cos(x[4])*(k2*r02-k2*r2)-sin(x[2])*g*m1-2*x[5]*x[6]*m1)/(m1*r1)"));

        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
