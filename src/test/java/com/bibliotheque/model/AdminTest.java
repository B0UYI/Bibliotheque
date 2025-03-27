package com.bibliotheque.model;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
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
    void testConstructorWithParameters() {
        Admin admin = new Admin("admin1", "password123");
        assertEquals("admin1", admin.getCodeAdmin());
        assertEquals("password123", admin.getPassword());
    }

    @Test
    void testCheckPassword() {
        // Création d'un mot de passe haché avec BCrypt
        String plainPassword = "mySecretPassword";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        // Création de l'admin avec le mot de passe haché
        Admin admin = new Admin("admin1", hashedPassword);

        // Vérification avec le bon mot de passe
        assertTrue(admin.checkPassword(plainPassword));

        // Vérification avec un mauvais mot de passe
        assertFalse(admin.checkPassword("wrongPassword"));
    }

    @Test
    void testToStringDoesNotRevealPassword() {
        Admin admin = new Admin("admin1", "secret");
        String result = admin.toString();
        assertFalse(result.contains("secret"));
        assertTrue(result.contains("********"));
    }
}