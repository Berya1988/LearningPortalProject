package ua.berest.lab3.exception;

/**
 * Created by Oleg on 15.02.2016.
 */
public class ProblemWithConnectionException extends Exception {
    public ProblemWithConnectionException(Exception e, String message){
        super(e);
        System.out.println(message);
    }
}
