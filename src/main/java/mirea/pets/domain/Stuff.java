package mirea.pets.domain;

public class Stuff {
    private int id = 0;
    private String name = "Brush";
    private int price = 1000;

    public Stuff(){

    }

    public Stuff(int id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
}
