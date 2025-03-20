package com.bibliotheque.model;

import java.util.Objects;

/**
 * Modèle représentant un livre dans la bibliothèque.
 * Contient des informations sur l'ISBN, le titre, l'auteur, l'année de publication,
 * ainsi que son état et son statut dans le système.
 */

public class Livre {
    private String ISBN;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String etat;
    private String statut;

    /**
     * Constructeur permettant d'initialiser un livre avec toutes ses informations.
     *
     * @param ISBN Identifiant unique du livre.
     * @param titre Titre du livre.
     * @param auteur Nom de l'auteur du livre.
     * @param anneePublication Année de publication du livre.
     * @param etat État physique du livre (ex: neuf, usé, abîmé).
     * @param statut Statut de disponibilité du livre (ex: disponible, emprunté).
     */

    public Livre(String ISBN, String titre, String auteur, int anneePublication, String etat, String statut) {
        this.ISBN = ISBN;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.etat = etat;
        this.statut = statut;
    }

    /**
     * Constructeur vide utilisé pour certaines opérations comme la sérialisation ou l'ORM.
     */

    public Livre() {}

    /**
     * Retourne l'ISBN du livre.
     *
     * @return L'ISBN du livre.
     */

    public String getISBN() {
        return ISBN;
    }

    /**
     * Définit l'ISBN du livre.
     *
     * @param ISBN Le nouvel ISBN du livre.
     */

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Retourne le titre du livre.
     *
     * @return Le titre du livre.
     */

    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du livre.
     *
     * @param titre Le nouveau titre du livre.
     */

    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne le nom de l'auteur du livre.
     *
     * @return Le nom de l'auteur.
     */

    public String getAuteur() {
        return auteur;
    }

    /**
     * Définit le nom de l'auteur du livre.
     *
     * @param auteur Le nouveau nom de l'auteur.
     */

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Retourne l'année de publication du livre.
     *
     * @return L'année de publication.
     */

    public int getAnneePublication() {
        return anneePublication;
    }

    /**
     * Définit l'année de publication du livre.
     *
     * @param anneePublication La nouvelle année de publication.
     */

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    /**
     * Retourne l'état physique du livre (ex: neuf, usé).
     *
     * @return L'état du livre.
     */

    public String getEtat() {
        return etat;
    }

    /**
     * Définit l'état physique du livre.
     *
     * @param etat Le nouvel état du livre.
     */

    public void setEtat(String etat) {
        this.etat = etat;
    }

    /**
     * Retourne le statut de disponibilité du livre (ex: disponible, emprunté).
     *
     * @return Le statut du livre.
     */

    public String getStatut() {
        return statut;
    }

    /**
     * Définit le statut de disponibilité du livre.
     *
     * @param statut Le nouveau statut du livre.
     */

    public void setStatut(String statut) {
        this.statut = statut;
    }

    /**
     * Vérifie si deux objets Livre sont identiques en comparant leur ISBN.
     *
     * @param o L'objet à comparer.
     * @return true si les ISBN sont identiques, sinon false.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livre livre = (Livre) o;
        return Objects.equals(ISBN, livre.ISBN);
    }

    /**
     * Génère un code de hachage basé sur l'ISBN du livre.
     *
     * @return Le code de hachage de l'ISBN.
     */

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    /**
     * Retourne une représentation textuelle du livre contenant ses principales informations.
     *
     * @return Une chaîne contenant l'ISBN, le titre, l'auteur, l'année de publication, l'état et le statut du livre.
     */

    @Override
    public String toString() {
        return "Livre{" +
                "ISBN='" + ISBN + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", anneePublication=" + anneePublication +
                ", etat='" + etat + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
