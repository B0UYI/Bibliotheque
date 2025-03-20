package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.LivreRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * Gère les opérations sur les livres.
 */
public class LivreController {
    private final LivreRepository livreRepository = new LivreRepository();

    /**
     * Récupère tous les livres depuis la base de données.
     *
     * @return Liste des livres.
     */
    public List<Livre> getAllLivres() {
        return livreRepository.listerLivres();
    }

    /**
     * Ajoute un livre à la base de données.
     *
     * @param livre Livre à ajouter.
     */
    public void ajouterLivre(Livre livre) {
        livre.setStatut(livre.getStatut().toLowerCase()); // Force en minuscule
        livreRepository.ajouterLivre(livre);
    }

    /**
     * Modifie les informations d'un livre.
     *
     * @param livre Livre à modifier.
     */
    public void modifierLivre(Livre livre) {
        livre.setStatut(livre.getStatut().toLowerCase()); // Force en minuscule
        livreRepository.modifierLivre(livre);
    }

    /**
     * Récupère les informations d'un livre depuis OpenLibrary en utilisant son ISBN.
     *
     * @param isbn ISBN du livre.
     * @return Livre contenant les informations récupérées.
     */
    public Livre getLivreFromISBN(String isbn) {
        String apiUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            Scanner scanner = new Scanner(conn.getInputStream());
            String jsonResponse = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject bookData = jsonObject.optJSONObject("ISBN:" + isbn);

            if (bookData != null) {
                String titre = bookData.optString("title", "Titre inconnu");
                String auteur = bookData.has("authors") ? bookData.getJSONArray("authors").getJSONObject(0).getString("name") : "Auteur inconnu";
                int anneePublication = bookData.has("publish_date") ? Integer.parseInt(bookData.getString("publish_date").replaceAll("[^0-9]", "")) : 0;

                return new Livre(isbn, titre, auteur, anneePublication, "neuf", "disponible");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération des informations du livre : " + e.getMessage());
        }
        return null;
    }

    /**
     * Supprime un livre de la base de données.
     *
     * @param isbn ISBN du livre à supprimer.
     */
    public void supprimerLivre(String isbn) {
        livreRepository.supprimerLivre(isbn);
    }
}
