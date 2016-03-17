<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Location" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<% Map<Integer,String> mapOfAllCountries = (Map<Integer,String>) request.getSession().getAttribute("mapOfAllLocations"); %>
<form name="locationForm" method="POST">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" >
                <% for (Map.Entry<Integer, String> entry : mapOfAllCountries.entrySet()) { %>
                <a href="<%= "DispatcherServlet?action=showAllLocations&parentId=" + entry.getKey() %>"><%= entry.getValue() %></a> :
                <% } %>
            </th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" >NEW</button>
                    <button type="submit" >EDIT</button>
                    <button type="submit" >DELETE</button>
                </div>
            </th>
        </tr>
    </table>

    <% List<Location> listOfAllCountries = (List<Location>) request.getSession().getAttribute("listOfAllLocations"); %>
    <p>
        <% for (int i = 0; i < listOfAllCountries.size(); i++) { %>
        <input type="checkbox" name ="locations"  value="<%= listOfAllCountries.get(i).getLocationId() %>"><a href="<%= "DispatcherServlet?action=showAllLocations&parentId=" + listOfAllCountries.get(i).getLocationId() %>"><%= listOfAllCountries.get(i).getName()%></a><br/>
        <% } %>
    </p>
</form>