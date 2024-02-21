package se.itmo.victor.lib.Items;

import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    protected static String[] family_names= {};
    protected ArrayList<Guy> family = new ArrayList<Guy>();

    // ВНУТРЕННИЙ КЛАСС
    public static class Guy{
        public String name;
        public boolean isSeat;
        public Guy(String name){
            if (Arrays.asList(family_names).contains(name)){
                this.isSeat = true;
            }
            else this.isSeat = false;
            this.name = name;
        }
        public void drink(Drinkable x){
            System.out.println(x.drink());
        }
        public void seat(){
            // АНОНИМНЫЙ КЛАСС
            if (this.isSeat){
                drink(new Drinkable(){
                    public String drink(){
                        return name + " пьет " + drink;
                    }
                });}
            else System.out.println(name + " не сидит за столом, чтобы пить.\n");
        }

        @Override
        public String toString(){
            if (this.isSeat){
                return this.name + " сидит за столом";
            }
            else return this.name + " не сидит за столом";
        }
    }

    public Table(String[] family_names){
        this.family_names = family_names;
        for (int i=0; i<family_names.length; ++i){
            family.add(new Table.Guy(family_names[i]));
        }
    }

    public void checkFamily(){
        for (int i=0; i<this.family.size(); ++i){
            family.get(i).seat();
        }
    }
}
