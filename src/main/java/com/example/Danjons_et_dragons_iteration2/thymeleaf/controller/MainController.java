package com.example.Danjons_et_dragons_iteration2.thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.Danjons_et_dragons_iteration2.thymeleaf.form.PersonageForm;
import com.example.Danjons_et_dragons_iteration2.thymeleaf.model.Personnage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private static final List<Personnage> personnagesList = new ArrayList<>();

    static {
        personnagesList.add(new Personnage(1 , "khad" , "guerrier"));
        personnagesList.add(new Personnage(2 , "khadi" , "guerrier"));
        personnagesList.add(new Personnage(3 , "khadij" , "magicien"));
        personnagesList.add(new Personnage(4 , "khadija" , "guerrier"));
        personnagesList.add(new Personnage(5 , "khad" , "magicien"));
    }

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = { "/listPersonnages" }, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("personages", personnagesList);

        return "listPersonnages";
    }

    @RequestMapping(value = { "/addPersonage" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        PersonageForm personForm = new PersonageForm();
        model.addAttribute("personageForm", personForm);

        return "addPersonage";
    }

    @RequestMapping(value = { "/addPersonage" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
                             @ModelAttribute("personageForm") PersonageForm personForm) {

        String name = personForm.getName();
        String type = personForm.getType();

        if (name != null && name.length() > 0 //
                && type != null && type.length() > 0) {
            Personnage newPerson = new Personnage(1 , name, type);
            personnagesList.add(newPerson);

            return "redirect:/listPersonnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPersonage";
    }

}