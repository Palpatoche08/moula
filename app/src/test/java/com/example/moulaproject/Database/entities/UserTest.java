package com.example.moulaproject.Database.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.moulaproject.Database.entities.User;


//Nabil test Branch
public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("Nabil", "n", false);
    }

    @Test
    public void testGetName() {
        // Ensure the initial name matches the expected value
        assertEquals("Expected initial name to be 'Nabil'", "Nabil", user.getName());
    }

    @Test
    public void testSetName() {
        // Change the name and verify the new value
        user.setName("Omar");
        assertEquals("Expected name to be updated to 'Omar'", "Omar", user.getName());

        // Ensure the name is no longer the original value
        assertNotEquals("Expected name not to be 'Nabil'", "Nabil", user.getName());
    }

    @Test
    public void testGetPassword() {
        // Check that the initial password matches the expected value
        assertEquals("Expected initial password to be 'n'", "n", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        // Change the password and verify the new value
        user.setPassword("newpassword");
        assertEquals("Expected password to be updated to 'newpassword'", "newpassword", user.getPassword());
    }

    @Test
    public void testIsAdmin() {
        // Ensure the initial admin status matches the expected value
        assertFalse("Expected initial admin status to be 'false'", user.isAdmin());
    }

    @Test
    public void testSetAdmin() {
        // Change the admin status to true and verify
        user.setAdmin(true);
        assertTrue("Expected admin status to be updated to 'true'", user.isAdmin());

        // Revert to false and verify
        user.setAdmin(false);
        assertFalse("Expected admin status to be 'false'", user.isAdmin());
    }

    @Test
    public void testGetBalance() {
        // Check that the initial balance matches the expected value
        assertEquals("Expected initial balance to be 50", 50, user.getBalance());
    }

    @Test
    public void testSetBalance() {
        // Change the balance and verify the new value
        user.setBalance(100);
        assertEquals("Expected balance to be updated to 100", 100, user.getBalance());

        // Ensure the balance isn't set to the original value
        assertNotEquals("Expected balance not to be 50", 50, user.getBalance());
    }

    @Test
    public void testGetAndSetId() {
        // Initially, `id` might be unset or defaulted (typically 0 if auto-generated)
        assertEquals("Expected initial id to be 0", 0, user.getId());

        // Change the ID and verify the new value
        user.setId(101);
        assertEquals("Expected id to be updated to 101", 101, user.getId());
    }
}
