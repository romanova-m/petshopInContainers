package mirea.pets.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id = 1;
    private int age = 10;
    private String name = "Max";
    int price = 1000;

    public Pet(){

    }
    public Pet(int id, String name, int age, int price){
        this.id = id;
        this.name = name;
        this.age = age;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
