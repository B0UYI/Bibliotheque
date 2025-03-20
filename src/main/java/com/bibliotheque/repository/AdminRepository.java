package com.bibliotheque.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe permettant la gestion des administrateurs (connexion à la BDD).
 */
public class AdminRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    /**
     * Vérifie si un administrateur existe en base de données.
     * @param codeAdmin Le code administrateur.
     * @param password Le mot de passe administrateur.
     * @return true si les identifiants sont corrects, sinon false.
     */
    public boolean authentifierAdmin(String codeAdmin, String password) {
        String sql = "SELECT * FROM admins WHERE code_admin = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codeAdmin);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Retourne true si l'admin existe
            }
        } catch (SQLException e) {
            System.err.println("Erreur connexion MySQL : " + e.getMessage());
            return false;
        }
    }
}
