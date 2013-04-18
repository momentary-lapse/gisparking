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
            #imdiv {height: 100px}
            #content {max-height: 550px; max-width: 550px}
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
            var marker;
            var content;
            var contents = [];
            var lat;
            var lng;
            var tolat;
            var tolng;
            var me;
            var directionsService;
            var directionsDisplay;
            var image;
            var images = [];
            var shadow;
            var shadowdot;
            
            function initialize() {
                
                lat = ${lat};
                lng = ${lng};
                tolat = 55.1617;
                tolng = 61.401;
                
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
                    disableAutoPan: false
                });
                
                
                map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
                
                
                image = 'https://maps.gstatic.com/mapfiles/ms2/micons/bus.png';
                images[0] = 'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png';
                images[1] = 'https://maps.gstatic.com/mapfiles/ms2/micons/yellow-dot.png';
                images[2] = 'https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png';
                
                shadow = {
                    url: 'https://maps.gstatic.com/mapfiles/ms2/micons/bus.shadow.png',
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(16, 32)
                };
                
                shadowdot = {
                    url: 'https://maps.gstatic.com/mapfiles/ms2/micons/msmarker.shadow.png',
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(16, 32)
                };
                
                
                var i = 0;
                
            <c:if test="${empty marker}">
                <c:forEach var="m" items="${markers}">
                    
                        var im = images[${m.cluster} - 1];
                        markers[i] = new google.maps.Marker({
                            position: new google.maps.LatLng(${m.lat}, ${m.lng}),
                            map: map,
                            icon: im,
                            shadow: shadowdot
                        });
                        
                        bounds.extend(markers[i].position);
                    
                        contents[i] = '<div id="content">${m.address}<br /><div id="imdiv"><img src="/GISParking/images/${m.id}" height="100px" /></div><br /><a href="/GISParking/choose/${m.id}">Принять заявку</a><br /><a href="/GISParking/complain/${m.id}">Удалить и пожаловаться</a><br /><a href="#" onclick="interface.getMarker(${m.id})">Подробнее...</a></div>';
                        google.maps.event.addListener(markers[i], 'click', (function(i) {
                            return function() {
                                infowindow.setContent(contents[i]);
                                map.panTo(markers[i].position);
                                map.setZoom(15);
                                infowindow.open(map, markers[i]);
                            }
                        })(i));
                    
                        i++;
                </c:forEach>
                
                        me = new google.maps.Marker({
                            position: new google.maps.LatLng(lat, lng),
                            icon: image,
                            shadow: shadow,
                            map: map
                        });
                
                        bounds.extend(me.position);
                        
                        map.fitBounds(bounds);
                
            </c:if>
                
            
                
            <c:if test="${not empty marker}">
                    
                    tolat = ${marker.lat};
                    tolng = ${marker.lng};
                    content = '<div id="content">${marker.address}<br /><div id="imdiv"><img src="/GISParking/images/${marker.id}" height="100px" /></div><br /><a href="/GISParking/cancel/${marker.id}">Отменить заявку</a><br /><a href="/GISParking/delete/${marker.id}">Удалить заявку</a></div>';
                    infowindow.setContent(content);  
                    
                    showDirections();
                    
                    

            </c:if>
                
                    
                
                }
                
                function showDirections() {
                    
                    var a = new google.maps.LatLng(lat, lng);
                    var b = new google.maps.LatLng(tolat, tolng);
                    
                    var request = {
                        origin: a,
                        destination: b,
                        travelMode: google.maps.TravelMode.DRIVING
                    };
                    
                    directionsDisplay = new google.maps.DirectionsRenderer({
                        map: map,
                        preserveViewport: true,
                        draggable: false,
                        suppressMarkers: true
                    });
  
                    
                    directionsService = new google.maps.DirectionsService();
                    directionsService.route(request, drawRoute);
                    
                    
                }
                
                
                function drawRoute(response, status) {
                        
                    if (status == google.maps.DirectionsStatus.OK) {
                        
                        var leg = response.routes[0].legs[0];
                        
                        map.fitBounds(response.routes[0].bounds);
                        
                        
                        me = new google.maps.Marker({
                            position: leg.start_location,
                            icon: image,
                            shadow: shadow,
                            map: map
                        });
                        marker = new google.maps.Marker({
                            position: leg.end_location,
                            map: map
                        });
                            
                        google.maps.event.addListener(marker, 'click', function() {
                            map.panTo(marker.position);
                            map.setZoom(15);
                            infowindow.open(map, marker);
                        });
                        
                        directionsDisplay.setDirections(response);
                    }
                        
                }
                
        </script>
    </head>
    <body onload="initialize()">
        <div id="map_canvas" style="height: 100%; width: 100%"></div>
    </body>
</html>
