package mirea.pets.domain;

public class Balance {
    private int id;
    private int user_id;
    private int val;

    public Balance() {
    }

    public Balance(int id, int user_id, int val) {
        this.id = id;
        this.user_id = user_id;
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
