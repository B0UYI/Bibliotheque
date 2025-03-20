package com.bibliotheque.repository;

import java.sql.*;
import java.time.LocalDate;

/**
 * Gère les opérations liées aux utilisateurs et aux emprunts.
 */
public class UtilisateurRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    /**
     * Ajoute un emprunt en base de données.
     *
     * @param utilisateurId ID de l'utilisateur qui emprunte.
     * @param isbn ISBN du livre emprunté.
     * @param dateEmprunt Date de l'emprunt.
     * @return true si l'emprunt a été ajouté, sinon false.
     */
    public boolean ajouterEmprunt(int utilisateurId, String isbn, LocalDate dateEmprunt) {
        String query = "INSERT INTO emprunts (utilisateur_id, ISBN, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, utilisateurId);
            pstmt.setString(2, isbn);
            pstmt.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            pstmt.setDate(4, java.sql.Date.valueOf(dateEmprunt.plusMonths(6))); // Date de retour à 6 mois

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'emprunt : " + e.getMessage());
            return false;
        }
    }

    /**
     * Ajoute un utilisateur s'il n'existe pas en base de données.
     *
     * @param nom Nom de l'utilisateur.
     * @param prenom Prénom de l'utilisateur.
     * @param email Adresse e-mail de l'utilisateur.
     * @param telephone Numéro de téléphone de l'utilisateur.
     * @return L'ID de l'utilisateur existant ou nouvellement ajouté, -1 en cas d'erreur.
     */
    public int ajouterUtilisateurSiInexistant(String nom, String prenom, String email, String telephone) {
        int utilisateurId = -1;
        String checkUserQuery = "SELECT id FROM utilisateurs WHERE email = ?";
        String insertUserQuery = "INSERT INTO utilisateurs (nom, prenom, email, telephone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {

            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                utilisateurId = rs.getInt("id"); // L'utilisateur existe déjà
            } else {
                insertStmt.setString(1, nom);
                insertStmt.setString(2, prenom);
                insertStmt.setString(3, email);
                insertStmt.setString(4, telephone);
                insertStmt.executeUpdate();

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    utilisateurId = generatedKeys.getInt(1); // Récupération de l'ID généré
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout ou de la vérification de l'utilisateur : " + e.getMessage());
        }
        return utilisateurId;
    }
}
