package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.utils.AgeCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgeCalculatorTest {

    @Test
    public void testCalculate_validDate() {
        String validDate = "10/27/1988";
        int age = AgeCalculator.calculate(validDate);
        assertEquals(35, age);
    }

    @Test
    public void testCalculate_invalidDate() {
        String invalidDate = "27-10-1988";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AgeCalculator.calculate(invalidDate);
        });
        assertEquals("Invalid date format. Expected format: MM/dd/yyyy", exception.getMessage());
    }

    @Test
    void testIsChild() {
        assertTrue(AgeCalculator.isChild(5));
        assertTrue(AgeCalculator.isChild(18));

        assertFalse(AgeCalculator.isChild(19));
    }

}
