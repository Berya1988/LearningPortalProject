package ua.berest.lab3.controller.processors;

import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 18.02.2016.
 */
public abstract class Processor {
    protected String actionToPerform = null;
    public boolean canProcess(String action) {
        return action.equals(actionToPerform);
    }
    public abstract String process(HttpServletRequest request) throws DataAccessException;
}
