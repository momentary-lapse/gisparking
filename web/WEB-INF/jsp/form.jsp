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
            Широта:
            <input type="text" name="lat" />
            <br/>
            Долгота:
            <input type="text" name="lng" />
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
