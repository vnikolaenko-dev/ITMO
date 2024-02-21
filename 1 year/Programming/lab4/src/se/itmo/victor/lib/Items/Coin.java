package se.itmo.victor.lib.Items;

public enum Coin {
    F1(5),
    F2(15),
    F3(25);
    private Integer cost;

    private Coin(Integer cost){
        this.cost = cost;
    }

    public Integer getInt(){
        return this.cost;
    }

    public void compare(Coin c){
        System.out.println(this.getInt() == c.getInt());
    }

    @Override
    public String toString(){
        return "" + this.cost;
    }
}
