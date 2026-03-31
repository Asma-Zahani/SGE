package com.sge.model;

import java.util.HashSet;
import java.util.Set;

public class Departement {
    private int id;
    private String codeDept;
    private String nomDept;
    private Set<Etudiant> etudiants = new HashSet<>();

    public Departement() {
    }

    public Departement(int id, String codeDept, String nomDept) {
        this.id = id;
        this.codeDept = codeDept;
        this.nomDept = nomDept;
    }

    public Departement(String codeDept, String nomDept) {
        this.codeDept = codeDept;
        this.nomDept = nomDept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeDept() {
        return codeDept;
    }

    public void setCodeDept(String codeDept) {
        this.codeDept = codeDept;
    }

    public String getNomDept() {
        return nomDept;
    }

    public void setNomDept(String nomDept) {
        this.nomDept = nomDept;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public void addEtudiant(Etudiant e) {
        if (e.getDepartement() != null) {
            e.getDepartement().removeEtudiant(e);
        }
        etudiants.add(e);
        e.setDepartement(this);
    }

    public void removeEtudiant(Etudiant e) {
        etudiants.remove(e);
        e.setDepartement(null);
    }

    @Override
    public String toString() {
        return nomDept;
    }
}