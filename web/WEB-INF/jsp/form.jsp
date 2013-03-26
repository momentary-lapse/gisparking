<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Отправка</title>
    </head>
    <body>
        <form action="form" method="post" enctype="multipart/form-data" >
            Север:
            <input type="text" name="north" />
            <br/>
            Восток:
            <input type="text" name="east" />
            <br/>
            Телефон:
            <input type="text" name="phone" />
            <br/>
            Фото:
            <input type="file" name="image" />
            <br/>
            <input type="submit" value="Отправить" />
        </form>
    </body>
</html>
