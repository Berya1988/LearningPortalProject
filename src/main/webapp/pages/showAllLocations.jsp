<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ua.berest.lab3.model.Location" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page errorPage="errorPage.jsp"%>

<script>
    function validateForm3() {
        for (var i = 0; i < document.locationForm.locations.length; i++) {
            if (document.locationForm.locations[i].checked) {
                return true;
            }
        }
        alert("You should choose at least one item!");
        return false;
    }
</script>

<% Map<Integer,String> mapOfAllLocations = (Map<Integer,String>) request.getSession().getAttribute("mapOfAllLocationsById"); %>
<form name="locationForm" method="POST">
    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
        <tr>
            <th id="col1" >
                <% for (Map.Entry<Integer, String> entry : mapOfAllLocations.entrySet()) { %>
                    <a href="<%= "DispatcherServlet?action=showAllLocations&parentId=" + entry.getKey() %>"><%= entry.getValue() %></a> :
                <% } %>
            </th>
            <th id="col2">
                <div style="float:right">
                    <button type="submit" formaction="DispatcherServlet?action=showFormAddLocation" >NEW</button>
                    <button type="submit" formaction="DispatcherServlet?action=showFormEditLocation" onclick="return validateForm3()">EDIT</button>
                    <button type="submit" formaction="DispatcherServlet?action=deleteLocation" onclick="return validateForm3()">DELETE</button>
                </div>
            </th>
        </tr>
    </table>

    <% List<Location> listOfAllLocations = (List<Location>) request.getSession().getAttribute("listOfAllLocations"); %>
    <p>
        <% for (int i = 0; i < listOfAllLocations.size(); i++) { %>
                <input type="checkbox" name ="locations"  value="<%= listOfAllLocations.get(i).getLocationId() %>"><a href="<%= "DispatcherServlet?action=showAllLocations&parentId=" + listOfAllLocations.get(i).getLocationId() %>"><%= listOfAllLocations.get(i).getName()%></a><br/>
        <% } %>
    </p>
</form>