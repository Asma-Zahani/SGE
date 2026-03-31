package com.sge.controller;

import com.sge.dao.DAO;
import com.sge.factory.DAOFactory;
import com.sge.model.Departement;
import com.sge.model.Etudiant;
import com.sge.utils.MySqlRowSet;
import com.sge.view.DepartementView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import javax.sql.rowset.CachedRowSet;
import java.util.List;

public class DepartementController {
    private MainController mainController;
    private DepartementView view;
    private DAO<Departement> departementDAO;

    private CachedRowSet crsDepartements; // Remplace resultSet
    private int currentRow = 0;

    public DepartementController(MainController mainController) {
        view = new DepartementView();
        DAOFactory factory = DAOFactory.getInstance();
        departementDAO = factory.getDepartementDAO();
        this.mainController = mainController;

        initActions();
    }

    private void initActions() {
        view.getAjouterDepartement().setOnAction(e -> ajouterDepartement());
        view.getModifierDepartement().setOnAction(e -> modifierDepartement());
        view.getSupprimerDepartement().setOnAction(e -> supprimerDepartement());
        view.getRechercherDepartement().setOnAction(e -> rechercherDepartement());
        view.getAfficherDepartement().setOnAction(e -> afficherDepartement());

        view.getPrecedentDepartement().setOnAction(e -> precedentDepartement());
        view.getSuivantDepartement().setOnAction(e -> suivantDepartement());

        chargerDepartements();

        view.getTableDepartement().setOnMouseClicked(e -> {
            Departement depart = view.getTableDepartement().getSelectionModel().getSelectedItem();
            if (depart != null) {
                view.getCode().setText(depart.getCodeDept());
                view.getNom().setText(depart.getNomDept());
            }
        });
    }

    private void ajouterDepartement() {
        try {
            String code = view.getCode().getText();
            String nom = view.getNom().getText();

            if (code.isEmpty() || nom.isEmpty()) {
                mainController.showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs.");
                return;
            }
            Departement d = new Departement(code, nom);
            departementDAO.create(d);

            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Département ajouté.");
            chargerDepartements();
            view.viderChamps();
        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout.");
            e.printStackTrace();
        }
    }

    private void modifierDepartement() {
        try {
            Departement selected = view.getTableDepartement().getSelectionModel().getSelectedItem();

            if (selected == null) {
                mainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Sélectionnez un département.");
                return;
            }
            selected.setCodeDept(view.getCode().getText());
            selected.setNomDept(view.getNom().getText());
            departementDAO.update(selected);

            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Département modifié.");
            chargerDepartements();
            view.viderChamps();
        } catch (Exception e) {

            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification.");
            e.printStackTrace();
        }
    }

    private void supprimerDepartement() {
        try {
            Departement selected = view.getTableDepartement().getSelectionModel().getSelectedItem();

            if (selected == null) {
                mainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Sélectionnez un département.");
                return;
            }
            departementDAO.delete(selected);

            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Département supprimé.");
            chargerDepartements();
            view.viderChamps();
        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression.");
            e.printStackTrace();
        }
    }

    private void rechercherDepartement() {
        try {
            String rechercher = view.getRechercher().getText().trim();

            if (rechercher.isEmpty()) {
                mainController.showAlert(Alert.AlertType.WARNING, "Champ vide", "Entrer un code ou un nom de département.");
                return;
            }

            List<Departement> departements = departementDAO.find(rechercher);

            if (departements == null || departements.isEmpty()) {
                mainController.showAlert(Alert.AlertType.INFORMATION, "Résultat", "Aucun département trouvé.");
                view.getTableDepartement().setItems(FXCollections.observableArrayList());
                return;
            }

            ObservableList<Departement> liste = FXCollections.observableArrayList(departements);
            view.getTableDepartement().setItems(liste);

        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche.");
            e.printStackTrace();
        }
    }

    private void afficherDepartement() {
        chargerDepartements();
    }

    private void suivantDepartement() {
        try {
            if (crsDepartements != null && !view.getTableDepartement().getItems().isEmpty()) {
                if (crsDepartements.absolute(currentRow + 1)) {
                    afficherCachedRow(crsDepartements);
                    currentRow++;
                } else {
                    // revenir au début
                    crsDepartements.first();
                    afficherCachedRow(crsDepartements);
                    currentRow = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void precedentDepartement() {
        try {
            if (crsDepartements != null && !view.getTableDepartement().getItems().isEmpty()) {
                if (crsDepartements.absolute(currentRow - 1)) {
                    afficherCachedRow(crsDepartements);
                    currentRow--;
                } else {
                    // aller à la fin
                    crsDepartements.last();
                    afficherCachedRow(crsDepartements);
                    currentRow = crsDepartements.size(); // CachedRowSet supporte size()
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerDepartements() {
        try {
            ObservableList<Departement> liste = FXCollections.observableArrayList();
            crsDepartements = MySqlRowSet.getDepartementsRowSet(); // RowSet des départements
            crsDepartements.beforeFirst(); // assure que le curseur est au début

            while (crsDepartements.next()) {
                Departement d = new Departement(
                        crsDepartements.getInt("id"),
                        crsDepartements.getString("codeDept"),
                        crsDepartements.getString("nomDept")
                );

                CachedRowSet crsEtudiants = MySqlRowSet.getEtudiantsByDepartement(d.getId());
                while (crsEtudiants.next()) {
                    Etudiant e = new Etudiant();
                    e.setNom(crsEtudiants.getString("nom"));

                    d.addEtudiant(e);
                }
                crsEtudiants.close();

                liste.add(d);
            }

            view.getTableDepartement().setItems(liste);

            // initialiser la sélection
            if (!liste.isEmpty()) {
                view.getTableDepartement().getSelectionModel().select(0);
                currentRow = 1;
            }

        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les départements et étudiants.");
            e.printStackTrace();
        }
    }

    private void afficherCachedRow(CachedRowSet crs) {
        try {
            String code = crs.getString("codeDept");
            view.getCode().setText(code);
            view.getNom().setText(crs.getString("nomDept"));

            for (Departement d : view.getTableDepartement().getItems()) {
                if (d.getCodeDept().equals(code)) {
                    view.getTableDepartement().getSelectionModel().select(d);
                    view.getTableDepartement().scrollTo(d);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Parent getView() {
        return view.getRoot();
    }
}