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
            
            var mapOptions;
            var infowindow;
            var bounds;
            var map;
            var markers = [];
            var contents = [];
            var lat;
            var lng;
            var me;
            
            function initialize() {
                
                lat = 55.1617;
                lng = 61.401;
                
                if (window.android) {
                    lat = window.android.getLatitude();
                    lng = window.android.getLongitude();
                }
                
                bounds = new google.maps.LatLngBounds();
           
                mapOptions = {
                    center: new google.maps.LatLng(55.1667, 61.4000),
                    zoom: 12,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                
                infowindow = new google.maps.InfoWindow({
                    content: ''
                });
                
                
                map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
                
                var image = 'https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png';
                var shadow = {
                    url: 'https://maps.gstatic.com/mapfiles/ms2/micons/msmarker.shadow.png',
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(16, 32)
                };
                me = new google.maps.Marker({
                    position: new google.maps.LatLng(lat, lng),
                    icon: image,
                    shadow: shadow,
                    map: map
                });
                
                bounds.extend(me.position);
                
                var i = 0;
                
            <c:if test="${empty marker}">
                <c:forEach var="m" items="${markers}">
                        markers[i] = new google.maps.Marker({
                            position: new google.maps.LatLng(${m.north}, ${m.east}),
                            map: map
                        });
                        
                        bounds.extend(markers[i].position);
                    
                        contents[i] = '<img src="/GISParking/images/${m.id}" height="200" />';
                        google.maps.event.addListener(markers[i], 'click', (function(i) {
                            return function() {
                                infowindow.setContent(contents[i]);
                                map.setCenter(markers[i].position);
                                map.setZoom(15);
                                infowindow.open(map, markers[i]);
                            }
                        })(i));
                    
                        i++;
                </c:forEach>
            </c:if>
                
            
                
            <c:if test="${not empty marker}">
                    var marker  = new google.maps.Marker({
                        position: new google.maps.LatLng(${marker.north}, ${marker.east}),
                        map: map
                                
                    });
                    var content = '<img src="/GISParking/images/${marker.id}" height="200" />';
                    infowindow.setContent(content);
                    google.maps.event.addListener(marker, 'click', function() {
                        infowindow.open(map, marker)
                    });
                    
                    bounds.extend(marker.position);

            </c:if>
                
                    map.fitBounds(bounds);
                
                }
        </script>
    </head>
    <body onload="initialize()">
        <div id="map_canvas" style="height: 100%; width: 100%"></div>
    </body>
</html>