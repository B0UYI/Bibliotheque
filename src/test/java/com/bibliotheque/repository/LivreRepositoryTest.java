package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class LivreRepositoryTest {

    @Test
    void testListerLivres() {
        LivreRepository repo = new LivreRepository();
        List<Livre> livres = repo.listerLivres();
        assertNotNull(livres);
    }
}
