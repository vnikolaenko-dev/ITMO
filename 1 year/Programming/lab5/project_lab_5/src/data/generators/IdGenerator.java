package data.generators;

import java.lang.Math;
import java.util.ArrayList;

import managers.CollectionManager;

import javax.persistence.criteria.CriteriaBuilder;

public class IdGenerator {
    private static final Integer min = 1000000;
    private static final Integer max = 10000000;
    private static ArrayList<Long> IdList = new ArrayList<>();

    public static Long generateId(){
        Long id = (long)Math.floor(Math.random() * (max - min + 1)) + min;
        while (IdList.equals(id)){
            id = (long)Math.floor(Math.random() * (max - min + 1)) + min;;
        }
        IdList.add(id);
        return id;
    }
}
