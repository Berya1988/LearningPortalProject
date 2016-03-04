package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Oleg on 03.03.2016.
 */
public class ProcessorDeleteStudent extends Processor {
    public  ProcessorDeleteStudent() {
        actionToPerform = "deleteStudent";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents");
        Student student;

        String[] students = request.getParameterValues("students");
        if (students != null) {
            for (int i = 0; i < students.length; i++)
            {
                student = listOfAllStudents.get(Integer.parseInt(students[i]));
                OracleDataAccess.getInstance().removeStudent(student.getStudentId());
            }
        }

        return "showFormAddNewStudent";
    }
}
