package com.example.moulaproject.Database.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.moulaproject.Database.entities.Currency;

//Thomas test
public class CurrencyTest {
    private Currency currency;

    // Initialize a Currency instance before each test
    @Before
    public void setUp() {
        currency = new Currency("USD", 1.0); // Example setup
    }

    @Test
    public void testGetName() {
        // Ensure the initial name matches the expected value
        assertEquals("Expected initial currency name to be 'USD'", "USD", currency.getName());
    }

    @Test
    public void testSetName() {
        // Change the name and verify the new value
        currency.setName("EUR");
        assertEquals("Expected currency name to be updated to 'EUR'", "EUR", currency.getName());

        // Ensure the name is no longer the original value
        assertNotEquals("Expected currency name not to be 'USD'", "USD", currency.getName());
    }

    @Test
    public void testGetRate() {
        // Ensure the initial rate matches the expected value
        assertEquals("Expected initial exchange rate to be 1.0", 1.0, currency.getRate(), 0.0);
    }

    @Test
    public void testSetRate() {
        // Change the rate and verify the new value
        currency.setRate(0.9);
        assertEquals("Expected exchange rate to be updated to 0.9", 0.9, currency.getRate(), 0.0);

        // Ensure the rate isn't the original value
        assertNotEquals("Expected exchange rate not to be 1.0", 1.0, currency.getRate(), 0.0);
    }
}
