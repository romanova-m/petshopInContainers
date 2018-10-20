package mirea.pets.domain;

public class Cart {
    int id;
    int user_id;
    int item_id;

    public Cart(){
    }

    public Cart(int id, int user_id, int item_id){
        this.id = id;
        this.item_id = item_id;
        this.user_id = user_id;
    }
    public int getId(){
        return this.id;
    }
    public int getUser_id(){
        return this.user_id;
    }
    public int getItem_id(){
        return this.item_id;
    }
}
