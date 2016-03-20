<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Помилка</title>
</head>
<body bgcolor="white">
    <h3>
        Вибачте за незручність, виникли проблеми з сервером:
    </h3>
    <p>
        Причиною помилки було: <%= exception %>
    </p>
</body>
</html>