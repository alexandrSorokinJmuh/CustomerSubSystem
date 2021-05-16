package com.example.offer_sub_system.controllers;

import com.example.offer_sub_system.entities.Characteristic;
import com.example.offer_sub_system.services.CharacteristicService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/characteristic", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacteristicController {
    CharacteristicService characteristicService;

    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @ModelAttribute("allCharacteristic")
    public List<Characteristic> getAll(){
        List<Characteristic> characteristicList = characteristicService.getAll();
        return characteristicList;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title", "Characteristics");
        return "characteristic/index";
    }

    @GetMapping("/{characteristic_id}")
    public String show(@PathVariable("characteristic_id") int id, Model model) {
        model.addAttribute("characteristic", characteristicService.getById(id));
        model.addAttribute("title", "Show characteristic");
        return "characteristic/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("characteristic") Characteristic characteristic, Model model) {
        model.addAttribute("title", "Create characteristic");
        return "characteristic/new";
    }

    @GetMapping("/{characteristic_id}/edit")
    public String edit(Model model, @PathVariable("characteristic_id") int id) {
        model.addAttribute("characteristic", characteristicService.getById(id));
        model.addAttribute("title", "Edit category");
        return "characteristic/edit";
    }
}
