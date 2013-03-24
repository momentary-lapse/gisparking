<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>${fn:length(list)}
    <br />
    <c:forEach var="marker" items="${list}">
            ${marker.id} ${marker.north} ${marker.east} ${marker.phone} ${marker.url}
            <br />
    </c:forEach>
</html>
