package mirea.items.Controller;

import mirea.items.Domain.Pet;
import mirea.items.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class PetController {

    @Autowired
    private PetService petService;


    @RequestMapping(value = "/pet", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Pet> pets() { return petService.pets();}

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Pet> pet(@PathVariable long id) {
        return petService.petById(id);
    }

    @RequestMapping(value = "/pet", method = RequestMethod.PUT)
    @ResponseBody
    public Pet newPet(@RequestBody Pet newPet) {
        return petService.add(newPet);
    }

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delPet(@PathVariable long id) {
        petService.del(id);
    }
}