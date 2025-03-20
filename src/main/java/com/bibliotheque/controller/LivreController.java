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
 * Contrôleur gérant les opérations sur les livres de la bibliothèque.
 * Permet d'ajouter, modifier, supprimer des livres et de récupérer leurs informations,
 * y compris via l'API OpenLibrary.
 */

public class LivreController {
    private final LivreRepository livreRepository = new LivreRepository();

    /**
     * Récupère tous les livres stockés dans la base de données.
     *
     * @return Une liste contenant tous les livres enregistrés.
     */

    public List<Livre> getAllLivres() {
        return livreRepository.listerLivres();
    }

    /**
     * Ajoute un livre dans la base de données et s'assure que son statut est en minuscule.
     *
     * @param livre Le livre à ajouter.
     */

    public void ajouterLivre(Livre livre) {
        livre.setStatut(livre.getStatut().toLowerCase()); // Force en minuscule
        livreRepository.ajouterLivre(livre);
    }

    /**
     * Modifie les informations d'un livre existant dans la base de données.
     * Le statut du livre est systématiquement converti en minuscule avant l'enregistrement.
     *
     * @param livre Le livre à modifier.
     */

    public void modifierLivre(Livre livre) {
        livre.setStatut(livre.getStatut().toLowerCase()); // Force en minuscule
        livreRepository.modifierLivre(livre);
    }

    /**
     * Récupère les informations d'un livre depuis l'API OpenLibrary à partir de son ISBN.
     * Si aucune donnée n'est trouvée, des valeurs par défaut sont utilisées.
     *
     * @param isbn L'ISBN du livre à rechercher.
     * @return Un objet Livre avec les informations récupérées ou des valeurs par défaut si indisponibles.
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
     * Supprime un livre de la base de données en fonction de son ISBN.
     *
     * @param isbn L'ISBN du livre à supprimer.
     */

    public void supprimerLivre(String isbn) {
        livreRepository.supprimerLivre(isbn);
    }
}
