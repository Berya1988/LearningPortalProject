package ua.berest.lab3.controller.processors;

import ua.berest.lab3.exception.DataAccessException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 03.03.2016.
 */
public class ProcessorShowFormEditStudent extends Processor {
    public ProcessorShowFormEditStudent() {
        actionToPerform = "showFormEditStudent";
    }
    public String process(HttpServletRequest request) throws DataAccessException {

        String[] students = request.getParameterValues("students");
        System.out.println( "Edited student: with index" + students[0]);
        /*if (students != null) {
            for (int i = 0; i < students.length; i++)
            {
                System.out.println (students[i]);
            }
        }
        else
            System.out.println ("none");*/

        request.getSession().setAttribute("index", students[0]);
        return "showFormEditStudent";
    }
}