package exceptions;

public class NoElementException extends Exception{
    public NoElementException(String key){
        super("No element in collection with key: " + key);
    }

    public NoElementException(Long id){
        super("No element in collection with id: " + id);
    }
}
