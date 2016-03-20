package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorEditGrade extends Processor {
    public ProcessorEditGrade() {
        actionToPerform = "editGrade";
    }
    public ProcessorResult process(HttpServletRequest request) throws DataAccessException {
        Integer id = Integer.valueOf(request.getParameter("idName"));
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Integer studentId = Integer.valueOf(request.getParameter("studentId"));
        Integer currentGrade = Integer.valueOf(request.getParameter("currentGrade"));
        Date date = new Date(System.currentTimeMillis());
        String description = request.getParameter("descriptionName");

        Grade grade = new GradeImpl(id, courseId, studentId, date, currentGrade, description);
        OracleDataAccess.getInstance().updateGrade(grade);

        return new ProcessorResult(("?action=showAllGrades&courseId=" + courseId + "&studentId=" + studentId), "showAllGrades.jsp", false);
    }
}
