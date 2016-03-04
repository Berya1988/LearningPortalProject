<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/form.css" rel="stylesheet" type="text/css">

<form id="myForm" method="POST" action="DispatcherServlet?action=addNewStudent">
    <fieldset>
        <legend><b>СТУДЕНТ:ЗАРЕЄСТРУВАТИ НОВОГО</b></legend>
        <div>
            <label for="userNameInput" >Прізвище та ім'я по-батькові:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="userName" id="userNameInput" placeholder="Введіть прізвище та ім'я по-батькові: Іванов Іван Іванович" />
            </div>
        </div>

        <div>
            <label for="groupInput" >Група:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="groupName"  id="groupInput" placeholder="Введіть групу: СУ-51" />
            </div>
        </div>

        <div>
            <label for="mailInput" >Пошта:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="mailName" id="mailInput"  placeholder="Введіть e-mail: example@mail.ua" />
            </div>
        </div>

        <div>
            <label for="phoneInput" >Телефон:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="phoneName" id="phoneInput" placeholder="Введіть номер мобльного телефону: +38(066)66-66-666" />
            </div>
        </div>

        <div>
            <label for="addressInput" >Адреса:</label>
            <div class="col-lg-9">
                <input type="text" class="form-control" name="addressName" id="addressInput" placeholder="Введіть домашню адресу: м.Суми, вул.Заливна 7, кв.20" />
            </div>
        </div>
        <div>
            <button class="btn btn-default" type="submit" formaction="DispatcherServlet?action=showAllStudents">Відміна</button>
            <button class="btn btn-primary" type="submit">Підтвердити</button>
        </div>
    </fieldset>
</form>

