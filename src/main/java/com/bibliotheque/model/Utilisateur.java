package com.bibliotheque.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modèle représentant un utilisateur de la bibliothèque.
 * Contient les informations personnelles de l'utilisateur ainsi que les détails
 * de son emprunt de livre.
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
     * Constructeur permettant d'initialiser un utilisateur avec ses informations personnelles
     * et les détails de son emprunt éventuel.
     *
     * @param nom Nom de l'utilisateur.
     * @param prenom Prénom de l'utilisateur.
     * @param telephone Numéro de téléphone de l'utilisateur.
     * @param email Adresse e-mail de l'utilisateur.
     * @param livreId Identifiant du livre emprunté (peut être null si aucun livre n'est emprunté).
     * @param dateEmprunt Date à laquelle l'utilisateur a emprunté le livre.
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
     * Constructeur vide utilisé pour la sérialisation ou pour créer un utilisateur avant de renseigner ses informations.
     */

    public Utilisateur() {}

    /**
     * Retourne l'identifiant unique de l'utilisateur.
     *
     * @return L'ID de l'utilisateur.
     */

    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     *
     * @param id Le nouvel identifiant de l'utilisateur.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */

    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param nom Le nouveau nom de l'utilisateur.
     */

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     *
     * @return Le prénom de l'utilisateur.
     */

    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom Le nouveau prénom de l'utilisateur.
     */

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne le numéro de téléphone de l'utilisateur.
     *
     * @return Le numéro de téléphone.
     */

    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone de l'utilisateur.
     *
     * @param telephone Le nouveau numéro de téléphone.
     */

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Retourne l'adresse e-mail de l'utilisateur.
     *
     * @return L'adresse e-mail.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     *
     * @param email La nouvelle adresse e-mail.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne l'identifiant du livre emprunté par l'utilisateur.
     *
     * @return L'identifiant du livre, ou null si aucun livre n'est emprunté.
     */

    public String getLivreId() {
        return livreId;
    }

    /**
     * Définit l'identifiant du livre emprunté par l'utilisateur.
     *
     * @param livreId Le nouvel identifiant du livre emprunté.
     */

    public void setLivreId(String livreId) {
        this.livreId = livreId;
    }

    /**
     * Retourne la date à laquelle l'utilisateur a emprunté un livre.
     *
     * @return La date d'emprunt, ou null si aucun livre n'est emprunté.
     */

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    /**
     * Définit la date d'emprunt du livre par l'utilisateur.
     *
     * @param dateEmprunt La nouvelle date d'emprunt.
     */

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    /**
     * Vérifie si deux objets Utilisateur sont identiques en comparant leur ID.
     *
     * @param o L'objet à comparer.
     * @return true si les ID sont identiques, sinon false.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id;
    }

    /**
     * Génère un code de hachage basé sur l'ID de l'utilisateur.
     *
     * @return Le code de hachage de l'ID.
     */

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Retourne une représentation textuelle de l'utilisateur contenant ses principales informations.
     *
     * @return Une chaîne contenant l'ID, le nom, le prénom, le téléphone, l'e-mail,
     *         l'identifiant du livre emprunté et la date d'emprunt.
     */

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
