package com.bibliotheque.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurControllerTest {
    private UtilisateurController controller;

    @BeforeEach
    void setUp() {
        controller = new UtilisateurController();
    }

    @Test
    void testEmpruntUtilisateurManquant() {
        boolean result = controller.emprunterLivre("", "", "", "", "", LocalDate.now());
        assertFalse(result, "Emprunt avec tous les champs vides devrait échouer");
    }

    @Test
    void testEmpruntAvecChampsVides() {
        // Test avec nom vide
        assertFalse(controller.emprunterLivre("", "Dupont", "test@example.com", "0123456789", "ISBN123", LocalDate.now()),
                "Emprunt avec nom vide devrait échouer");

        // Test avec prénom vide
        assertFalse(controller.emprunterLivre("Jean", "", "test@example.com", "0123456789", "ISBN123", LocalDate.now()),
                "Emprunt avec prénom vide devrait échouer");

        // Test avec email vide
        assertFalse(controller.emprunterLivre("Jean", "Dupont", "", "0123456789", "ISBN123", LocalDate.now()),
                "Emprunt avec email vide devrait échouer");

        // Test avec téléphone vide
        assertFalse(controller.emprunterLivre("Jean", "Dupont", "test@example.com", "", "ISBN123", LocalDate.now()),
                "Emprunt avec téléphone vide devrait échouer");

        // Test avec ISBN vide
        assertFalse(controller.emprunterLivre("Jean", "Dupont", "test@example.com", "0123456789", "", LocalDate.now()),
                "Emprunt avec ISBN vide devrait échouer");
    }

    @Test
    void testEmpruntUtilisateurInexistant() {
        // Utilisateur qui n'existe probablement pas dans la base de données
        LocalDate date = LocalDate.now();
        boolean result = controller.emprunterLivre(
                "TestNom",
                "TestPrenom",
                "test" + System.currentTimeMillis() + "@example.com",
                "0987654321",
                "ISBN-INEXISTANT",
                date
        );

        assertFalse(result, "Emprunt avec utilisateur inexistant devrait échouer");
    }

    @Test
    void testEmpruntAvecNull() {
        // Teste chaque paramètre null individuellement
        assertAll(
                () -> assertFalse(controller.emprunterLivre(null, "Dupont", "test@example.com", "0123456789", "ISBN123", LocalDate.now()),
                        "Emprunt avec nom null devrait échouer"),
                () -> assertFalse(controller.emprunterLivre("Jean", null, "test@example.com", "0123456789", "ISBN123", LocalDate.now()),
                        "Emprunt avec prénom null devrait échouer"),
                () -> assertFalse(controller.emprunterLivre("Jean", "Dupont", null, "0123456789", "ISBN123", LocalDate.now()),
                        "Emprunt avec email null devrait échouer"),
                () -> assertFalse(controller.emprunterLivre("Jean", "Dupont", "test@example.com", null, "ISBN123", LocalDate.now()),
                        "Emprunt avec téléphone null devrait échouer"),
                () -> assertFalse(controller.emprunterLivre("Jean", "Dupont", "test@example.com", "0123456789", null, LocalDate.now()),
                        "Emprunt avec ISBN null devrait échouer"),
                () -> assertFalse(controller.emprunterLivre("Jean", "Dupont", "test@example.com", "0123456789", "ISBN123", null),
                        "Emprunt avec date null devrait échouer")
        );
    }

    @Test
    void testEmpruntAvecDateFuture() {
        LocalDate dateFuture = LocalDate.now().plusYears(1);
        boolean result = controller.emprunterLivre(
                "Jean", "Dupont", "test@example.com",
                "0123456789", "ISBN123", dateFuture
        );
        assertFalse(result, "Emprunt avec date future devrait échouer");
    }

    @Test
    void testEmpruntAvecDatePassee() {
        LocalDate datePassee = LocalDate.now().minusYears(1);
        boolean result = controller.emprunterLivre(
                "Jean", "Dupont", "test@example.com",
                "0123456789", "ISBN123", datePassee
        );
        assertFalse(result, "Emprunt avec date passée devrait échouer");
    }
}