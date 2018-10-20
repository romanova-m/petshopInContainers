package mirea.pets.service;

import mirea.pets.domain.Pet;
import mirea.pets.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Component
public class PetService {

    private HashMap<Long, Pet> map = new HashMap<>();

    @PostConstruct
    public void init(){
        map.put(Long.valueOf(1), new Pet(1,"ALEX",1,50));
        map.put(Long.valueOf(2), new Pet(2,"BOB", 2,40));
    }

    public Collection<Pet> pets() {
        return map.values();
    }


    public Pet petById(long id){
        return map.get(id);
    }

    public Pet add(Pet newPet, long id){
        map.put(Long.valueOf(id), newPet);
        return this.petById(id);
    }

    public void del(long id) {
        map.remove(id);
    }

}
