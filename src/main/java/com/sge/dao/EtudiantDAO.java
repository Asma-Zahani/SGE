package com.sge.dao;

import com.sge.model.Departement;
import com.sge.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO extends DAO<Etudiant> {
    public EtudiantDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();

        try {

            String req = "SELECT * FROM etudiant";
            PreparedStatement pstmt = connection.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setAdresse(rs.getString("adresse"));
                e.setAge(rs.getInt("age"));

                // Charger le département de l'étudiant
                String req2 = "SELECT * FROM departement WHERE codeDept = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(req2);
                pstmt2.setString(1, rs.getString("departement"));

                ResultSet rs2 = pstmt2.executeQuery();

                if (rs2.next()) {
                    Departement dept = new Departement();
                    dept.setCodeDept(rs2.getString("codeDept"));
                    dept.setNomDept(rs2.getString("nomDept"));
                    e.setDepartement(dept);
                }

                etudiants.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return etudiants;
    }

    @Override
    public boolean create(Etudiant entity) {
        try {
            String req =
                    "INSERT INTO Etudiant (matricule, nom, adresse, age, departement) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, entity.getMatricule());
            pstmt.setString(2, entity.getNom());
            pstmt.setString(3, entity.getAdresse());
            pstmt.setInt(4, entity.getAge());
            pstmt.setInt(5, entity.getDepartement().getId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Etudiant find(int id) {
        Etudiant e = new Etudiant();
        try {
            String req = "SELECT * FROM etudiant WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                e.setMatricule(rs.getString("Matricule"));
                e.setNom(rs.getString("Nom"));
                e.setAdresse(rs.getString("Adresse"));
                e.setAge(rs.getInt("Age"));
                DepartementDAO deptDAO = new DepartementDAO(connection);
                e.setDepartement(deptDAO.find(rs.getInt("departement")));
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Etudiant> find(String rechercher) {
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            String req = "SELECT * FROM etudiant e JOIN departement d ON e.departement = d.id " +
                    "WHERE matricule LIKE ? OR nom LIKE ? OR adresse LIKE ? OR age LIKE ? OR d.nomDept LIKE ?";

            PreparedStatement pstmt = connection.prepareStatement(req);

            // pour les champs texte, on utilise %
            pstmt.setString(1, "%" + rechercher + "%");
            pstmt.setString(2, "%" + rechercher + "%");
            pstmt.setString(3, "%" + rechercher + "%");
            pstmt.setString(4, "%" + rechercher + "%");
            pstmt.setString(5, "%" + rechercher + "%");

            ResultSet rs = pstmt.executeQuery();

            DepartementDAO deptDAO = new DepartementDAO(connection);

            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("ID"));
                e.setMatricule(rs.getString("Matricule"));
                e.setNom(rs.getString("Nom"));
                e.setAdresse(rs.getString("Adresse"));
                e.setAge(rs.getInt("Age"));
                e.setDepartement(deptDAO.find(rs.getInt("departement")));
                etudiants.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return etudiants;
    }

    @Override
    public boolean update(Etudiant e) {
        try {
            String req = "UPDATE etudiant SET matricule = ?, nom = ?, adresse = ?, age = ?, departement = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setString(1, e.getMatricule());
            pstmt.setString(2, e.getNom());
            pstmt.setString(3, e.getAdresse());
            pstmt.setInt(4, e.getAge());
            pstmt.setInt(5, e.getDepartement().getId());
            pstmt.setInt(6, e.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Etudiant e) {
        try {
            String req = "DELETE FROM etudiant WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setLong(1, e.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}