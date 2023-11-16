package se.itmo.victor.lib.PR;

import se.itmo.victor.lib.System.Colors;

public class Dog extends Entity implements Thinkable{

    public Dog() {
        this.dnaCode = 11;
        this.str= "Объект класса Собака";
    }
    @Override
    public String thinkFrom(Thinking somebody) {
        if (somebody.getClass() == Malish.class) {
            if (((Malish) somebody).coinsCount() == 0) {
                return "Малыш думает о собаке:\n" + Colors.ANSI_PURPLE + "-тот, кто решил стать Карлсону родной матерью, не может позволить себе роскошь иметь собаку\n" + Colors.ANSI_RESET;
            }
            else {
                return "Малыш думает о собаке:\n" + Colors.ANSI_PURPLE + "-хочу, чтобы у меня была собака\n" + Colors.ANSI_RESET;
            }
        }
        return "ничего не знаю";
    }

}
