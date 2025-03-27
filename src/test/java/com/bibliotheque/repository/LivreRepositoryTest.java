package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

class LivreRepositoryTest {

    @Test
    void testListerLivres() {
        LivreRepository repo = new LivreRepository();
        List<Livre> livres = repo.listerLivres();
        assertNotNull(livres);
    }

    @Test
    void testAjouterEtSupprimerLivre() {
        LivreRepository repo = new LivreRepository();

        // Génération d'un ISBN unique pour éviter les conflits
        String testISBN = "TEST-" + UUID.randomUUID().toString().substring(0, 8);
        Livre livre = new Livre(testISBN, "Titre Test", "Auteur Test", 2025, "neuf", "disponible");

        // Test de l'ajout
        repo.ajouterLivre(livre);

        // Test de la suppression
        repo.supprimerLivre(testISBN);

        // Pas d'assertions explicites car nous vérifions seulement que les méthodes
        // ne lancent pas d'exceptions
    }

    @Test
    void testModifierLivre() {
        LivreRepository repo = new LivreRepository();

        // Génération d'un ISBN unique pour éviter les conflits
        String testISBN = "TEST-" + UUID.randomUUID().toString().substring(0, 8);
        Livre livre = new Livre(testISBN, "Titre Original", "Auteur Original", 2025, "neuf", "disponible");

        // Ajouter d'abord le livre
        repo.ajouterLivre(livre);

        // Modifier le livre
        livre.setTitre("Titre Modifié");
        livre.setAuteur("Auteur Modifié");
        repo.modifierLivre(livre);

        // Nettoyer
        repo.supprimerLivre(testISBN);

        // Pas d'assertions explicites car nous vérifions seulement que les méthodes
        // ne lancent pas d'exceptions
    }

    @Test
    void testMettreAJourStatutLivre() {
        LivreRepository repo = new LivreRepository();

        // Génération d'un ISBN unique pour éviter les conflits
        String testISBN = "TEST-" + UUID.randomUUID().toString().substring(0, 8);
        Livre livre = new Livre(testISBN, "Titre Test", "Auteur Test", 2025, "neuf", "disponible");

        // Ajouter d'abord le livre
        repo.ajouterLivre(livre);

        // Mettre à jour le statut
        repo.mettreAJourStatutLivre(testISBN, "emprunté");

        // Nettoyer
        repo.supprimerLivre(testISBN);

        // Pas d'assertions explicites car nous vérifions seulement que la méthode
        // ne lance pas d'exception
    }

    @Test
    void testOperationsAvecLivreInexistant() {
        LivreRepository repo = new LivreRepository();

        // ISBN qui n'existe certainement pas
        String nonExistentISBN = "NONEXISTENT-" + UUID.randomUUID();

        // Ces opérations devraient échouer silencieusement, sans lancer d'exception
        repo.supprimerLivre(nonExistentISBN);
        repo.mettreAJourStatutLivre(nonExistentISBN, "indisponible");

        Livre livreFictif = new Livre(nonExistentISBN, "N'existe Pas", "Auteur Fictif", 2099, "inconnu", "inconnu");
        repo.modifierLivre(livreFictif);

        // Pas d'assertions explicites car nous vérifions seulement que les méthodes
        // ne lancent pas d'exceptions
    }
}