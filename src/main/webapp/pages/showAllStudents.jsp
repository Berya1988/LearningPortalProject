<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>

<table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
    <tr>
        <th id="col1" >Студенти: всі</th>
        <th id="col2">
            <button>NEW</button><span>   </span><button>EDIT</button><span>   </span><button>DELETE</button>
        </th>
    </tr>
</table>

<% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
<% int i = 0; %>
<p>
    <form method="post" action="input5.php">
        <% for (Student student:listOfAllStudents) { %>
         <input type="checkbox" name="<%= "option"+i %>" value="<%= "a"+i %>"><%= student.getFio()%><br/>
        <% } %>
    </form>
</p>