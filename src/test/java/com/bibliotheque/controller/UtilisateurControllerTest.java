package com.bibliotheque.controller;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurControllerTest {

    @Test
    void testEmpruntUtilisateurManquant() {
        UtilisateurController controller = new UtilisateurController();
        boolean result = controller.emprunterLivre("", "", "", "", "", LocalDate.now());
        assertFalse(result);
    }
}
