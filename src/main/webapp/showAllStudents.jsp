<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 18.02.2016
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LearningPortal</title>
</head>
<body>
<div>Here will be the list of all students:</div>
<ul>
    <% List<Student> listOfAllStudents = new ArrayList<Student>(); %>
    <% listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
    <% for (Student student:listOfAllStudents) {       %>
            <li><%= student.getFio()%></li>
    <% } %>
</ul>
</body>
</html>
