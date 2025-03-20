package com.bibliotheque.controller;

import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import java.time.LocalDate;

/**
 * Contrôleur pour gérer les utilisateurs et les emprunts de livres.
 */
public class UtilisateurController {
    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final LivreRepository livreRepository = new LivreRepository();

    /**
     * Permet à un utilisateur d'emprunter un livre.
     *
     * @param nom Nom de l'utilisateur.
     * @param prenom Prénom de l'utilisateur.
     * @param email Adresse e-mail de l'utilisateur.
     * @param telephone Numéro de téléphone de l'utilisateur.
     * @param isbn ISBN du livre à emprunter.
     * @param dateEmprunt Date de l'emprunt.
     * @return true si l'emprunt réussit, sinon false.
     */
    public boolean emprunterLivre(String nom, String prenom, String email, String telephone, String isbn, LocalDate dateEmprunt) {
        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || isbn.isEmpty()) {
            return false; // Empêche l'ajout d'un emprunt avec des données manquantes
        }

        // Vérifier si l'utilisateur existe ou l'ajouter
        int utilisateurId = utilisateurRepository.ajouterUtilisateurSiInexistant(nom, prenom, email, telephone);
        if (utilisateurId == -1) {
            return false; // Échec de la récupération ou de l'ajout de l'utilisateur
        }

        // Ajouter l'emprunt
        boolean empruntAjoute = utilisateurRepository.ajouterEmprunt(utilisateurId, isbn, dateEmprunt);

        // Mettre à jour le statut du livre si l'emprunt est réussi
        if (empruntAjoute) {
            livreRepository.mettreAJourStatutLivre(isbn, "indisponible");
        }

        return empruntAjoute;
    }
}
