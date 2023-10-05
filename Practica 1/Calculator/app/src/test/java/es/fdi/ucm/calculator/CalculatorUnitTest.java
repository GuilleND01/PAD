package es.fdi.ucm.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorUnitTest {
    private static final double DELTA = 1e-15;
    @Test
    public void addition_isCorrect() {
        Calculator c = new Calculator();
        assertEquals(4, c.addNumbers(2, 2), DELTA);
        assertEquals(4.4, c.addNumbers(2.4, 2), DELTA);
    }
}
