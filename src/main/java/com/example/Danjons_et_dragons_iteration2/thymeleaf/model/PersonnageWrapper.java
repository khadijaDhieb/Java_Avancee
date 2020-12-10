package com.example.Danjons_et_dragons_iteration2.thymeleaf.model;

import java.util.ArrayList;
import java.util.List;

public class PersonnageWrapper {
    private List<Personnage> personnages;

    public PersonnageWrapper() {
        personnages = new ArrayList<>();
    }

    public PersonnageWrapper(List<Personnage> personnages) {
        this.personnages = personnages;
    }

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(List<Personnage> personnages) {
        this.personnages = personnages;
    }
}
