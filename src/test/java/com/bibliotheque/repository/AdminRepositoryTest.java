package com.bibliotheque.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminRepositoryTest {

    @Test
    void testAuthentifierAdminFaux() {
        AdminRepository repo = new AdminRepository();
        boolean result = repo.authentifierAdmin("fakeadmin", "wrongpassword");
        assertFalse(result); // Doit échouer si l'admin n'existe pas
    }
}
