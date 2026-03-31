package com.sge.controller;

import com.sge.dao.DAO;
import com.sge.factory.DAOFactory;
import com.sge.model.Departement;
import com.sge.model.Etudiant;
import com.sge.utils.MySqlConnection;
import com.sge.view.EtudiantView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class EtudiantController {
    private MainController mainController;
    private EtudiantView view;
    private DAO<Etudiant> etudiantDAO;
    private DAO<Departement> departementDAO;

    private ResultSet resultSet;
    private Connection connection;

    public EtudiantController(MainController mainController) {
        view = new EtudiantView();
        DAOFactory factory = DAOFactory.getInstance();
        etudiantDAO = factory.getEtudiantDAO();
        departementDAO = factory.getDepartementDAO();
        this.mainController = mainController;

        initActions();
    }

    private void initActions() {

        view.getAjouterEtudiant().setOnAction(e -> ajouterEtudiant());
        view.getModifierEtudiant().setOnAction(e -> modifierEtudiant());
        view.getSupprimerEtudiant().setOnAction(e -> supprimerEtudiant());
        view.getRechercherEtudiants().setOnAction(e -> rechercherEtudiant());
        view.getAfficherEtudiants().setOnAction(e -> afficherEtudiant());

        view.getPrecedentEtudiant().setOnAction(e -> precedentEtudiant());
        view.getSuivantEtudiant().setOnAction(e -> suivantEtudiant());

        chargerEtudiants();
        chargerDepartements();

        view.getTableEtudiant().setOnMouseClicked(e -> {
            Etudiant et = view.getTableEtudiant().getSelectionModel().getSelectedItem();
            if (et != null) {
                view.getMatricule().setText(et.getMatricule());
                view.getNom().setText(et.getNom());
                view.getAdresse().setText(et.getAdresse());
                view.getAge().setText(String.valueOf(et.getAge()));
                view.getDepartementCombo().setValue(et.getDepartement());
            }
        });
    }

    private void ajouterEtudiant() {
        try {
            String matricule = view.getMatricule().getText();
            String nom = view.getNom().getText();
            String adresse = view.getAdresse().getText();
            String ageString = view.getAge().getText();
            Departement dep = view.getDepartementCombo().getSelectionModel().getSelectedItem();

            if (matricule.isEmpty() || nom.isEmpty() || adresse.isEmpty() || ageString.isEmpty() || dep == null) {
                mainController.showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs.");
                return;
            }

            int age = Integer.parseInt(ageString);
            Etudiant etudiant = new Etudiant(matricule, nom, adresse, age, dep);
            etudiantDAO.create(etudiant);
            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant ajouté avec succès.");

            chargerEtudiants();
            view.viderChamps();

        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout.");
        }
    }

    private void modifierEtudiant() {
        try {
            Etudiant selected = view.getTableEtudiant().getSelectionModel().getSelectedItem();

            if (selected == null) {
                mainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un étudiant.");
                return;
            }

            selected.setMatricule(view.getMatricule().getText());
            selected.setNom(view.getNom().getText());
            selected.setAdresse(view.getAdresse().getText());
            selected.setAge(Integer.parseInt(view.getAge().getText()));
            selected.setDepartement(view.getDepartementCombo().getSelectionModel().getSelectedItem());
            etudiantDAO.update(selected);

            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant modifié.");
            chargerEtudiants();
            view.viderChamps();
        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification.");
        }
    }

    private void supprimerEtudiant() {
        try {
            Etudiant selected = view.getTableEtudiant().getSelectionModel().getSelectedItem();

            if (selected == null) {
                mainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un étudiant.");
                return;
            }

            etudiantDAO.delete(selected);

            mainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant supprimé.");
            chargerEtudiants();
            view.viderChamps();
        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression.");
        }
    }

    private void rechercherEtudiant() {
        try {
            String rechercher = view.getRechercher().getText().trim();

            if (rechercher.isEmpty()) {
                mainController.showAlert(Alert.AlertType.WARNING, "Champ vide", "Entrer un matricule, un nom, une adresse ou un age");
                return;
            }

            List<Etudiant> etudiants = etudiantDAO.find(rechercher);

            if (etudiants == null || etudiants.isEmpty()) {
                mainController.showAlert(Alert.AlertType.INFORMATION, "Résultat", "Aucun étudiant trouvé.");
                view.getTableEtudiant().setItems(FXCollections.observableArrayList());
                return;
            }

            ObservableList<Etudiant> liste = FXCollections.observableArrayList(etudiants);
            view.getTableEtudiant().setItems(liste);

        } catch (Exception ex) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche.");
            ex.printStackTrace();
        }
    }

    private void afficherEtudiant() {
        chargerEtudiants();
    }

    private void suivantEtudiant() {
        try {
            if (resultSet != null) {
                if (resultSet.next()) {
                    afficherResultSet(resultSet);
                } else {
                    if (resultSet.first()) {
                        afficherResultSet(resultSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void precedentEtudiant() {
        try {
            if (resultSet != null) {
                if (resultSet.previous()) {
                    afficherResultSet(resultSet);
                } else {
                    if (resultSet.last()) {
                        afficherResultSet(resultSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerEtudiants() {
        try {
            connection = MySqlConnection.getInstance();
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            resultSet = stmt.executeQuery("SELECT * FROM etudiant");
            ObservableList<Etudiant> liste = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int idDept = resultSet.getInt("departement");

                Departement departement = departementDAO.find(idDept);

                Etudiant e = new Etudiant(
                        resultSet.getInt("id"),
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getInt("age"),
                        departement
                );
                liste.add(e);
            }

            view.getTableEtudiant().setItems(liste);
            resultSet.beforeFirst();
        } catch (Exception e) {
            mainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les étudiants.");
        }
    }

    private void chargerDepartements() {
        view.getDepartementCombo().setItems(FXCollections.observableArrayList(departementDAO.findAll()));
    }

    private void afficherResultSet(ResultSet rs) {
        try {
            String matricule = rs.getString("matricule");

            view.getMatricule().setText(matricule);
            view.getNom().setText(rs.getString("nom"));
            view.getAdresse().setText(rs.getString("adresse"));
            view.getAge().setText(String.valueOf(rs.getInt("age")));
            view.getDepartementCombo()
                    .getSelectionModel()
                    .select(departementDAO.find(resultSet.getInt("departement")));

            for (Etudiant et : view.getTableEtudiant().getItems()) {
                if (et.getMatricule().equals(matricule)) {
                    view.getTableEtudiant().getSelectionModel().select(et);
                    view.getTableEtudiant().scrollTo(et);
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