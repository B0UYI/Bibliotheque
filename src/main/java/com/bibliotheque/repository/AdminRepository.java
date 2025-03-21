package com.bibliotheque.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Classe permettant la gestion des administrateurs dans la base de données.
 * Elle fournit des méthodes pour vérifier l'authentification des administrateurs.
 */

public class AdminRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    /**
     * Vérifie si un administrateur existe en base de données en comparant ses identifiants.
     *
     * @param codeAdmin Le code administrateur unique.
     * @param password Le mot de passe associé à l'administrateur.
     * @return true si les identifiants sont corrects et existent en base, sinon false.
     * @throws SQLException En cas d'erreur lors de la connexion à la base de données.
     */

    public boolean ajouterAdmin(String codeAdmin, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hachage du mot de passe

        String sql = "INSERT INTO admins (code_admin, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codeAdmin);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'admin : " + e.getMessage());
            return false;
        }
    }
    /**
     * Vérifie si l'authentification d'un administrateur est correcte en comparant les mots de passe hachés.
     *
     * @param codeAdmin Le code administrateur.
     * @param password  Le mot de passe fourni par l'utilisateur.
     * @return true si les identifiants sont corrects, sinon false.
     */
    public boolean authentifierAdmin(String codeAdmin, String password) {
        String sql = "SELECT password FROM admins WHERE code_admin = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codeAdmin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword); // Comparaison sécurisée
            }
        } catch (SQLException e) {
            System.err.println("Erreur connexion MySQL : " + e.getMessage());
        }
        return false;
    }
}
