package com.sge.utils;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class MySqlRowSet {

    private static final String URL = "jdbc:mysql://localhost/GestionEtudiant";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private MySqlRowSet() {} // constructeur privé

    /**
     * Retourne un CachedRowSet pour tous les départements
     */
    public static CachedRowSet getDepartementsRowSet() {
        try {
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(URL);
            crs.setUsername(USER);
            crs.setPassword(PASSWORD);
            crs.setCommand("SELECT * FROM departement");
            crs.execute();
            return crs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retourne un CachedRowSet pour tous les étudiants
     */
    public static CachedRowSet getEtudiantsRowSet() {
        try {
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(URL);
            crs.setUsername(USER);
            crs.setPassword(PASSWORD);
            crs.setCommand("SELECT * FROM etudiant");
            crs.execute();
            return crs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retourne un CachedRowSet pour les étudiants d'un département donné
     */
    public static CachedRowSet getEtudiantsByDepartement(int departementId) {
        try {
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(URL);
            crs.setUsername(USER);
            crs.setPassword(PASSWORD);
            crs.setCommand("SELECT * FROM etudiant WHERE departement = ?");
            crs.setInt(1, departementId);
            crs.execute();
            return crs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}