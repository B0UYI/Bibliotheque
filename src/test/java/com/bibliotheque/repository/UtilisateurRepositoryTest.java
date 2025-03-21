package com.bibliotheque.repository;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurRepositoryTest {

    @Test
    void testAjouterEmpruntFauxUtilisateur() {
        UtilisateurRepository repo = new UtilisateurRepository();
        boolean result = repo.ajouterEmprunt(-1, "0000000000", LocalDate.now());
        assertFalse(result); // -1 n'est pas un ID valide
    }
}
