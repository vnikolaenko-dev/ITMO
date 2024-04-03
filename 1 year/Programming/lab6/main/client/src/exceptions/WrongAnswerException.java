package exceptions;

public class WrongAnswerException extends Exception{
    public WrongAnswerException(){
        super("Wrong answer");
    }
}
