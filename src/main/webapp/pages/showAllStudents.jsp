<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>

<form method="POST">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" >Студенти: всі</th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" formaction="DispatcherServlet?action=showFormAddStudent">NEW</button>
                    <button type="submit" formaction="DispatcherServlet?action=showFormEditStudent">EDIT</button>
                    <button type="submit" formaction="DispatcherServlet?action=deleteStudent">DELETE</button>
                </div>
            </th>
        </tr>
    </table>

    <% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
    <p>
            <% for (int i = 0; i < listOfAllStudents.size(); i++) { %>
             <input type="checkbox" name ="students"  value="<%= listOfAllStudents.get(i).getStudentId() %>"><a href="<%= "DispatcherServlet?action=showStudentCourses&studentId=" + listOfAllStudents.get(i).getStudentId() %>"><%= listOfAllStudents.get(i).getFio()%></a>><br/>
            <% } %>
    </p>
</form>