package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivreControllerTest {

    @InjectMocks
    private LivreController livreController;

    @Mock
    private LivreRepository livreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLivres() {
        // Arrange
        List<Livre> livres = new ArrayList<>();
        livres.add(new Livre("1234567890", "Titre 1", "Auteur 1", 2020, "Neuf", "Disponible"));
        livres.add(new Livre("0987654321", "Titre 2", "Auteur 2", 2021, "Occasion", "Emprunté"));

        when(livreRepository.listerLivres()).thenReturn(livres);

        // Act
        List<Livre> result = livreController.getAllLivres();

        // Assert
        assertEquals(livres.size(), result.size());
        assertSame(livres, result);
        verify(livreRepository, times(1)).listerLivres();
    }

    @Test
    void testGetAllLivresEmpty() {
        // Arrange
        List<Livre> livres = new ArrayList<>();
        when(livreRepository.listerLivres()).thenReturn(livres);

        // Act
        List<Livre> result = livreController.getAllLivres();

        // Assert
        assertTrue(result.isEmpty());
        verify(livreRepository, times(1)).listerLivres();
    }

    @Test
    void testAjouterLivreAvecStatutNull() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut(null);

        // Act
        livreController.ajouterLivre(livre);

        // Assert
        assertNull(livre.getStatut());
        verify(livreRepository, times(1)).ajouterLivre(livre);
    }

    @Test
    void testAjouterLivre() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut("NEUF");

        // Act
        livreController.ajouterLivre(livre);

        // Assert
        assertEquals("neuf", livre.getStatut());
        verify(livreRepository, times(1)).ajouterLivre(livre);
    }

    @Test
    void testAjouterLivreAvecStatutDejaEnMinuscules() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut("neuf");

        // Act
        livreController.ajouterLivre(livre);

        // Assert
        assertEquals("neuf", livre.getStatut());
        verify(livreRepository, times(1)).ajouterLivre(livre);
    }

    @Test
    void testModifierLivreAvecStatutNull() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut(null);

        // Act
        livreController.modifierLivre(livre);

        // Assert
        assertNull(livre.getStatut());
        verify(livreRepository, times(1)).modifierLivre(livre);
    }

    @Test
    void testModifierLivre() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut("OCCASION");

        // Act
        livreController.modifierLivre(livre);

        // Assert
        assertEquals("occasion", livre.getStatut());
        verify(livreRepository, times(1)).modifierLivre(livre);
    }

    @Test
    void testModifierLivreAvecStatutDejaEnMinuscules() {
        // Arrange
        Livre livre = new Livre();
        livre.setStatut("occasion");

        // Act
        livreController.modifierLivre(livre);

        // Assert
        assertEquals("occasion", livre.getStatut());
        verify(livreRepository, times(1)).modifierLivre(livre);
    }

    @Test
    void testGetLivreFromISBN() {
        // Arrange
        String isbn = "9782744005084";

        // Act
        Livre livre = livreController.getLivreFromISBN(isbn);

        // Assert
        assertNotNull(livre);
        assertEquals(isbn, livre.getISBN());
    }

    @Test
    void testSupprimerLivre() {
        // Arrange
        String isbn = "9782744005084";

        // Act
        livreController.supprimerLivre(isbn);

        // Assert
        verify(livreRepository, times(1)).supprimerLivre(isbn);
    }
}