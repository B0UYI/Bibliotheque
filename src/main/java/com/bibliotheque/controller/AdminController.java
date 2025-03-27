package com.bibliotheque.controller;

import com.bibliotheque.repository.AdminRepository;

/**
 * Contrôleur permettant la gestion de l'authentification des administrateurs.
 * Vérifie les identifiants fournis et renvoie un résultat indiquant si la connexion est réussie.
 */

public class AdminController {
    private final AdminRepository adminRepository;

    /**
     * Constructeur par défaut qui initialise le contrôleur avec une instance réelle du repository.
     * Utilisé pour le fonctionnement normal de l'application en production.
     */
    public AdminController() {
        this.adminRepository = new AdminRepository();
    }

    /**
     * Constructeur permettant l'injection du repository pour les tests.
     * Ce constructeur facilite les tests unitaires en permettant l'utilisation de mocks.
     *
     * @param adminRepository Une implémentation ou un mock de AdminRepository à utiliser.
     */
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

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