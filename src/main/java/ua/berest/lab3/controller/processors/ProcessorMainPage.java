package ua.berest.lab3.controller.processors;

import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorMainPage extends Processor {
    public ProcessorMainPage() {
        actionToPerform = "mainPage";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        String username = request.getParameter("username");
        System.out.println("User name: " + username);
        request.getSession().setAttribute("username", username);
        return "template";
    }
}
