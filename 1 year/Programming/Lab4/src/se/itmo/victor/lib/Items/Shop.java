package se.itmo.victor.lib.Items;

import se.itmo.victor.lib.Entyties.Malish;

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

    public void buyFull(Malish malish, Malish.Wallet wallet, Malish.Pocket pocket, int saveCount){
        int n = wallet.coin_list.size();
        for (int i=n - 1; i>=saveCount; --i){
            for (int j=this.getSweety() - 1; j>=0; --j){
                if (wallet.coin_list.get(i).getInt() == this.sp[j].getCoin().getInt()){
                    wallet.coin_list.remove(i);
                    System.out.println("Малыш купил " + sp[j].getName());
                    pocket.addSweety(sp[j]);
                    break;
                }
            }
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
