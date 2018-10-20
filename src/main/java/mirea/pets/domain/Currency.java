package mirea.pets.domain;

public class Currency {

    public int id;
    public int user_id;
    public double conversion;

    public Currency(){

    }
    public Currency(int id, int user_id, double conversion){
        this.id = id;
        this.user_id = user_id;
        this.conversion = conversion;
    }

    int getId(){
        return this.id;
    }
    int getUser_id(){
        return this.user_id;
    }
}