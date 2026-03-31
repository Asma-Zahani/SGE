package com.sge.dao;

import com.sge.model.Departement;
import com.sge.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO extends DAO<Departement> {
    public DepartementDAO(Connection connection) {
        super(connection);
    }

    public List<Departement> findAll() {
        List<Departement> departements = new ArrayList<>();
        try {
            String req = "SELECT * FROM Departement";
            PreparedStatement pstmt = connection.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Departement dept = new Departement();
                dept.setId(rs.getInt("id"));
                dept.setCodeDept(rs.getString("codeDept"));
                dept.setNomDept(rs.getString("nomDept"));

                // Charger les étudiants du département
                String req2 = "SELECT * FROM Etudiant WHERE departement = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(req2);
                pstmt2.setString(1, dept.getCodeDept());

                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Etudiant e = new Etudiant();
                    e.setMatricule(rs2.getString("Matricule"));
                    e.setNom(rs2.getString("Nom"));
                    e.setDepartement(dept);

                    dept.addEtudiant(e);
                }

                departements.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departements;
    }

    @Override
    public boolean create(Departement entity) {
        try {
            String req = "INSERT INTO Departement (codeDept, nomDept) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, entity.getCodeDept());
            pstmt.setString(2, entity.getNomDept());
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
    public Departement find(int id) {
        Departement dept = new Departement();
        try {
            String req = "SELECT * FROM Departement WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                dept.setId(rs.getInt("id"));
                dept.setCodeDept(rs.getString("codeDept"));
                dept.setNomDept(rs.getString("nomDept"));

                String req2 = "SELECT * FROM Etudiant WHERE departement = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(req2);
                pstmt2.setString(1, rs.getString("id"));
                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Etudiant e = new Etudiant();
                    e.setNom(rs2.getString("Nom"));
                    dept.addEtudiant(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dept;
    }

    @Override
    public List<Departement> find(String recherche) {
        List<Departement> departements = new ArrayList<>();
        try {
            String req = "SELECT * FROM Departement WHERE codeDept LIKE ? OR nomDept LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(req);

            pstmt.setString(1, "%" + recherche + "%");
            pstmt.setString(2, "%" + recherche + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Departement dept = new Departement();
                dept.setId(rs.getInt("id"));
                dept.setCodeDept(rs.getString("codeDept"));
                dept.setNomDept(rs.getString("nomDept"));

                String req2 = "SELECT * FROM Etudiant WHERE departement = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(req2);
                pstmt2.setInt(1, rs.getInt("id"));

                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Etudiant e = new Etudiant();
                    e.setNom(rs2.getString("Nom"));
                    dept.addEtudiant(e);
                }

                departements.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departements;
    }

    @Override
    public boolean update(Departement dept) {
        try {
            String req = "UPDATE Departement SET codeDept = ?, nomDept = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setString(1, dept.getCodeDept());
            pstmt.setString(2, dept.getNomDept());
            pstmt.setLong(3, dept.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(Departement dept) {
        try {
            String req = "DELETE FROM Departement WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setLong(1, dept.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
