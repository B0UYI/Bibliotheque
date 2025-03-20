package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les opérations sur la base de données des livres.
 */
public class LivreRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "Aqwzsxedc12345-";

    /**
     * Récupère tous les livres disponibles dans la base de données.
     *
     * @return Liste des livres.
     */
    public List<Livre> listerLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getString("ISBN"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("annee_publication"),
                        rs.getString("etat"),
                        rs.getString("statut")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des livres : " + e.getMessage());
        }

        return livres;
    }

    /**
     * Ajoute un livre à la base de données.
     *
     * @param livre Livre à ajouter.
     */
    public void ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livres (ISBN, titre, auteur, annee_publication, etat, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livre.getISBN());
            pstmt.setString(2, livre.getTitre());
            pstmt.setString(3, livre.getAuteur());
            pstmt.setInt(4, livre.getAnneePublication());
            pstmt.setString(5, livre.getEtat());
            pstmt.setString(6, livre.getStatut());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    /**
     * Modifie les informations d'un livre.
     *
     * @param livre Livre à modifier.
     */
    public void modifierLivre(Livre livre) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, annee_publication = ?, etat = ?, statut = ? WHERE ISBN = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setInt(3, livre.getAnneePublication());
            pstmt.setString(4, livre.getEtat());
            pstmt.setString(5, livre.getStatut());
            pstmt.setString(6, livre.getISBN());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du livre : " + e.getMessage());
        }
    }

    /**
     * Supprime un livre de la base de données.
     *
     * @param isbn ISBN du livre à supprimer.
     */
    public void supprimerLivre(String isbn) {
        String sql = "DELETE FROM livres WHERE ISBN = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    /**
     * Met à jour le statut d'un livre (ex: disponible -> emprunté).
     *
     * @param isbn ISBN du livre.
     * @param nouveauStatut Nouveau statut du livre.
     */
    public void mettreAJourStatutLivre(String isbn, String nouveauStatut) {
        String sql = "UPDATE livres SET statut = ? WHERE ISBN = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nouveauStatut);
            pstmt.setString(2, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut du livre : " + e.getMessage());
        }
    }
}
