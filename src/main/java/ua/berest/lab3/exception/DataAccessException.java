package ua.berest.lab3.exception;

/**
 * Created by Oleg on 15.02.2016.
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message, Exception e){
        super(e);
        System.out.println(message);
    }
}