package com.bibliotheque.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testGettersAndSetters() {
        Admin admin = new Admin();
        admin.setCodeAdmin("admin1");
        admin.setPassword("password123");

        assertEquals("admin1", admin.getCodeAdmin());
        assertEquals("password123", admin.getPassword());
    }

    @Test
    void testToStringDoesNotRevealPassword() {
        Admin admin = new Admin("admin1", "secret");
        String result = admin.toString();
        assertFalse(result.contains("secret"));
        assertTrue(result.contains("********"));
    }
}
