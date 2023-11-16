package se.itmo.victor.lib.SH;

import se.itmo.victor.lib.SW.Sweety;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class Shop {
    protected Sweety[] sp;
    public Shop(Sweety s1, Sweety s2, Sweety s3){
        this.sp = new Sweety[]{s1, s2, s3};
    }
    public int dnaCode = 50;
    public String str= "Объект класса Магазин";

    public int getSweety(){
        return this.sp.length;
    }

    public void buyFull(int m, Shop sh){
        // сделать в общем виде
        for (int i=sh.getSweety() - 1; i>=0; --i){
            int c = 0;
            while (m >= this.sp[i].getCost()){
                m -= this.sp[i].getCost();
                c += 1;
            }
            System.out.println("Малыш купил " + c + " " + sp[i].getName());
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return this.str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return dnaCode == shop.dnaCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dnaCode);
    }
}
