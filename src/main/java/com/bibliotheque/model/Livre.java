package com.bibliotheque.model;

import java.util.Objects;

/**
 * Modèle représentant un livre dans la bibliothèque.
 */
public class Livre {
    private String ISBN;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String etat;
    private String statut;

    /**
     * Constructeur complet.
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
     * Constructeur vide.
     */
    public Livre() {}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livre livre = (Livre) o;
        return Objects.equals(ISBN, livre.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

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
