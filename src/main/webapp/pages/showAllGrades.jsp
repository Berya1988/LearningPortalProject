<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.berest.lab3.model.Grade" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="ua.berest.lab3.model.Course" %>
<%@ page errorPage="errorPage.jsp"%>

<% Student student = (Student) request.getSession().getAttribute("student"); %>
<% Course course = (Course) request.getSession().getAttribute("course"); %>
<form method="POST">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" ><a href="DispatcherServlet?action=showAllStudents">Студенти</a> : <%= student.getFio()%>, <a href="<%= "DispatcherServlet?action=showStudentCourses&studentId=" + student.getStudentId() %>">курси</a> : <%= course.getName()%></th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" formaction="DispatcherServlet?action=">NEW</button>
                    <button type="submit" formaction="DispatcherServlet?action=">EDIT</button>
                    <button type="submit" formaction="DispatcherServlet?action=">DELETE</button>
                </div>
            </th>
        </tr>
    </table>

    <% List<Grade> listOfAllGrades = (List<Grade>) request.getSession().getAttribute("listOfAllGrades"); %>
    <p>
        <table border="1" width="100%" cellpadding="5" bgcolor="#87CEEB">
            <% float sum = 0; %>
            <% for (int i = 0; i < listOfAllGrades.size(); i++) { %>
                <% sum += listOfAllGrades.get(i).getCredits(); %>
                <tr>
                    <th><input type="checkbox" name ="grades"  value="<%= listOfAllGrades.get(i).getGradeId() %>"></th>
                    <th><%= listOfAllGrades.get(i).getCredits()%></th>
                    <th><%= listOfAllGrades.get(i).getDate()%></th>
                </tr>
            <% } %>
            <tr>
                <th colspan="3">Загальна кількість балів: <%= sum %>/100 </th>
            </tr>
        </table>
    </p>
</form>