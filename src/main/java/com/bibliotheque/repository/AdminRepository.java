package com.bibliotheque.repository;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe permettant l'authentification des administrateurs dans la base de données.
 */
public class AdminRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    // Pour les tests unitaires
    private boolean testMode = false;
    private static final String TEST_ADMIN = "admin";
    private static final String TEST_PASSWORD = "password";

    /**
     * Constructeur par défaut
     */
    public AdminRepository() {
        // Constructeur par défaut
    }

    /**
     * Constructeur avec mode test
     *
     * @param testMode Activation du mode test (true) ou non (false)
     */
    public AdminRepository(boolean testMode) {
        this.testMode = testMode;
    }

    /**
     * Authentifie un administrateur en vérifiant si le mot de passe fourni correspond
     * au mot de passe haché stocké dans la base de données.
     *
     * @param codeAdmin Le code administrateur saisi.
     * @param password  Le mot de passe en clair saisi.
     * @return true si les identifiants sont valides, sinon false.
     */
    public boolean authentifierAdmin(String codeAdmin, String password) {
        // Vérification des paramètres null
        if (codeAdmin == null || password == null) {
            System.out.println(">>> Identifiant ou mot de passe null");
            return false;
        }

        // Vérification des chaînes vides
        if (codeAdmin.isEmpty() || password.isEmpty()) {
            System.out.println(">>> Identifiant ou mot de passe vide");
            return false;
        }

        // Mode test pour les tests unitaires
        if (testMode) {
            return TEST_ADMIN.equals(codeAdmin) && TEST_PASSWORD.equals(password);
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT password FROM admins WHERE code_admin = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codeAdmin);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                // Vérification pour éviter NullPointerException
                if (hashedPassword == null) {
                    System.out.println(">>> Hash récupéré est null");
                    return false;
                }

                // Affichage de debug
                System.out.println(">>> code_admin entré : [" + codeAdmin + "]");
                System.out.println(">>> Mot de passe en clair : [" + password + "]");
                System.out.println(">>> Hash récupéré : [" + hashedPassword + "]");

                try {
                    boolean match = BCrypt.checkpw(password, hashedPassword);
                    System.out.println(">>> Résultat Bcrypt : " + match);
                    return match;
                } catch (Exception e) {
                    System.err.println("Erreur lors de la vérification du mot de passe : " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println(">>> Aucun administrateur trouvé avec ce code.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erreur connexion MySQL : " + e.getMessage());
            return false;
        } finally {
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            closeQuietly(rs);
            closeQuietly(pstmt);
            closeQuietly(conn);
        }
    }

    /**
     * Ferme silencieusement une ressource JDBC (Connection, Statement, ResultSet)
     *
     * @param closeable La ressource à fermer
     */
    private void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Active ou désactive le mode test
     *
     * @param testMode true pour activer le mode test, false pour le désactiver
     */
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    /**
     * Vérifie si le mode test est activé
     *
     * @return true si le mode test est activé, false sinon
     */
    public boolean isTestMode() {
        return testMode;
    }
}