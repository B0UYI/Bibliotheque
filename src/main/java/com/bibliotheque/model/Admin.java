package com.bibliotheque.model;

import org.mindrot.jbcrypt.BCrypt;

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
     * @param password  Mot de passe haché (récupéré tel quel depuis la base).
     */
    public Admin(String codeAdmin, String password) {
        this.codeAdmin = codeAdmin;
        this.password = password; // Pas de hash ici, on récupère depuis la base
    }

    /**
     * Constructeur vide requis pour certaines opérations comme JDBC ou JavaFX.
     */
    public Admin() {}

    /**
     * Retourne le code administrateur.
     *
     * @return Le code admin.
     */
    public String getCodeAdmin() {
        return codeAdmin;
    }

    /**
     * Définit le code administrateur.
     *
     * @param codeAdmin Le code à définir.
     */
    public void setCodeAdmin(String codeAdmin) {
        this.codeAdmin = codeAdmin;
    }

    /**
     * Retourne le mot de passe (déjà haché).
     *
     * @return Le mot de passe haché.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe tel quel (ne pas hacher ici).
     *
     * @param password Mot de passe haché à affecter.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Vérifie si un mot de passe en clair correspond au hash stocké.
     *
     * @param plainPassword Mot de passe en clair saisi par l'utilisateur.
     * @return true si la correspondance est vérifiée, sinon false.
     */
    public boolean checkPassword(String plainPassword) {
        System.out.println(">>> Mot de passe entré : [" + plainPassword + "]");
        System.out.println(">>> Mot de passe hashé (stocké) : [" + password + "]");
        return BCrypt.checkpw(plainPassword, this.password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "codeAdmin='" + codeAdmin + '\'' +
                ", password='********'}";
    }
}
