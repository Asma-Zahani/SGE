package com.sge.view;

import com.sge.model.Departement;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DepartementView {
    private VBox root = new VBox(15);

    private TextField code = new TextField();
    private TextField nom = new TextField();

    private Button ajouterDepartement = new Button("Ajouter département");
    private Button modifierDepartement = new Button("Modifier département");
    private Button supprimerDepartement = new Button("Supprimer département");

    private TableView<Departement> tableView = new TableView<>();
    private TextField rechercher = new TextField();
    private Button rechercherDepartement = new Button("Rechercher départements");
    private Button afficherDepartement = new Button("Afficher les départements");
    private Button suivantDepartement = new Button(">>");
    private Button precedentDepartement = new Button("<<");

    public DepartementView() {
        construireVue();
    }

    private void construireVue() {
        root.setStyle("-fx-padding: 20;");
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Gestion des Départements");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        root.getChildren().addAll(title, createForm(), createTable());
    }

    private VBox createForm() {
        VBox form = new VBox(10.0);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 15;");

        Label title = new Label("Formulaire Département");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox hboxNCE = createFormField("Code", code, "Entrer le code");
        HBox hboxName = createFormField("Nom", nom, "Entrer le nom");

        HBox hboxActions = new HBox(10, createStyledButton(ajouterDepartement), createStyledButton(modifierDepartement), createStyledButton(supprimerDepartement));
        hboxActions.setStyle("-fx-padding: 10 0 0 0;");

        form.getChildren().addAll(title, hboxNCE, hboxName, hboxActions);
        return form;
    }

    private VBox createTable() {
        VBox table = new VBox(10.0);
        table.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 15;");

        Label title = new Label("Liste Départements");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox hboxRechercher = createFormField(null, rechercher, "Rechercher par code ou nom");
        HBox hboxActions = new HBox(10, hboxRechercher, createStyledButton(rechercherDepartement), createStyledButton(afficherDepartement), createStyledButton(precedentDepartement), createStyledButton(suivantDepartement));

        tableView.getColumns().addAll(
                createColumn("Code", "codeDept", 150),
                createColumn("Nom", "nomDept", 200),
                createColumn("Étudiants", "etudiants", 476)
        );

        table.getChildren().addAll(title, hboxActions, tableView);
        return table;
    }

    private <T> TableColumn<Departement, T> createColumn(String title, String property, double width) {
        TableColumn<Departement, T> column = new TableColumn<>(title);
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

    public TextField getCode() {
        return code;
    }

    public TextField getNom() {
        return nom;
    }

    public Button getAjouterDepartement() {
        return ajouterDepartement;
    }

    public Button getModifierDepartement() {
        return modifierDepartement;
    }

    public Button getSupprimerDepartement() {
        return supprimerDepartement;
    }

    public TableView<Departement> getTableDepartement() {
        return tableView;
    }

    public TextField getRechercher() {
        return rechercher;
    }

    public Button getRechercherDepartement() {
        return rechercherDepartement;
    }

    public Button getAfficherDepartement() {
        return afficherDepartement;
    }

    public Button getSuivantDepartement() {
        return suivantDepartement;
    }

    public Button getPrecedentDepartement() {
        return precedentDepartement;
    }

    public void viderChamps() {
        code.clear();
        nom.clear();
    }
}
