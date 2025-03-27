package com.bibliotheque.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LivreTest {

    @Test
    void testLivreCreation() {
        Livre livre = new Livre("1234567890", "Titre", "Auteur", 2020, "neuf", "disponible");

        assertEquals("1234567890", livre.getISBN());
        assertEquals("Titre", livre.getTitre());
        assertEquals("Auteur", livre.getAuteur());
        assertEquals(2020, livre.getAnneePublication());
        assertEquals("neuf", livre.getEtat());
        assertEquals("disponible", livre.getStatut());
    }

    @Test
    void testConstructeurVide() {
        Livre livre = new Livre();

        // Vérifier que l'objet est créé (non null)
        assertNotNull(livre);

        // Définir et vérifier les propriétés
        livre.setISBN("9876543210");
        livre.setTitre("Nouveau Titre");
        livre.setAuteur("Nouvel Auteur");
        livre.setAnneePublication(2022);
        livre.setEtat("usé");
        livre.setStatut("emprunté");

        assertEquals("9876543210", livre.getISBN());
        assertEquals("Nouveau Titre", livre.getTitre());
        assertEquals("Nouvel Auteur", livre.getAuteur());
        assertEquals(2022, livre.getAnneePublication());
        assertEquals("usé", livre.getEtat());
        assertEquals("emprunté", livre.getStatut());
    }

    @Test
    void testEquals() {
        Livre livre1 = new Livre("1234567890", "Titre1", "Auteur1", 2020, "neuf", "disponible");
        Livre livre2 = new Livre("1234567890", "Titre2", "Auteur2", 2021, "usé", "emprunté");
        Livre livre3 = new Livre("9876543210", "Titre1", "Auteur1", 2020, "neuf", "disponible");

        // Equals se base uniquement sur l'ISBN
        assertEquals(livre1, livre2);
        assertNotEquals(livre1, livre3);
        assertNotEquals(livre1, null);
        assertNotEquals(livre1, new Object());
        assertEquals(livre1, livre1); // Réflexivité
    }

    @Test
    void testHashCode() {
        Livre livre1 = new Livre("1234567890", "Titre1", "Auteur1", 2020, "neuf", "disponible");
        Livre livre2 = new Livre("1234567890", "Titre2", "Auteur2", 2021, "usé", "emprunté");

        // Les hash codes devraient être identiques si les ISBN sont identiques
        assertEquals(livre1.hashCode(), livre2.hashCode());
    }

    @Test
    void testToString() {
        Livre livre = new Livre("1234567890", "Titre Test", "Auteur Test", 2020, "neuf", "disponible");
        String toStringResult = livre.toString();

        // Vérifier que toString contient toutes les informations importantes
        assertTrue(toStringResult.contains("1234567890"));
        assertTrue(toStringResult.contains("Titre Test"));
        assertTrue(toStringResult.contains("Auteur Test"));
        assertTrue(toStringResult.contains("2020"));
        assertTrue(toStringResult.contains("neuf"));
        assertTrue(toStringResult.contains("disponible"));
    }
}