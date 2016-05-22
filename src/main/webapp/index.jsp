<%-- 
    Document   : index
    Created on : May 13, 2016, 5:18:10 PM
    Author     : Element
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Collect'IF</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
        <link rel="stylesheet" href="CollectifCSS.css"/>
    </head>
    <body>
        <%@ include file="navbar.html" %> 
    
        <div class="jumbotron">
            <div class='jumbotron text-center'>
                <h1>Collect'IF</h1>
                

                    <div class="text-box-custom">
                        <p>Vous souhaitez faire une partie de tarot jeudi prochain ? Un match de volley le samedi de la semaine prochaine ?</p>
                        <p>Collect'IF, l'association événementielle locale, vous trouve des co-équipiers et met à votre disposition un local pour votre rencontre !</p>
                        <p>Rejoignez-nous !</p>                        
                    </div>
 


                <button type="button" class="btn btn-primary">Nous rejoindre !</button>
                <span class="custom-separator"></span>
                <a class="btn btn-primary" href="ActionServlet?action=getPage&page=connexion">Se connecter</a>
            </div>
        </div>
        
    </body>
</html>

