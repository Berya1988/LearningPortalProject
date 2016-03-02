<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Learning Portal</title>
    <link href="css/template.css" rel="stylesheet" type="text/css">
</head>
    <body>

    <div class="wrapper">

        <header class="header">
            <div class="logo">
                <img src="images/portal.jpg" alt="Логотип"  width="130" height="130" name="logo" />
            </div>
            <div class="descr">
                <h1>Всесвітній учбовий онлайн-портал</h1>
            </div>

            <div class="welcome">
                Ласкаво просимо, <%=request.getSession().getAttribute("username")%> !<br/>
                <a href="DispatcherServlet?action=logOut">Вихід</a>
            </div>
        </header>

        <div class="middle">

            <div class="container">
                <main class="content">

                    <table border="1" width="100%" cellpadding="5" bgcolor="#ffcc00">
                        <tr>
                            <th id="col1" >Студенти: всі</th>
                            <th id="col2">
                                <button>NEW</button><span>   </span><button>EDIT</button><span>   </span><button>DELETE</button>
                            </th>
                        </tr>
                    </table>

                    <p>
                    <form method="post" action="input5.php">
                        <input type="checkbox" name="option1" value="a1">Іванов Д.<br/>
                        <input type="checkbox" name="option2" value="a2">Петров Д.<br/>
                        <input type="checkbox" name="option3" value="a3">Сидоров Д.<br/>
                        <input type="checkbox" name="option4" value="a4">Ткачов Д.<br/>
                    </form>
                    </p>

                    <div>
                        <% List<String> list =  (List<String>)request.getSession().getAttribute("namesOfAllProcessors"); %>
                        <% String includedJSPName = (String)request.getSession().getAttribute("includedJSPName"); %>
                        <p>Name of processor:</p>
                        <% if (list != null){ %>
                        <ul>
                            <% for (String name:list) { %>
                            <li><%= name%></li>
                            <% } %>
                        </ul>
                        <% } %>
                        <% if (includedJSPName != null){
                            if(includedJSPName.equals("showAllStudents")) { %>
                        <p>
                            <%@include file = "showAllStudents.jsp"%>
                        </p>
                        <%  } else if (includedJSPName.equals("showAllCountries")) { %>
                        <p>
                            <%@include file = "showAllCountries.jsp"%>
                        </p>
                        <% }
                        }   %>
                    </div>
                </main>
            </div>

            <aside class="left-sidebar">
                    <a href = "DispatcherServlet?action=showAllStudents"><p class="button">Студенти</p></a>
                <a href = "DispatcherServlet?action=showAllCountries"><p class="button">Країни</p></a>
            </aside>

        </div>

    </div>

    <footer class="footer">
        Розробник: Берест Олег, 2016 р.
    </footer>


    </body>
</html>