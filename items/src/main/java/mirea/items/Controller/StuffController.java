package mirea.items.Controller;

import mirea.items.Domain.Stuff;
import mirea.items.Service.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/stuff/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Stuff> stuff(@PathVariable long id){
        return stuffService.stuffById(id);
    }

    @RequestMapping(value = "/stuff", method = RequestMethod.PUT)
    @ResponseBody
    public Stuff newStuff(@RequestBody Stuff newStuff) {
        return stuffService.add(newStuff);
    }

    @RequestMapping(value = "/stuff/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delStuff(@PathVariable long id) {
        stuffService.del(id);
    }
}
