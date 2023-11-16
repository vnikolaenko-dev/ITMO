package se.itmo.victor.lib.SW;

public enum Sweety {
    Ch("Шоколад", 5),
    LP("Леденец", 15),
    NT("Засахаренные орешки", 100);

    protected int dnaCode = 50;
    protected String str= "Объект класса Сладкое";
    private final String called;
    private final Integer cost;

    private Sweety(String called, Integer cost){
        this.called = called;
        this.cost = cost;
    }

    public String getName(){
        return this.called;
    }

    public int getCost(){
        return this.cost;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
