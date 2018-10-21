package mirea.pets.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Currency {

    @Id
    @GeneratedValue
    private long id;
    private long user_id;
    private double conversion;

    public Currency(){

    }
    public Currency(long user_id, double conversion){
        this.user_id = user_id;
        this.conversion = conversion;
    }

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}