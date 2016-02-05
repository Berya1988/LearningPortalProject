package ua.berest.lab3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Oleg on 24.01.2016.
 */
public class SimpleServlet extends javax.servlet.http.HttpServlet {
    private String responseTemplate;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Student> listOfAllStudents = new ArrayList<Student>();


        response.setStatus(200);
        OracleDataAccess test = new OracleDataAccess();
        test.updateStudentNameById(1, "Kozlov");
        //test.removeStudentById(32);
        //test.addStudent(32, "Beresta", "SU-21", "Berest_Oleg@mail.ru", "380665673221", "20 Lushpu str.");
        listOfAllStudents = test.getAllStudents();

        responseTemplate =
            "<html>\n" +
                    "<body>\n" +
                    "<h2>Hello from Simple Servlet 5</h2>\n" +
                    "<p>Size of student list: " + listOfAllStudents.size()  + "</p>" +
                    "</body>\n" +
                    "</html>";
        response.getWriter().write(responseTemplate);
    }
}
