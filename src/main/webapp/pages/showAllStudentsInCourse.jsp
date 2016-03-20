<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.berest.lab3.model.Location" %>
<%@ page import="ua.berest.lab3.model.Course" %>
<%@ page errorPage="errorPage.jsp"%>


<% Location location = (Location)request.getSession().getAttribute("location"); %>
<% Course course = (Course) request.getSession().getAttribute("course"); %>
<form method="POST">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" >Студенти кафедри <a href="<%="DispatcherServlet?action=showAllLocations&parentId=" + location.getLocationId()%>"><%= location.getName()%></a> : курс <%= course.getName()%></th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" formaction="DispatcherServlet?action=showFormAddStudent" >NEW</button>
                    <button type="submit" formaction="DispatcherServlet?action=showFormEditStudent" onclick="return validateForm2()">EDIT</button>
                    <button type="submit" formaction="DispatcherServlet?action=deleteStudent" onclick="return validateForm2()">DELETE</button>
                </div>
            </th>
        </tr>
    </table>

    <% List<Student> listOfAllStudentsInCourse = (List<Student>) request.getSession().getAttribute("listOfAllStudentsInCourse"); %>
    <p>
            <% for (int i = 0; i < listOfAllStudentsInCourse.size(); i++) { %>
             <input type="checkbox" name ="students"  value="<%= listOfAllStudentsInCourse.get(i).getStudentId() %>"><a href="<%= "DispatcherServlet?action=showStudentCourses&studentId=" + listOfAllStudentsInCourse.get(i).getStudentId() %>"><%= listOfAllStudentsInCourse.get(i).getFio()%></a><br/>
            <% } %>
    </p>
</form>