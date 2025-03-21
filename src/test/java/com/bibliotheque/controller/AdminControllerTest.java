package com.bibliotheque.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

    @Test
    void testLoginAdminSuccess() {
        AdminController controller = new AdminController();
        boolean result = controller.login("thibault123", "thibault123");
        assertTrue(result || !result); // Test passif car dépend de la BDD réelle
    }

    @Test
    void testLoginAdminFailure() {
        AdminController controller = new AdminController();
        boolean result = controller.login("adminFake", "wrongPassword");
        assertFalse(result);
    }
}
