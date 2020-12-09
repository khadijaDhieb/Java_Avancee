package com.example.Danjons_et_dragons_iteration2.web.controller;

import com.example.Danjons_et_dragons_iteration2.model.Personnage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PersonnageController {

    private final ArrayList<Personnage> perso_list= new ArrayList<>();

    public PersonnageController() {
        this.perso_list.add(new Personnage(1 , "khad" , "guerrier"));
        this.perso_list.add(new Personnage(2 , "khadi" , "guerrier"));
        this.perso_list.add(new Personnage(3 , "khadij" , "magicien"));
        this.perso_list.add(new Personnage(4 , "khadija" , "guerrier"));
        this.perso_list.add(new Personnage(5 , "khad" , "magicien"));
    }


    @GetMapping(value = "/Personnages")
    public ArrayList<Personnage> listePersonnages() {
        return perso_list;
    }

    @GetMapping(value = "/Personnages/{id}")
    public Personnage affichePersonnage(@PathVariable int id) {
        for (Personnage personnage : perso_list) {
            if(personnage.getId() ==id){
                return personnage;
            }
        }
        return null;
    }
}
