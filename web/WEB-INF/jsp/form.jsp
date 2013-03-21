<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
        ${trace}
        </div>
        <form action="form" method="post" enctype="multipart/form-data" >
            <input type="file" name="image" />
            <input type="submit" value="Upload" />
        </form>
    </body>
</html>
