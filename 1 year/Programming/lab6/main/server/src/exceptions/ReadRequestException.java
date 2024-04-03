package exceptions;

public class ReadRequestException extends Exception{
    public ReadRequestException(){
        super("Something wrong with request");
    }
}
