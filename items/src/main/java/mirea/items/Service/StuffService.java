package mirea.items.Service;

import mirea.items.Domain.Stuff;
import mirea.items.Repository.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class StuffService {

    @Autowired
    private StuffRepository stuffRepository;

    @PostConstruct
    public void init(){
        stuffRepository.save(new Stuff("Food", 50));
        stuffRepository.save(new Stuff("Brush", 20));
    }

    public Iterable<Stuff> stuff() {
        return stuffRepository.findAll();
    }

    public Optional<Stuff> stuffById(long id) {
        return stuffRepository.findById(id);
    }

    public Stuff add(Stuff stuff){
        stuffRepository.save(stuff);
        return stuff;
    }

    public void del(long id) {
        stuffRepository.deleteById(id);
    }
}