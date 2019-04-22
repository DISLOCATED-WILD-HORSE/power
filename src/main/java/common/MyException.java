package common;

public class MyException extends RuntimeException {


    public MyException(Exception e,String message){
        super(message, e);
    }

}
