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
}
