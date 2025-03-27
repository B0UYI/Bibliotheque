package com.bibliotheque.controller;

import com.bibliotheque.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdminControllerTest {
    private AdminController controller;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new AdminController(adminRepository);
    }

    @Test
    void testConstructeurParDefaut() {
        AdminController defaultController = new AdminController();
        assertNotNull(defaultController, "Le constructeur par défaut devrait créer une instance valide");
    }

    @Test
    void testLoginAvecCredentialsValides() {
        when(adminRepository.authentifierAdmin("admin", "password")).thenReturn(true);

        boolean result = controller.login("admin", "password");
        assertTrue(result, "La connexion avec des identifiants valides devrait réussir");
        verify(adminRepository, times(1)).authentifierAdmin("admin", "password");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "invalide"})
    void testLoginAvecCodeAdminInvalide(String codeAdmin) {
        when(adminRepository.authentifierAdmin(anyString(), anyString())).thenReturn(false);

        boolean result = controller.login(codeAdmin, "password");
        assertFalse(result, "La connexion avec un code admin invalide devrait échouer");
        verify(adminRepository, times(1)).authentifierAdmin(codeAdmin, "password");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "motdepasse"})
    void testLoginAvecMotDePasseInvalide(String motDePasse) {
        when(adminRepository.authentifierAdmin(anyString(), anyString())).thenReturn(false);

        boolean result = controller.login("admin", motDePasse);
        assertFalse(result, "La connexion avec un mot de passe invalide devrait échouer");
        verify(adminRepository, times(1)).authentifierAdmin("admin", motDePasse);
    }

    @Test
    void testLoginAvecNull() {
        when(adminRepository.authentifierAdmin(anyString(), anyString())).thenReturn(false);

        assertAll(
                () -> assertFalse(controller.login(null, "password"),
                        "La connexion avec code admin null devrait échouer"),
                () -> assertFalse(controller.login("admin", null),
                        "La connexion avec mot de passe null devrait échouer"),
                () -> assertFalse(controller.login(null, null),
                        "La connexion avec code admin et mot de passe null devrait échouer")
        );
        verify(adminRepository, times(3)).authentifierAdmin(anyString(), anyString());
    }

    @Test
    void testLoginCasseSensible() {
        when(adminRepository.authentifierAdmin(anyString(), anyString())).thenReturn(false);

        assertAll(
                () -> assertFalse(controller.login("ADMIN", "password"),
                        "La connexion devrait être sensible à la casse pour le code admin"),
                () -> assertFalse(controller.login("admin", "PASSWORD"),
                        "La connexion devrait être sensible à la casse pour le mot de passe")
        );
        verify(adminRepository, times(2)).authentifierAdmin(anyString(), anyString());
    }

    @Test
    void testLoginAvecEspaces() {
        when(adminRepository.authentifierAdmin(anyString(), anyString())).thenReturn(false);

        assertAll(
                () -> assertFalse(controller.login(" admin ", "password"),
                        "La connexion ne devrait pas accepter les espaces avant/après le code admin"),
                () -> assertFalse(controller.login("admin", " password "),
                        "La connexion ne devrait pas accepter les espaces avant/après le mot de passe")
        );
        verify(adminRepository, times(2)).authentifierAdmin(anyString(), anyString());
    }

    @Test
    void testLoginAvecExceptionRepository() {
        when(adminRepository.authentifierAdmin(anyString(), anyString()))
                .thenThrow(new RuntimeException("Erreur d'accès à la base de données"));

        boolean result = controller.login("admin", "password");
        assertFalse(result, "La connexion devrait échouer en cas d'exception du repository");
        verify(adminRepository, times(1)).authentifierAdmin("admin", "password");
    }
}