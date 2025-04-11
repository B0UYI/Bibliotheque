package com.bibliotheque.view;

import com.bibliotheque.controller.AdminController;
import com.bibliotheque.controller.LivreController;
import com.bibliotheque.controller.UtilisateurController;
import com.bibliotheque.model.Livre;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe principale représentant l'interface utilisateur de l'application de gestion de bibliothèque.
 * Cette classe gère l'affichage des livres, l'authentification des administrateurs,
 * l'emprunt et la gestion des livres via une interface JavaFX.
 */
public class App extends Application {
    private final AdminController adminController = new AdminController();
    private final LivreController livreController = new LivreController();
    private final UtilisateurController utilisateurController = new UtilisateurController();
    private final TableView<Livre> tableView = new TableView<>();
    private final Button btnAddBook = new Button("Ajouter un livre");
    private final Button btnEditBook = new Button("Modifier le livre");
    private final Button btnDeleteBook = new Button("Supprimer le livre");
    private final TextField searchField = new TextField();

    private ObservableList<Livre> listeLivres;
    private FilteredList<Livre> filteredLivres;

    @Override
    public void start(Stage stage) {
        btnAddBook.setDisable(true);
        btnEditBook.setDisable(true);
        btnDeleteBook.setDisable(true);

        btnAddBook.setOnAction(e -> ajouterLivre());
        btnEditBook.setOnAction(e -> modifierLivre());
        btnDeleteBook.setOnAction(e -> supprimerLivre());

        Button btnLogin = new Button("Connexion Admin");
        btnLogin.setOnAction(e -> showLoginDialog());

        Button btnBorrowBook = new Button("Emprunter un livre");
        btnBorrowBook.setOnAction(e -> emprunterLivre());

        setupTableView();
        setupSearchFilter();
        loadBooks();

        searchField.setPromptText("Rechercher...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredLivres.setPredicate(livre -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return livre.getISBN().toLowerCase().contains(lowerCaseFilter)
                        || livre.getTitre().toLowerCase().contains(lowerCaseFilter)
                        || livre.getAuteur().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(livre.getAnneePublication()).contains(lowerCaseFilter)
                        || livre.getEtat().toLowerCase().contains(lowerCaseFilter)
                        || livre.getStatut().toLowerCase().contains(lowerCaseFilter);
            });
        });

        VBox root = new VBox(10, btnLogin, searchField, btnBorrowBook, tableView, btnAddBook, btnEditBook, btnDeleteBook);
        root.setPadding(new Insets(15));
        root.getStyleClass().add("root"); // 🔥 Ajout de la classe CSS root

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // 🔥 Ajout du CSS

        stage.setScene(scene);
        stage.setTitle("Gestion de Bibliothèque");
        stage.show();
    }

    private void loadBooks() {
        List<Livre> livres = livreController.getAllLivres();
        listeLivres = FXCollections.observableArrayList(livres);
        filteredLivres = new FilteredList<>(listeLivres, p -> true);
        tableView.setItems(filteredLivres);
    }

    private void setupTableView() {
        TableColumn<Livre, String> colISBN = new TableColumn<>("ISBN");
        colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Livre, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<Livre, String> colAuteur = new TableColumn<>("Auteur");
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));

        TableColumn<Livre, Integer> colAnnee = new TableColumn<>("Année");
        colAnnee.setCellValueFactory(new PropertyValueFactory<>("anneePublication"));

        TableColumn<Livre, String> colEtat = new TableColumn<>("État");
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        TableColumn<Livre, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        tableView.getColumns().setAll(colISBN, colTitre, colAuteur, colAnnee, colEtat, colStatut);
    }

    private void setupSearchFilter() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredLivres.setPredicate(livre -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return livre.getISBN().toLowerCase().contains(lowerCaseFilter)
                        || livre.getTitre().toLowerCase().contains(lowerCaseFilter)
                        || livre.getAuteur().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(livre.getAnneePublication()).contains(lowerCaseFilter)
                        || livre.getEtat().toLowerCase().contains(lowerCaseFilter)
                        || livre.getStatut().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    private void emprunterLivre() {
        Livre livreSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (livreSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un livre à emprunter.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Emprunter un Livre");
        dialog.setHeaderText("Veuillez renseigner vos informations pour emprunter ce livre.");

        TextField prenomField = new TextField();
        TextField nomField = new TextField();
        TextField emailField = new TextField();
        TextField telephoneField = new TextField();
        DatePicker dateEmpruntPicker = new DatePicker(LocalDate.now());

        VBox content = new VBox(10,
                new Label("Prénom :"), prenomField,
                new Label("Nom :"), nomField,
                new Label("E-mail :"), emailField,
                new Label("Numéro de téléphone :"), telephoneField,
                new Label("Date d'emprunt :"), dateEmpruntPicker
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String prenom = prenomField.getText();
                String nom = nomField.getText();
                String email = emailField.getText();
                String telephone = telephoneField.getText();
                LocalDate dateEmprunt = dateEmpruntPicker.getValue();

                if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
                    return;
                }

                boolean success = utilisateurController.emprunterLivre(nom, prenom, email, telephone, livreSelectionne.getISBN(), dateEmprunt);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Emprunt enregistré avec succès !\n\nVous avez 6 mois pour le rendre.");
                    loadBooks();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'emprunt.");
                }
            }
        });
    }

    private void ajouterLivre() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un livre");
        dialog.setHeaderText("Entrez l'ISBN du livre à ajouter");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(isbn -> {
            Livre livre = livreController.getLivreFromISBN(isbn);
            if (livre != null) {
                livreController.ajouterLivre(livre);
                loadBooks();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre ajouté avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les informations du livre.");
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void modifierLivre() {
        Livre livreSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (livreSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un livre à modifier.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier un Livre");
        dialog.setHeaderText("Modifiez les informations du livre");

        TextField titreField = new TextField(livreSelectionne.getTitre());
        TextField auteurField = new TextField(livreSelectionne.getAuteur());
        TextField anneeField = new TextField(String.valueOf(livreSelectionne.getAnneePublication()));
        TextField etatField = new TextField(livreSelectionne.getEtat());
        TextField statutField = new TextField(livreSelectionne.getStatut());

        VBox content = new VBox(10,
                new Label("Titre :"), titreField,
                new Label("Auteur :"), auteurField,
                new Label("Année de publication :"), anneeField,
                new Label("État du livre :"), etatField,
                new Label("Statut :"), statutField
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                livreSelectionne.setTitre(titreField.getText());
                livreSelectionne.setAuteur(auteurField.getText());
                livreSelectionne.setAnneePublication(Integer.parseInt(anneeField.getText()));
                livreSelectionne.setEtat(etatField.getText());
                livreSelectionne.setStatut(statutField.getText().toLowerCase());

                livreController.modifierLivre(livreSelectionne);
                loadBooks();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre modifié avec succès !");
            }
        });
    }

    private void supprimerLivre() {
        Livre livreSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (livreSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un livre à supprimer.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce livre ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                livreController.supprimerLivre(livreSelectionne.getISBN());
                loadBooks();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre supprimé avec succès !");
            }
        });
    }

    private void showLoginDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connexion Admin");
        dialog.setHeaderText("Entrez vos identifiants administrateur");

        TextField codeAdminField = new TextField();
        codeAdminField.setPromptText("Code Admin");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        VBox content = new VBox(10, new Label("Code Admin:"), codeAdminField, new Label("Mot de passe:"), passwordField);
        dialog.getDialogPane().setContent(content);

        ButtonType loginButtonType = new ButtonType("Se connecter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(codeAdminField.getText(), passwordField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(credentials -> {
            boolean success = adminController.login(credentials.getKey(), credentials.getValue());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie !");
                btnAddBook.setDisable(false);
                btnEditBook.setDisable(false);
                btnDeleteBook.setDisable(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la connexion.");
            }
        });
    }
}
