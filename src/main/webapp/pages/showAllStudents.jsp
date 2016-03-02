<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>

<div>Here will be the list of all students:</div>
<ul>
    <% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
    <% for (Student student:listOfAllStudents) {       %>
            <li><%= student.getFio()%></li>
    <% } %>
</ul>

