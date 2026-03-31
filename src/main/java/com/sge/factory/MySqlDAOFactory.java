package com.sge.factory;

import com.sge.dao.DAO;
import com.sge.dao.DepartementDAO;
import com.sge.dao.EtudiantDAO;
import com.sge.utils.MySqlConnection;

import java.sql.Connection;

public class MySqlDAOFactory implements IDAOFactory {
    private Connection conn = MySqlConnection.getInstance();

    public DAO getEtudiantDAO() {
        return new EtudiantDAO(conn);
    }

    public DAO getDepartementDAO() {
        return new DepartementDAO(conn);
    }
}