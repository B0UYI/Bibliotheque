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
}
