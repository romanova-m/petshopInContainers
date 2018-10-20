package mirea.pets.controller;

import mirea.pets.service.PetService;
import mirea.pets.domain.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class PetController {

    @Autowired
    private PetService petService;


    @RequestMapping(value = "/pet", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Pet> pets() { return petService.pets();}

    @RequestMapping(value = "/pet/id{id}", method = RequestMethod.GET)
    @ResponseBody
    public Pet pet(@PathVariable long id) {
        return petService.petById(id);
    }

    @RequestMapping(value = "/pet/id{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Pet newPet(@PathVariable long id, @RequestBody Pet newPet) {
        return petService.add(newPet, id);
    }

    @RequestMapping(value = "/pet/id{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delPet(@PathVariable long id) {
        petService.del(id);
    }
}