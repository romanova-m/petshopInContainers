package mirea.items.Service;

import mirea.items.Domain.Pet;
import mirea.items.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @PostConstruct
    public void init() {
        petRepository.save(new Pet("ALEX", 1, 50));
        petRepository.save(new Pet("BOB", 2, 40));
        petRepository.save(new Pet("JOHN", 1, 50));
        petRepository.save(new Pet("TED", 2, 40));
    }

    public Iterable<Pet> pets() {
        return petRepository.findAll();
    }

    public Optional<Pet> petById(long id) {
        return petRepository.findById(id);
    }

    public Pet add(Pet pet) {
        petRepository.save(pet);
        return pet;
    }

    public void del(long id) {
        petRepository.deleteById(id);
    }
}
