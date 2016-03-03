<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>

<table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
    <tr>
        <th id="col1" >Студенти: всі</th>
        <th id="col2">
            <form style="float:right" action="DispatcherServlet?action=showFormAddNewStudent" method="POST">
                <input type="submit" value="NEW" />
            </form>
            <span>   </span>
            <form style="float:right" action="DispatcherServlet?action=editStudent" method="POST">
                <input type="submit" value="EDIT" />
            </form>
            <span>   </span>
            <form style="float:right" action="DispatcherServlet?action=deleteStudent" method="POST">
                <input type="submit" value="DELETE" />
            </form>
        </th>
    </tr>
</table>

<% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
<% int i = 0; %>
<p>
    <form method="POST">
        <% for (Student student:listOfAllStudents) { %>
         <input type="checkbox" name="<%= "option"+i %>" value="<%= "a"+i %>"><%= student.getFio()%><br/>
        <% } %>
    </form>
</p>