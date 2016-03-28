package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.controller.PaginationController;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.ProcessorResult;
import ua.berest.lab3.model.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Oleg on 18.02.2016.
 */
public class ProcessorShowAllStudents extends Processor {
    public ProcessorShowAllStudents() {
        actionToPerform = "showAllStudents";
    }
    public ProcessorResult process(HttpServletRequest request) throws DataAccessException {
        int page = Integer.parseInt(request.getParameter("page"));
        int studentsCount = OracleDataAccess.getInstance().getTotalCountOfStudents();
        int studentsPerPage = 5;
        PaginationController paginationController = new PaginationController(studentsCount, studentsPerPage, page);
        request.getSession().setAttribute("paginationController", paginationController);
        List<Student> listOfAllStudents;
        if(studentsCount <= studentsPerPage) {
            listOfAllStudents = OracleDataAccess.getInstance().getAllStudents();
        } else {
            listOfAllStudents = OracleDataAccess.getInstance().getAllStudentsByPage(page, studentsPerPage);
        }
        System.out.println(listOfAllStudents.size());
        request.getSession().setAttribute("listOfAllStudents", listOfAllStudents);
        return new ProcessorResult("pages/template.jsp", "showAllStudents.jsp", true);
    }
}
