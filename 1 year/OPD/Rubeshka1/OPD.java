import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class OPD {
    public static HashMap<String, String> createComand(){
        HashMap<String, String> comands = new HashMap<>();
        comands.put("AND", "2XXX\nAC AND M -> AC");
        comands.put("OR", "3XXX\nAC OR M -> AC");
        comands.put("ADD", "4XXX\nAC + M -> AC");
        comands.put("ADC", "5XXX\nAC + M + C -> AC");
        comands.put("SUB", "6XXX\nAC - M -> AC");
        comands.put("CMP", "7XXX\nУстановить флаг по результату AC - M");
        comands.put("LOOP", "8XXX\nM - 1 -> M; Если M <= 0, то IP + 1 -> IP");
        comands.put("LD", "AXXX\nM -> AC");
        comands.put("SWAM", "BXXX\nM <-> AC");
        comands.put("JUMP", "CXXX\nM -> IP");
        comands.put("ST", "EXXX\nAC -> M");

        comands.put("NOP", "0000\nнет операции");
        comands.put("HLT", "0100\nОстановка");
        comands.put("CLA", "0200\n0 -> AC");
        comands.put("NOT", "0280\n(^AC) -> AC");
        comands.put("CLC", "0300\n0 -> C");
        comands.put("CMC", "0380\n(^C) -> C");
        comands.put("ROL", "0400\nAC и C сдвигаются влево. AC15 -> C, C->AC0");
        comands.put("ROR", "0480\nAC и C сдвигаются вправо. AC0 -> C, C->AC15");
        comands.put("ASL", "0500\nAC сдвигается влево. AC15 -> C, 0->AC0");
        comands.put("ASR", "0580\nAC сдвигается вправо. AC0 -> C, AC15->AC14");

        comands.put("INC", "0700\nAC + 1 -> AC");
        comands.put("DEC", "0740\nAC - 1 -> AC");
        comands.put("NEG", "0780\n(^AC) + 1 -> AC");


        return comands;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        Random rn = new Random();

        HashMap<String, String> comands = createComand();

        while (1==1){
            int randomNum = rn.nextInt(comands.size());
            String key = comands.keySet().toArray()[randomNum].toString();
            System.out.println(key);
            String ans = in.next();
            System.out.println(comands.get(key));
            System.out.println();
        }
    }
}
