package com.bibliotheque.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    void testConstructeurEtGetters() {
        Utilisateur utilisateur = new Utilisateur("Doe", "John", "0600000000", "john.doe@email.com", "1234567890", LocalDate.of(2024, 1, 1));

        assertEquals("Doe", utilisateur.getNom());
        assertEquals("John", utilisateur.getPrenom());
        assertEquals("0600000000", utilisateur.getTelephone());
        assertEquals("john.doe@email.com", utilisateur.getEmail());
        assertEquals("1234567890", utilisateur.getLivreId());
        assertEquals(LocalDate.of(2024, 1, 1), utilisateur.getDateEmprunt());
    }

    @Test
    void testSetters() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Smith");
        utilisateur.setPrenom("Anna");
        utilisateur.setTelephone("0612345678");
        utilisateur.setEmail("anna.smith@email.com");
        utilisateur.setLivreId("9876543210");
        utilisateur.setDateEmprunt(LocalDate.of(2025, 3, 20));

        assertEquals("Smith", utilisateur.getNom());
        assertEquals("Anna", utilisateur.getPrenom());
        assertEquals("0612345678", utilisateur.getTelephone());
        assertEquals("anna.smith@email.com", utilisateur.getEmail());
        assertEquals("9876543210", utilisateur.getLivreId());
        assertEquals(LocalDate.of(2025, 3, 20), utilisateur.getDateEmprunt());
    }

    @Test
    void testIdGetterSetter() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(123);
        assertEquals(123, utilisateur.getId());
    }

    @Test
    void testEquals() {
        Utilisateur utilisateur1 = new Utilisateur("Doe", "John", "0600000000", "john.doe@email.com", "1234567890", LocalDate.of(2024, 1, 1));
        utilisateur1.setId(1);

        Utilisateur utilisateur2 = new Utilisateur("Smith", "Anna", "0612345678", "anna.smith@email.com", "9876543210", LocalDate.of(2025, 3, 20));
        utilisateur2.setId(1);

        Utilisateur utilisateur3 = new Utilisateur("Doe", "John", "0600000000", "john.doe@email.com", "1234567890", LocalDate.of(2024, 1, 1));
        utilisateur3.setId(2);

        // Même ID = égaux, peu importe les autres champs
        assertEquals(utilisateur1, utilisateur2);

        // IDs différents = pas égaux
        assertNotEquals(utilisateur1, utilisateur3);

        // Autres cas
        assertNotEquals(utilisateur1, null);
        assertNotEquals(utilisateur1, new Object());
        assertEquals(utilisateur1, utilisateur1);  // Réflexivité
    }

    @Test
    void testHashCode() {
        Utilisateur utilisateur1 = new Utilisateur("Doe", "John", "0600000000", "john.doe@email.com", "1234567890", LocalDate.of(2024, 1, 1));
        utilisateur1.setId(42);

        Utilisateur utilisateur2 = new Utilisateur("Smith", "Anna", "0612345678", "anna.smith@email.com", "9876543210", LocalDate.of(2025, 3, 20));
        utilisateur2.setId(42);

        // Même ID = même hashCode
        assertEquals(utilisateur1.hashCode(), utilisateur2.hashCode());
    }

    @Test
    void testToString() {
        Utilisateur utilisateur = new Utilisateur("Doe", "John", "0600000000", "john.doe@email.com", "1234567890", LocalDate.of(2024, 1, 1));
        utilisateur.setId(42);

        String toStringResult = utilisateur.toString();

        // Vérifier que toutes les propriétés sont présentes
        assertTrue(toStringResult.contains("id=42"));
        assertTrue(toStringResult.contains("nom='Doe'"));
        assertTrue(toStringResult.contains("prenom='John'"));
        assertTrue(toStringResult.contains("telephone='0600000000'"));
        assertTrue(toStringResult.contains("email='john.doe@email.com'"));
        assertTrue(toStringResult.contains("livreId='1234567890'"));
        assertTrue(toStringResult.contains("dateEmprunt=2024-01-01"));
    }
}