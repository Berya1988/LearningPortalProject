<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.berest.lab3.model.Grade" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="ua.berest.lab3.model.Course" %>
<%@ page errorPage="errorPage.jsp"%>

<script>
    function validateForm5() {
        for (var i = 0; i < document.gradeForm.grades.length; i++) {
            if (document.gradeForm.grades[i].checked) {
                return true;
            }
        }
        alert("You should choose at least one item!");
        return false;
    }
</script>

<% Student student = (Student) request.getSession().getAttribute("student"); %>
<% Course course = (Course) request.getSession().getAttribute("course"); %>
<form method="POST" name="gradeForm">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" ><a href="DispatcherServlet?action=showAllStudents">Студенти</a> : <%= student.getFio()%>, <a href="<%= "DispatcherServlet?action=showStudentCourses&studentId=" + student.getStudentId() %>">курси</a> : <%= course.getName()%></th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" formaction="<%= "DispatcherServlet?action=showFormAddGrade&courseId=" + course.getCourseId() + "&studentId=" + student.getStudentId()%>">NEW</button>
                    <button type="submit" formaction="<%= "DispatcherServlet?action=showFormEditGrade&courseId=" + course.getCourseId() + "&studentId=" + student.getStudentId()%>" onclick="return validateForm5()">EDIT</button>
                    <button type="submit" formaction="<%= "DispatcherServlet?action=deleteGrade&courseId=" + course.getCourseId() + "&studentId=" + student.getStudentId()%>" onclick="return validateForm5()">DELETE</button>
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
                    <th><%= listOfAllGrades.get(i).getDescription()%></th>
                </tr>
            <% } %>
            <tr>
                <th colspan="4">Загальна кількість балів: <%= sum %>/100 </th>
            </tr>
        </table>
    </p>
</form>