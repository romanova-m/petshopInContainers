package mirea.pets.service;

import mirea.pets.domain.Stuff;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Component
public class StuffService {

    private HashMap<Long, Stuff> map = new HashMap();

    @PostConstruct
    public void init(){
        map.put(Long.valueOf(1), new Stuff(1,"Food", 50));
        map.put(Long.valueOf(2), new Stuff(2,"Brush", 20));
    }

    public Collection<Stuff> stuff() {
        return map.values();
    }

    public Stuff stuffById(long id) {
        return map.get(Long.valueOf(id));
    }

    public Stuff add(Stuff newStuff, long id){
        map.put(Long.valueOf(id), newStuff);
        return this.stuffById(id);
    }

    public void del(long id) {
        map.remove(id);
    }
}
