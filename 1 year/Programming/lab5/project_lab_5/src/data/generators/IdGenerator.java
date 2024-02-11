package data.generators;

import java.lang.Math;
import java.util.ArrayList;

import exceptions.ReplayIdException;
import exceptions.WrongArgumentException;
import managers.CollectionManager;

import javax.persistence.criteria.CriteriaBuilder;

public class IdGenerator {
    private static final Integer min = 1000000;
    private static final Integer max = 10000000;
    private static ArrayList<Long> IdList = new ArrayList<>();

    public IdGenerator(){
        IdList = new ArrayList<>();
    }

    public static Long generateId(){
        Long id = (long)Math.floor(Math.random() * (max - min + 1)) + min;
        while (IdList.contains(id)){
            id = (long)Math.floor(Math.random() * (max - min + 1)) + min;;
        }
        IdList.add(id);
        return id;
    }

    public static boolean idIsUnic(long id) throws ReplayIdException {
        if (IdList.contains(id)){
           return false;
        }
        IdList.add(id);
        return true;
    }

    public static void add(long id){
        IdList.add(id);
    }
}
