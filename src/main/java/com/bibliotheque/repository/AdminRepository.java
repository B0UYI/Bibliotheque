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

    /**
     * Authentifie un administrateur en vérifiant si le mot de passe fourni correspond
     * au mot de passe haché stocké dans la base de données.
     *
     * @param codeAdmin Le code administrateur saisi.
     * @param password  Le mot de passe en clair saisi.
     * @return true si les identifiants sont valides, sinon false.
     */
    public boolean authentifierAdmin(String codeAdmin, String password) {
        String sql = "SELECT password FROM admins WHERE code_admin = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codeAdmin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                // Affichage de debug
                System.out.println(">>> code_admin entré : [" + codeAdmin + "]");
                System.out.println(">>> Mot de passe en clair : [" + password + "]");
                System.out.println(">>> Hash récupéré : [" + hashedPassword + "]");

                boolean match = BCrypt.checkpw(password, hashedPassword);
                System.out.println(">>> Résultat Bcrypt : " + match);

                return match;
            } else {
                System.out.println(">>> Aucun administrateur trouvé avec ce code.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur connexion MySQL : " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
