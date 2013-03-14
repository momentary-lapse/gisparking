<%-- 
    Document   : list
    Created on : 14.03.2013, 10:24:11
    Author     : anton
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    ${fn:length(list)}
    <br />
    <c:forEach var="marker" items="${list}">
            ${marker.id} ${marker.north} ${marker.east} ${marker.info}
            <br />
    </c:forEach>
</html>
