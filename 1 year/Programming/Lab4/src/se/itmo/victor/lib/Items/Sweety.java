package se.itmo.victor.lib.Items;

import se.itmo.victor.lib.Items.Coin;

public enum Sweety {
    Ch("Шоколад", 5, Coin.F1),
    LP("Леденец", 15, Coin.F2),
    NT("Засахаренные орешки", 25, Coin.F3);

    protected int dnaCode = 50;
    protected String str= "Объект класса Сладкое";
    private final String called;
    private final Integer cost;
    private final Coin coin;

    private Sweety(String called, Integer cost, Coin coin){
        this.called = called;
        this.cost = cost;
        this.coin = coin;
    }

    public String getName(){
        return this.called;
    }

    public int getCost(){
        return this.cost;
    }
    public Coin getCoin(){
        return this.coin;
    }

    @Override
    public String toString() {
        return this.called;
    }
}
