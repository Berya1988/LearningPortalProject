<%@ page import="ua.berest.lab3.model.Location" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="errorPage.jsp"%>

<link href="css/form.css" rel="stylesheet" type="text/css">

<% Location location = (Location) request.getSession().getAttribute("location"); %>
<% Map<Integer,String> mapOfAllLocations = (Map<Integer,String>) request.getSession().getAttribute("mapOfAllLocations"); %>

<form id="myForm" method="POST" action="<%= location!=null?"DispatcherServlet?action=editLocation":"DispatcherServlet?action=addLocation"%>">
    <fieldset>
        <legend><b><%= location!=null?"РОЗМІЩЕННЯ:РЕДАГУВАТИ":"РОЗМІЩЕННЯ:СТВОРИТИ"%></b></legend>
        <input type="hidden" name="idName" value="<%= location!=null?location.getLocationId():""%>" />
        <div>
            <label for="locationNameInput" >Назва:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="locationName" id="locationNameInput" value="<%= location!=null?location.getName():""%>" placeholder="Введіть назву: Харківський політехнічний інститут" />
            </div>
        </div>

        <div>
            <label for="descriptionName" >Опис:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="descriptionName"  value="<%= location!=null?location.getDescription():""%>" id="descriptionName" placeholder="Введіть опис: найкращий ВУЗ в Харкові" />
            </div>
        </div>

        <div>
            <div class="col-lg-9">
                <label for="isCourse" >Чи є підрівні?:</label>
                <select name="isCourse" id="isCourse">
                    <option selected disabled>Виберіть один із варіантів</option>
                    <% if(location == null) { %>
                        <option value="true">true</option>
                        <option value="false">false</option>
                    <% } else {%>
                        <% if(location.getCourse().equals("true")) { %>
                            <option selected value="true">true</option>
                            <option value="false">false</option>
                        <% } else { %>
                            <option value="true">true</option>
                            <option selected value="false">false</option>
                        <% }
                    } %>
                </select>
            </div>
        </div>

        <div>
            <div class="col-lg-9">
                <label for="parent" >Виберіть батьківський елемент:</label>
                <select name="parent" id="parent">
                    <option selected disabled>Виберіть один із варіантів</option>
                    <% for (Map.Entry<Integer, String> entry : mapOfAllLocations.entrySet()) { %>
                        <% if(location != null && location.getParentId() == entry.getKey()) { %>
                            <option selected value="<%= entry.getKey()%>"><%= entry.getValue()%></option>
                        <% } else {%>
                            <option value="<%= entry.getKey()%>"><%= entry.getValue()%></option>
                        <% } %>
                    <% } %>
                </select>
            </div>
        </div>

        <div>
            <button class="btn btn-default" type="submit" formaction="DispatcherServlet?action=showAllLocations&parentId=0">Відміна</button>
            <button class="btn btn-primary" type="submit">Підтвердити</button>
        </div>
    </fieldset>
</form>

