package com.sge.controller;

import com.sge.view.MainView;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class MainController {

    private MainView view;

    public MainController() {
        view = new MainView();
        initTabs();
    }

    private void initTabs() {

        EtudiantController etudiantController = new EtudiantController(this);
        DepartementController departementController = new DepartementController(this);

        view.addTab("Étudiants", etudiantController.getView());
        view.addTab("Départements", departementController.getView());
    }

    public Parent getView() {
        return view.getRoot();
    }

    public void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}