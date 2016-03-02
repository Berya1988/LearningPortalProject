package ua.berest.lab3.controller.processors;

import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorLogOut extends Processor {
    public ProcessorLogOut() {
        actionToPerform = "logOut";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        request.getSession().invalidate();
        return "logOut";
    }
}