<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Отправка</title>
        
        <!-- <script type="text/javascript" src="http://panzi.github.com/Browser-Ponies/basecfg.js" id="browser-ponies-config"></script><script type="text/javascript" src="http://panzi.github.com/Browser-Ponies/browserponies.js" id="browser-ponies-script"></script><script type="text/javascript">/* <![CDATA[ */ (function (cfg) {BrowserPonies.setBaseUrl(cfg.baseurl);BrowserPonies.loadConfig(BrowserPoniesBaseConfig);BrowserPonies.loadConfig(cfg);})({"baseurl":"http://panzi.github.com/Browser-Ponies/","fadeDuration":500,"volume":1,"fps":25,"speed":3,"audioEnabled":false,"showFps":false,"showLoadProgress":true,"speakProbability":0.1,"spawn":{"applejack":1,"fluttershy":1,"pinkie pie":1,"rainbow dash":1,"rarity":1,"twilight sparkle":1},"autostart":true}); /* ]]> */</script> -->
        
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
