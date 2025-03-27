package com.bibliotheque.repository;

import com.bibliotheque.model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurRepositoryTest {
    private UtilisateurRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UtilisateurRepository();
    }

    @Test
    void testAjouterUtilisateurSiInexistant() {
        int resultat = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );
        assertTrue(resultat > 0, "L'ajout d'un nouvel utilisateur devrait retourner un ID positif");
    }

    @Test
    void testAjouterUtilisateurExistant() {
        // Ajouter un premier utilisateur
        int premierID = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        // Réessayer avec le même email
        int deuxiemeID = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        assertEquals(premierID, deuxiemeID, "L'ajout d'un utilisateur existant devrait retourner le même ID");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void testAjouterUtilisateurAvecChampsVides(String champVide) {
        int resultat = repository.ajouterUtilisateurSiInexistant(
                champVide, champVide, champVide + "@email.com", champVide
        );
        assertEquals(-1, resultat, "L'ajout avec des champs vides devrait échouer");
    }

    @Test
    void testAjouterUtilisateurAvecNull() {
        assertAll(
                () -> assertEquals(-1, repository.ajouterUtilisateurSiInexistant(null, "Dupont", "test@email.com", "0123"),
                        "L'ajout avec nom null devrait échouer"),
                () -> assertEquals(-1, repository.ajouterUtilisateurSiInexistant("Jean", null, "test@email.com", "0123"),
                        "L'ajout avec prénom null devrait échouer"),
                () -> assertEquals(-1, repository.ajouterUtilisateurSiInexistant("Jean", "Dupont", null, "0123"),
                        "L'ajout avec email null devrait échouer"),
                () -> assertEquals(-1, repository.ajouterUtilisateurSiInexistant("Jean", "Dupont", "test@email.com", null),
                        "L'ajout avec téléphone null devrait échouer")
        );
    }

    @Test
    void testAjouterEmprunt() {
        // Ajouter d'abord un utilisateur
        int utilisateurId = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        boolean resultat = repository.ajouterEmprunt(
                utilisateurId, "1234567890", LocalDate.now()
        );

        assertTrue(resultat, "L'ajout d'un emprunt avec un utilisateur valide devrait réussir");
    }

    @Test
    void testAjouterEmpruntUtilisateurInexistant() {
        boolean resultat = repository.ajouterEmprunt(
                -1, "1234567890", LocalDate.now()
        );

        assertFalse(resultat, "L'ajout d'un emprunt avec un utilisateur inexistant devrait échouer");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "isbn-invalide"})
    void testAjouterEmpruntAvecIsbnInvalide(String isbn) {
        int utilisateurId = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        boolean resultat = repository.ajouterEmprunt(utilisateurId, isbn, LocalDate.now());
        assertFalse(resultat, "L'ajout d'un emprunt avec un ISBN invalide devrait échouer");
    }

    @Test
    void testAjouterEmpruntAvecDateFuture() {
        int utilisateurId = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        LocalDate dateFuture = LocalDate.now().plusYears(1);
        boolean resultat = repository.ajouterEmprunt(utilisateurId, "1234567890", dateFuture);

        assertFalse(resultat, "L'ajout d'un emprunt avec une date future devrait échouer");
    }

    @Test
    void testAjouterEmpruntAvecDatePassee() {
        int utilisateurId = repository.ajouterUtilisateurSiInexistant(
                "Dupont", "Jean", "jean.dupont@email.com", "0123456789"
        );

        LocalDate datePassee = LocalDate.now().minusYears(1);
        boolean resultat = repository.ajouterEmprunt(utilisateurId, "1234567890", datePassee);

        assertFalse(resultat, "L'ajout d'un emprunt avec une date passée devrait échouer");
    }
}