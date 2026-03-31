package com.sge.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static String url = "jdbc:mysql://";
    private static String user = "root";
    private static String password = "";
    private static Connection cn;
    private static String name = "localhost/GestionEtudiant";
    private MySqlConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url + name, user, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getInstance() {
        if (cn == null) {
            new MySqlConnection();
            System.out.println("Création de la connexion à " + name);
        } else {
            System.out.println("Connexion à " + name + " existe déjà");
        }
        return cn;
    }
}