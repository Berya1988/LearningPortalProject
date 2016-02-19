<%--
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
<h1>Ukrainian Learning portal</h1>
<div>Some common information</div>
<%--<% String name1 = (String) request.getSession().getAttribute("name"); %>
<p>Name of processor:</p>
<p><%= name1%></p>
--%>
<p>
<%@include file="showAllStudents.jsp"%>
</p>

</body>
</html>