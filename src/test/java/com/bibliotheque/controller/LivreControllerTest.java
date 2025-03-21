package com.bibliotheque.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivreControllerTest {

    @Test
    void testGetLivreFromISBNInvalide() {
        LivreController controller = new LivreController();
        assertNull(controller.getLivreFromISBN("isbn-invalide-test"));
    }
}
