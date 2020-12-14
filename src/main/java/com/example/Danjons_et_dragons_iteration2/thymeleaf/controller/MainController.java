package com.example.Danjons_et_dragons_iteration2.thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.Danjons_et_dragons_iteration2.thymeleaf.form.PersonageForm;
import com.example.Danjons_et_dragons_iteration2.thymeleaf.model.Personnage;
import com.example.Danjons_et_dragons_iteration2.thymeleaf.model.PersonnageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    private String apiUrl =  "http://localhost:8081/Personnages/" ;

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    //=======================================================================
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    //====================================================================
    @RequestMapping(value = {"/listPersonnages"}, method = RequestMethod.GET)
    public String personList(Model model) {

        Personnage[] personnages = restTemplate.getForObject( apiUrl,
                Personnage[].class);
        model.addAttribute("personages", personnages);

        return "listPersonnages";
    }

    //======================================================================
    @RequestMapping(value = {"/displayPersonnage/{id}"}, method = RequestMethod.GET)
    public String displayPersonage(Model model , @PathVariable int id) {

        Personnage personnage = restTemplate.getForObject(
                apiUrl + id,
                Personnage.class);
        model.addAttribute("personage", personnage);

        return "displayPersonnage";
    }
    //======================================================================
    @RequestMapping(value = {"/addPersonage"}, method = RequestMethod.GET)
    public String showAddPersonagePage(Model model) {

        PersonageForm personForm = new PersonageForm();
        model.addAttribute("personageForm", personForm);

        return "addPersonage";
    }

    //======================================================
    @RequestMapping(value = {"/addPersonage"}, method = RequestMethod.POST)
    public String savePersonage(Model model, //
                                @ModelAttribute("personageForm") PersonageForm personForm) {

        String name = personForm.getName();
        String type = personForm.getType();

        if (name != null && name.length() > 0 //
                && type != null && type.length() > 0) {
            Personnage newPerson = new Personnage(7, name, type);
            //personnages.add(newPerson);
            Personnage person = restTemplate.postForObject(apiUrl,
                    newPerson, Personnage.class);


            return "redirect:/listPersonnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPersonage";
    }
    //===============================================================

    @RequestMapping(value = {"/updatePersonage/{id}"}, method = RequestMethod.GET)
    public String showUpdatePersonagePage(Model model, @PathVariable int id) {

        PersonageForm personForm = new PersonageForm();
        personForm.setId(id);
        Personnage p = restTemplate.getForObject(
                apiUrl+ id,
                Personnage.class);
        model.addAttribute("personageForm", personForm);
        model.addAttribute("personageName", p.getNom());
        model.addAttribute("personageType", p.getType());

        return "updatePersonage";
    }
    //=====================================================================

    @PostMapping(value = {"/updatePersonage/"})
    public String updatePersonage(Model model, //
                                @ModelAttribute("personageForm") PersonageForm personForm ) {
        int id = personForm.getId();
        String name = personForm.getName();
        String type = personForm.getType();

        if (name != null && name.length() > 0 //
                && type != null && type.length() > 0) {
            Personnage newPerson = new Personnage(id, name, type);
            restTemplate.put(apiUrl +id,
                    newPerson);


            return "redirect:/listPersonnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "updatePersonage";
    }
//===================================================================================

    @GetMapping (value = {"/deletePersonage/{id}"})
    public String deletePersonage (Model model, @PathVariable int id){
        restTemplate.delete(apiUrl +id);
        return "redirect:/listPersonnages";
    }

}