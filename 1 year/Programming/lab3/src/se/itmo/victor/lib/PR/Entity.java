package se.itmo.victor.lib.PR;

import java.util.Objects;

public abstract class Entity{
    public int dnaCode = 10;
    public String str= "Объект класса Сущность";

    @Override
    public String toString() {
        return this.str;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dnaCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return dnaCode == entity.dnaCode;
    }
}
