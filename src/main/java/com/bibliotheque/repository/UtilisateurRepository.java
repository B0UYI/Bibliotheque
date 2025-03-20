package com.bibliotheque.repository;

import java.sql.*;
import java.time.LocalDate;

/**
 * Gère les interactions avec la base de données pour la gestion des utilisateurs et des emprunts.
 * Permet d'ajouter un emprunt, de vérifier l'existence d'un utilisateur, et d'ajouter un nouvel utilisateur si nécessaire.
 */

public class UtilisateurRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    /**
     * Ajoute un emprunt dans la base de données pour un utilisateur donné.
     *
     * @param utilisateurId ID de l'utilisateur qui emprunte le livre.
     * @param isbn ISBN du livre emprunté.
     * @param dateEmprunt Date de l'emprunt.
     * @return true si l'emprunt a été enregistré avec succès, false en cas d'erreur SQL.
     * En cas d'échec, une erreur est affichée dans la console.
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
     * Vérifie si un utilisateur existe déjà en base de données grâce à son e-mail.
     * Si l'utilisateur n'existe pas, il est ajouté et son ID est retourné.
     *
     * @param nom Nom de l'utilisateur.
     * @param prenom Prénom de l'utilisateur.
     * @param email Adresse e-mail unique de l'utilisateur.
     * @param telephone Numéro de téléphone de l'utilisateur.
     * @return L'ID de l'utilisateur existant ou nouvellement ajouté, -1 en cas d'erreur SQL.
     * Une erreur est affichée en console si l'opération échoue.
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
