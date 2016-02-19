package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 18.02.2016.
 */
public class ProcessorShowAllStudents extends Processor {
    public ProcessorShowAllStudents() {
        actionToPerform = "showAllStudents";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        List<Student> listOfAllStudents = new ArrayList<Student>();
        listOfAllStudents = OracleDataAccess.getInstance().getAllStudents();
        request.getSession().setAttribute("listOfAllStudents", listOfAllStudents);
        return "template";
    }
}
