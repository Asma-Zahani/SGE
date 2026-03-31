package com.sge.factory;

import com.sge.dao.DAO;

public interface IDAOFactory {
    DAO getEtudiantDAO();
    DAO getDepartementDAO();
}