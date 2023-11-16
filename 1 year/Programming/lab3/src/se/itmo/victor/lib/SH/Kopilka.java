package se.itmo.victor.lib.SH;

import java.lang.Math;
import java.util.Objects;

import se.itmo.victor.lib.PR.Entity;
import se.itmo.victor.lib.System.Colors;
public class Kopilka {
    protected Integer coins;
    public String description;
    public int dnaCode = 40;
    protected String str= "Объект класса Копилка";
    public Kopilka(Integer n){
        this.coins = n;
        checkWight();
        System.out.println("У малыша " + this.description + " копилка\n");
    }

    public void checkWight(){
        if (this.coins > 50){
            this.description = Colors.ANSI_RED + "очень тяжелая" + Colors.ANSI_RESET;
        }
        else if (this.coins > 25){
            this.description = Colors.ANSI_YELLOW + "тяжеловатая" + Colors.ANSI_RESET;
        }
        else {
            this.description = Colors.ANSI_GREEN + "легкая" + Colors.ANSI_RESET;
        }
    }

    public Coin getCoin(){
        this.coins -= 1;
        checkWight();

        double chance = Math.random();
        if (chance > 0.6) {
            return Coin.F3;
        }
        else if (chance > 0.3) {
            return Coin.F2;
        }
        else
            return Coin.F1;
    }

    public Integer getCount(){
        return this.coins;
    }

    @Override
    public String toString() {
        return this.str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kopilka kopilka = (Kopilka) o;
        return dnaCode == kopilka.dnaCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dnaCode);
    }
}
