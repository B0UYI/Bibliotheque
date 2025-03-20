package com.bibliotheque.controller;

import com.bibliotheque.repository.AdminRepository;

/**
 * Gère l'authentification des administrateurs.
 */
public class AdminController {
    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * Vérifie les identifiants d'un administrateur.
     *
     * @param codeAdmin Le code administrateur.
     * @param password  Le mot de passe associé.
     * @return true si l'authentification réussit, sinon false.
     */
    public boolean login(String codeAdmin, String password) {
        try {
            return adminRepository.authentifierAdmin(codeAdmin, password);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
            return false;
        }
    }
}
