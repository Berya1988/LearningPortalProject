package ua.berest.lab3.controller.processors;

import ua.berest.lab3.controller.OracleDataAccess;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.Student;
import ua.berest.lab3.model.StudentImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Oleg on 02.03.2016.
 */
public class ProcessorAddNewStudent extends Processor {
    public ProcessorAddNewStudent() {
        actionToPerform = "addNewStudent";
    }
    public String process(HttpServletRequest request) throws DataAccessException {
        String lastName = request.getParameter("userName");
        String group = request.getParameter("groupName");
        String mail = request.getParameter("mailName");
        String phone = request.getParameter("phoneName");
        String address = request.getParameter("addressName");
        Student student = new StudentImpl(1, lastName, group, mail, phone, address);
        System.out.println("FIO: " + student.getFio());
        System.out.println("Group: " + student.getGroup());
        System.out.println("Mail: " + student.getMail());
        System.out.println("Phone: " + student.getPhone());
        System.out.println("Address: " + student.getAddress());
        OracleDataAccess.getInstance().addStudent(student);
        return "showAllStudents";
    }
}
