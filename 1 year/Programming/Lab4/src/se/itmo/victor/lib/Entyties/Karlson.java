package se.itmo.victor.lib.Entyties;

import se.itmo.victor.lib.System.Colors;

public class Karlson extends Entity implements Thinkable{
    protected int trigger;

    public Karlson(int trig){
        this.trigger = trig;
        this.dnaCode = 21;
        this.str = "Объект класса Карлсон";
    }

    public int getTrigger(){
        return this.trigger;
    }

    public void reaction(){
        System.out.println(Colors.ANSI_BLUE + "Карлсон кричит от счастья\n" + Colors.ANSI_RESET);
    }

    @Override
    public String thinkFrom(Thinking somebody) {
        if (somebody.getClass() == Malish.class){
            return "Малыш думает о Карлсоне\n" + Colors.ANSI_PURPLE + "-вот бы стать родной матерью для Карлсона\n" + Colors.ANSI_RESET;
        }
        return "ничего не знаю\n";
    }

}
