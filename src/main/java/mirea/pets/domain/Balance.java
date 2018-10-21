package mirea.pets.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Balance {
    @Id
    @GeneratedValue
    private long id;
    private long user_id;
    private int val;

    public Balance() {
    }

    public Balance(long user_id, int val) {
        this.user_id = user_id;
        this.val = val;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
