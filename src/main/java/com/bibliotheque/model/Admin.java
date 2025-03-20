package com.bibliotheque.model;

/**
 * Modèle représentant un administrateur de la bibliothèque.
 * Contient les informations nécessaires pour l'authentification.
 */

public class Admin {
    private String codeAdmin;
    private String password;

    /**
     * Constructeur avec paramètres.
     *
     * @param codeAdmin Code unique de l'administrateur.
     * @param password  Mot de passe de l'administrateur (hashé de préférence).
     */
    public Admin(String codeAdmin, String password) {
        this.codeAdmin = codeAdmin;
        this.password = password;
    }

    /**
     * Constructeur vide (nécessaire pour certaines utilisations comme Hibernate).
     */

    public Admin() {}

    /**
     * Retourne le code administrateur unique.
     *
     * @return Le code administrateur.
     */

    public String getCodeAdmin() {
        return codeAdmin;
    }

    /**
     * Définit le code administrateur.
     *
     * @param codeAdmin Le nouveau code administrateur.
     */

    public void setCodeAdmin(String codeAdmin) {
        this.codeAdmin = codeAdmin;
    }

    /**
     * Retourne le mot de passe de l'administrateur.
     * Il est recommandé d'utiliser un hash sécurisé.
     *
     * @return Le mot de passe de l'administrateur.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'administrateur.
     * Il est recommandé de stocker un mot de passe hashé pour plus de sécurité.
     *
     * @param password Le nouveau mot de passe de l'administrateur.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne une représentation textuelle de l'administrateur.
     * Le mot de passe est masqué pour éviter toute fuite de données sensibles.
     *
     * @return Une chaîne représentant l'administrateur sans afficher son mot de passe.
     */

    @Override
    public String toString() {
        return "Admin{" +
                "codeAdmin='" + codeAdmin + '\'' +
                ", password='********'}"; // Masquer le mot de passe pour éviter de l'afficher
    }
}
