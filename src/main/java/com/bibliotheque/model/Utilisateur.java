package com.bibliotheque.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modèle représentant un utilisateur de la bibliothèque.
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String livreId;
    private LocalDate dateEmprunt;

    /**
     * Constructeur complet.
     */
    public Utilisateur(String nom, String prenom, String telephone, String email, String livreId, LocalDate dateEmprunt) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
    }

    /**
     * Constructeur vide.
     */
    public Utilisateur() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLivreId() {
        return livreId;
    }

    public void setLivreId(String livreId) {
        this.livreId = livreId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", livreId='" + livreId + '\'' +
                ", dateEmprunt=" + dateEmprunt +
                '}';
    }
}
