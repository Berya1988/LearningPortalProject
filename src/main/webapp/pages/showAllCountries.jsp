<%@ page import="ua.berest.lab3.model.Location" %>
<%@ page import="java.util.List" %>

<%@ page import="ua.berest.lab3.model.Location" %>

<div>Here will be the list of all countries:</div>
<ul>
    <% List<Location> listOfAllCountries = (List<Location>) request.getSession().getAttribute("listOfAllCountries"); %>
    <% for (Location location:listOfAllCountries) { %>
    <li><%= location.getName()%></li>
    <% } %>
</ul>