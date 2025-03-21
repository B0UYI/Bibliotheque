package com.bibliotheque.view;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AppTest {

    @Disabled("Test ignoré car JavaFX ne peut pas être lancé en test unitaire")
    @Test
    void testAppStart() {
        App app = new App();
        assertNotNull(app);
    }

}
