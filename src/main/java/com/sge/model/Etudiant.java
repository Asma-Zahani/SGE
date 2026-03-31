package com.sge.model;

public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String adresse;
    private int age;
    private Departement departement;

    public Etudiant() {
    }

    public Etudiant(int id, String matricule, String nom, String adresse, int age, Departement departement) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.adresse = adresse;
        this.age = age;
        this.departement = departement;
    }

    public Etudiant(String matricule, String nom, String adresse, int age, Departement departement) {
        this.matricule = matricule;
        this.nom = nom;
        this.adresse = adresse;
        this.age = age;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getNomDepartement() {
        return departement != null ? departement.getNomDept() : "";
    }

    @Override
    public String toString() {
        return nom;
    }
}