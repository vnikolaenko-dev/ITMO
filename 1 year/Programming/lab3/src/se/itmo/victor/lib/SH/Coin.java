package se.itmo.victor.lib.SH;

public enum Coin {
    F1(5),
    F2(15),
    F3(25);
    private final Integer cost;

    private Coin(Integer cost){
        this.cost = cost;

    }

    public Integer getInt(){
        return this.cost;
    }

    @Override
    public String toString(){
        return "" + this.cost;
    }
}
