package com.bibliotheque.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AdminRepositoryTest {

    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        // Créer une instance en mode test pour éviter les connexions à la base de données
        adminRepository = new AdminRepository(true);
    }

    @Test
    void testAuthentifierAdminFaux() {
        // En mode test, seul "admin"/"password" est valide
        boolean result = adminRepository.authentifierAdmin("fakeadmin", "wrongpassword");
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecValeurNull() {
        boolean result = adminRepository.authentifierAdmin(null, null);
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecChainesVides() {
        boolean result = adminRepository.authentifierAdmin("", "");
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecCaracteresSpeciaux() {
        boolean result = adminRepository.authentifierAdmin("admin'; DROP TABLE admins; --", "password' OR '1'='1");
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecInformationsCorrectes() {
        boolean result = adminRepository.authentifierAdmin("admin", "password");
        assertTrue(result);
    }

    @Test
    void testAuthentifierAdminAvecIdentifiantNull() {
        boolean result = adminRepository.authentifierAdmin(null, "password");
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecMotDePasseNull() {
        boolean result = adminRepository.authentifierAdmin("admin", null);
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecIdentifiantVide() {
        boolean result = adminRepository.authentifierAdmin("", "password");
        assertFalse(result);
    }

    @Test
    void testAuthentifierAdminAvecMotDePasseVide() {
        boolean result = adminRepository.authentifierAdmin("admin", "");
        assertFalse(result);
    }

    @Test
    void testModeReel() throws Exception {
        // Tester avec le mode test désactivé pour couvrir le code de connexion réelle
        // Cette partie utilise des mocks pour simuler la connexion à la base de données

        // Créer une nouvelle instance avec le mode test désactivé
        AdminRepository repoSansTest = new AdminRepository(false);

        // Mock les objets JDBC
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            // Configuration du mock pour DriverManager
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Configuration des autres mocks
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("password")).thenReturn("$2a$10$hashedPassword");

            // Cas 1: Admin trouvé et mot de passe correct
            try (MockedStatic<BCrypt> mockedBCrypt = Mockito.mockStatic(BCrypt.class)) {
                mockedBCrypt.when(() -> BCrypt.checkpw(eq("correctpassword"), anyString()))
                        .thenReturn(true);

                boolean result = repoSansTest.authentifierAdmin("admin", "correctpassword");
                assertTrue(result);
            }

            // Cas 2: Admin trouvé mais mot de passe incorrect
            try (MockedStatic<BCrypt> mockedBCrypt = Mockito.mockStatic(BCrypt.class)) {
                mockedBCrypt.when(() -> BCrypt.checkpw(eq("wrongpassword"), anyString()))
                        .thenReturn(false);

                boolean result = repoSansTest.authentifierAdmin("admin", "wrongpassword");
                assertFalse(result);
            }

            // Cas 3: Admin non trouvé
            when(mockResultSet.next()).thenReturn(false);
            boolean result = repoSansTest.authentifierAdmin("unknownAdmin", "anypassword");
            assertFalse(result);

            // Cas 4: Hash null dans la base de données
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("password")).thenReturn(null);
            result = repoSansTest.authentifierAdmin("admin", "password");
            assertFalse(result);

            // Cas 5: Exception dans BCrypt
            when(mockResultSet.getString("password")).thenReturn("$2a$10$hashedPassword");
            try (MockedStatic<BCrypt> mockedBCrypt = Mockito.mockStatic(BCrypt.class)) {
                mockedBCrypt.when(() -> BCrypt.checkpw(anyString(), anyString()))
                        .thenThrow(new IllegalArgumentException("Hash invalide"));

                result = repoSansTest.authentifierAdmin("admin", "password");
                assertFalse(result);
            }
        }

        // Cas 6: Exception SQL dans la préparation (test séparé pour éviter les problèmes de mock)
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Erreur SQL"));
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            boolean result = repoSansTest.authentifierAdmin("admin", "password");
            assertFalse(result);
        }
    }

    @Test
    void testSetTestMode() {
        AdminRepository repo = new AdminRepository();
        assertFalse(repo.isTestMode());

        repo.setTestMode(true);
        assertTrue(repo.isTestMode());

        repo.setTestMode(false);
        assertFalse(repo.isTestMode());
    }
}