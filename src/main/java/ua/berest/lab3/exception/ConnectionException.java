package ua.berest.lab3.exception;

/**
 * Created by Oleg on 15.02.2016.
 */
public class ConnectionException extends Exception {
    public ConnectionException(String message, Exception e){
        super(e);
        System.out.println(message);
    }
}
