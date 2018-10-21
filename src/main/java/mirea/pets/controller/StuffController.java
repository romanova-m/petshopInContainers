package mirea.pets.controller;

import mirea.pets.service.StuffService;
import mirea.pets.domain.Stuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
public class StuffController {
    @Autowired
    private StuffService stuffService;

    @RequestMapping(value = "/stuff", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Stuff> stuff(){
        return stuffService.stuff();
    }

    @RequestMapping(value = "/stuff/id{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Stuff> stuff(@PathVariable long id){
        return stuffService.stuffById(id);
    }

    @RequestMapping(value = "/stuff/id{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Stuff newStuff(@PathVariable long id, @RequestBody Stuff newStuff) {
        return stuffService.add(newStuff);
    }

    @RequestMapping(value = "/stuff/id{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delStuff(@PathVariable long id) {
        stuffService.del(id);
    }
}
