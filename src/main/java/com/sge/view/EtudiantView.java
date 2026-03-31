package com.sge.view;

import com.sge.model.Departement;
import com.sge.model.Etudiant;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EtudiantView {
    private VBox root = new VBox(15);

    private TextField matricule = new TextField();
    private TextField nom = new TextField();
    private TextField adresse = new TextField();
    private TextField age = new TextField();
    private ComboBox<Departement> departementCombo = new ComboBox<>();

    private Button ajouterEtudiant = new Button("Ajouter étudiant");
    private Button supprimerEtudiant = new Button("Supprimer étudiant");
    private Button modifierEtudiant = new Button("Modifier étudiant");

    private TableView<Etudiant> tableView = new TableView<>();
    private TextField rechercher = new TextField();
    private Button rechercherEtudiants = new Button("Rechercher étudiants");
    private Button afficherEtudiants = new Button("Afficher les étudiants");
    private Button suivantEtudiant = new Button(">>");
    private Button precedentEtudiant = new Button("<<");

    public EtudiantView() {
        construireVue();
    }

    public void construireVue() {
        root.setStyle("-fx-padding: 20;");
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Gestion des Étudiants");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        root.getChildren().addAll(title, createForm(), createTable());
    }

    private VBox createForm() {
        VBox form = new VBox(10.0);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 15;");

        Label title = new Label("Formulaire Étudiant");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox hboxNCE = createFormField("Matricule", matricule, "Entrer le matricule");
        HBox hboxName = createFormField("Nom", nom, "Entrer le nom complet");
        HBox hboxAdresse = createFormField("Adresse", adresse, "Entrer l'adresse");
        HBox hboxAge = createFormField("Âge", age, "Entrer l'âge");

        Label label = new Label("Département");
        label.setStyle("-fx-min-width: 100;");
        departementCombo.setPromptText("Choisir un département");
        departementCombo.setPrefWidth(240.0);
        HBox hboxDepartement = new HBox(10, label, departementCombo);

        HBox hboxActions = new HBox(10, createStyledButton(ajouterEtudiant), createStyledButton(modifierEtudiant), createStyledButton(supprimerEtudiant));
        hboxActions.setStyle("-fx-padding: 10 0 0 0;");

        form.getChildren().addAll(title, hboxNCE, hboxName, hboxAdresse, hboxAge, hboxDepartement, hboxActions);
        return form;
    }

    private VBox createTable() {
        VBox table = new VBox(10.0);
        table.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 15;");

        Label title = new Label("Liste Étudiants");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox hboxRechercher = createFormField(null, rechercher, "Rechercher par matricule, nom, adresse, age, etc..");
        HBox hboxActions = new HBox(10, hboxRechercher, createStyledButton(rechercherEtudiants), createStyledButton(afficherEtudiants), createStyledButton(precedentEtudiant), createStyledButton(suivantEtudiant));

        tableView.getColumns().addAll(
                createColumn("Matricule", "matricule", 150),
                createColumn("Nom", "nom", 150),
                createColumn("Adresse", "adresse", 200),
                createColumn("Âge", "age", 150),
                createColumn("Département", "nomDepartement", 180)
        );

        table.getChildren().addAll(title, hboxActions, tableView);
        return table;
    }

    private <T> TableColumn<Etudiant, T> createColumn(String title, String property, double width) {
        TableColumn<Etudiant, T> column = new TableColumn<>(title);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private HBox createFormField(String labelText, TextField textField, String promptText) {
        HBox hBox = new HBox(10.0);

        if (labelText != null && !labelText.isEmpty()) {
            Label label = new Label(labelText);
            label.setStyle("-fx-min-width: 100;");
            hBox.getChildren().add(label);
        }

        textField.setPromptText(promptText);
        textField.setPrefWidth(240.0);
        textField.setPrefHeight(35.0);

        hBox.getChildren().addAll(textField);
        return hBox;
    }

    private Button createStyledButton(Button button) {
        button.setStyle("-fx-padding: 8 20;");
        return button;
    }

    public VBox getRoot() {
        return root;
    }

    public Button getPrecedentEtudiant() {
        return precedentEtudiant;
    }

    public Button getSuivantEtudiant() {
        return suivantEtudiant;
    }

    public Button getAfficherEtudiants() {
        return afficherEtudiants;
    }

    public Button getRechercherEtudiants() {
        return rechercherEtudiants;
    }

    public TextField getRechercher() {
        return rechercher;
    }

    public Button getModifierEtudiant() {
        return modifierEtudiant;
    }

    public Button getSupprimerEtudiant() {
        return supprimerEtudiant;
    }

    public Button getAjouterEtudiant() {
        return ajouterEtudiant;
    }

    public TextField getAge() {
        return age;
    }

    public TextField getAdresse() {
        return adresse;
    }

    public TextField getNom() {
        return nom;
    }

    public TextField getMatricule() {
        return matricule;
    }

    public ComboBox<Departement> getDepartementCombo() {
        return departementCombo;
    }

    public TableView<Etudiant> getTableEtudiant() {
        return tableView;
    }

    public void viderChamps() {
        matricule.clear();
        nom.clear();
        adresse.clear();
        age.clear();
        departementCombo.getSelectionModel().clearSelection();
        departementCombo.setPromptText("Choisir un département");
    }
}
