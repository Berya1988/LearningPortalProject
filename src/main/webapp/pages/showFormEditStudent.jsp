<%@ page import="ua.berest.lab3.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/form.css" rel="stylesheet" type="text/css">

<% List<Student> listOfAllStudents = (List<Student>) request.getSession().getAttribute("listOfAllStudents"); %>
<% int index = Integer.parseInt((String) request.getSession().getAttribute("index")); %>
<% Student student = listOfAllStudents.get(index); %>
<form id="myForm" method="POST" action="DispatcherServlet?action=editStudent">
    <fieldset>
        <legend><b>СТУДЕНТ:РЕДАГУВАТИ</b></legend>
        <input type="hidden" name="idName" value="<%= student.getStudentId()%>" />
        <div>
            <label for="userNameInput" >Прізвище та ім'я по-батькові:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="userName" id="userNameInput" value="<%= student.getFio()%>" placeholder="Введіть прізвище та ім'я по-батькові: Іванов Іван Іванович" />
            </div>
        </div>

        <div>
            <label for="groupInput" >Група:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="groupName"  value="<%= student.getGroup()%>" id="groupInput" placeholder="Введіть групу: СУ-51" />
            </div>
        </div>

        <div>
            <label for="mailInput" >Пошта:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="mailName" id="mailInput" value="<%= student.getMail()%>" placeholder="Введіть e-mail: example@mail.ua" />
            </div>
        </div>

        <div>
            <label for="phoneInput" >Телефон:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="phoneName" id="phoneInput" value="<%= student.getPhone()%>" placeholder="Введіть номер мобльного телефону: +38(066)66-66-666" />
            </div>
        </div>

        <div>
            <label for="addressInput" >Адреса:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="addressName" id="addressInput" value="<%= student.getAddress()%>" placeholder="Введіть домашню адресу: м.Суми, вул.Заливна 7, кв.20" />
            </div>
        </div>
        <div>
            <button class="btn btn-default" type="submit" formaction="DispatcherServlet?action=showAllStudents">Відміна</button>
            <button class="btn btn-primary" type="submit">Підтвердити</button>
        </div>
    </fieldset>
</form>

