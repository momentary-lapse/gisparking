<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Добро пожаловать в Челябинск!</title>

        <style type="text/css">
            html {height: 100%}
            body {height: 100%; margin: 0%; padding: 0%}
            #map_canvas {height: 100%}
        </style>
        <script type="text/javascript" 
                src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDbMnY6EFXaDYP_eBBwLFQgZuDS13bTdNc&sensor=true">
        </script>

        <script type="text/javascript">
            
            function initialize() {
                var mapOptions = {
                    center: new google.maps.LatLng(55.1667, 61.4000),
                    zoom: 12,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
                var markers = [];
                var infowindows = [];
                var i = 0;
                
                
                
            <c:forEach var="mark" items="${marks}">
                    markers[i] = new google.maps.Marker({
                        position: new google.maps.LatLng(${mark.north}, ${mark.east}),
                        map: map
                                
                    });
                    
                    infowindows[i] = new google.maps.InfoWindow({
                        content: '${mark.info}'
                    });
                    google.maps.event.addListener(markers[i], 'click', (function(i) {
                        return function() {
                            infowindows[i].open(map, markers[i]);
                        }
                    })(i));
                    
                    i++;
            </c:forEach>
                
                
                }
        </script>
    </head>
    <body onload="initialize()">
        <form action="index.htm" method="POST">
            <input type="text" name="id" />
            <input type="submit" name="Submit" />
        </form>
        <div id="map_canvas" style="height: 100%; width: 100%"></div>
    </body>
</html>