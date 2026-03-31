package com.sge.factory;

import com.sge.dao.DAO;
import com.sge.dao.DepartementDAO;
import com.sge.dao.EtudiantDAO;
import com.sge.model.Departement;
import com.sge.model.Etudiant;
import com.sge.utils.MySqlConnection;

import java.sql.Connection;

public class DAOFactory {
    private static DAOFactory instance = new DAOFactory();
    private Connection conn = MySqlConnection.getInstance();

    private DAOFactory() {} // constructeur privé

    public static DAOFactory getInstance() {
        return instance;
    }

    public DAO getEtudiantDAO() {
        return new EtudiantDAO(conn);
    }

    public DAO getDepartementDAO() {
        return new DepartementDAO(conn);
    }

    public DAO getDAO(Class<?> c) {
        if (c.equals(Etudiant.class)) return getEtudiantDAO();
        if (c.equals(Departement.class)) return getDepartementDAO();
        throw new IllegalArgumentException("DAO non trouvé pour la classe : " + c.getName());
    }
}