package com.bibliotheque.model;

/**
 * Modèle représentant un administrateur.
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

    public String getCodeAdmin() {
        return codeAdmin;
    }

    public void setCodeAdmin(String codeAdmin) {
        this.codeAdmin = codeAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "codeAdmin='" + codeAdmin + '\'' +
                ", password='********'}"; // Masquer le mot de passe pour éviter de l'afficher
    }
}
