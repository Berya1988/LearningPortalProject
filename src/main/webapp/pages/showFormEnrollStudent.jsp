
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.berest.lab3.model.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="errorPage.jsp"%>

<link href="css/form.css" rel="stylesheet" type="text/css">

<% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
<% Course course = (Course) request.getSession().getAttribute("course"); %>

<form id="myForm" method="POST" action="<%= "DispatcherServlet?action=enrollStudent&courseId=" + course.getCourseId()%>">
    <fieldset>
        <legend><b>ДOДАТИ СТУДЕНТА ДО КУРСУ <%= course.getName().toUpperCase()%></b></legend>

        <div>
            <div class="col-lg-9">
                <label for="enrolledStudent" >Виберіть студента, який буде зарахований:</label>
                <select name="enrolledStudent" id="enrolledStudent">
                    <% for (int i = 0; i < listOfAllStudents.size() ;i++) { %>
                            <option value="<%= listOfAllStudents.get(i).getStudentId()%>"><%= listOfAllStudents.get(i).getFio()%></option>
                    <% } %>
                </select>
            </div>
        </div>

        <div>
            <button class="btn btn-default" type="submit" formaction="<%="DispatcherServlet?action=showAllStudentsInCourse&courseId=" + course.getCourseId() + "&locationId="+ course.getLocationId()%>">Відміна</button>
            <button class="btn btn-primary" type="submit">Підтвердити</button>
        </div>
    </fieldset>
</form>

