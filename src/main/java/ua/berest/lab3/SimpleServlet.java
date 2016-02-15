package ua.berest.lab3;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.exception.ProblemWithConnectionException;
import ua.berest.lab3.model.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Oleg on 24.01.2016.
 */
public class SimpleServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        List<Student> listOfAllStudents = new ArrayList<Student>();

        response.setStatus(200);
        OracleDataAccess test = new OracleDataAccess();
        try {
            try {
                //test.updateStudent(new StudentImpl(32, "Ivanov", "sdf", "dsf", "sdf", "sdf"));
                listOfAllStudents = test.getAllStudents();
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        } catch (ProblemWithConnectionException e) {
            e.printStackTrace();
        }

        for (Student student:listOfAllStudents) {
            sb.append("  <li>" + student.getFio() + "</li>");
        }

        try {
            out.println("<html>"
                    + "<head>"
                    + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
                    + "<title>Ukrainian Learning Portal</title>"
                    + "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">"
                    + "</head>"
                    + "<body>"
                    + "<div class=\"main\">"
                    + "<div class=\"abzac\">Learning Portal</div>"
                    + "<div class=\"content\">"
                    + "<div class=\"small_column\">"
                    + "<img src=\"images/java.png\" alt=\"\" width=\"119\" height=\"222\">"
                    + "</div>"
                    + "<div class=\"big_column\">"
                    + "<div \">"
                    + "<table>"
                    + "<tr>"
                    + " <th>Size of student list: " + listOfAllStudents.size()  + "</th>"
                    + "  <th>EDIT ADD DELETE</th>"
                    + "</tr>"
                    + "    </table>"
                    + "</div>"
                    + "<div>"
                    + "<ul>"
                    + sb.toString()
                    + "</ul>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "<div class=\"footer\">Berest Oleg, 2016</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>");
        } finally {
            out.close();
        }
    }
}
