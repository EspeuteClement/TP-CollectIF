<%-- 
    Document   : connexion
    Created on : May 13, 2016, 5:25:58 PM
    Author     : Element
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
            <div class="container text-center">
                <h1>Collect'IF</h1>
                
                
                <div class="form-horizontal login-custom" role="form" action="ActionServlet" method="post">
                    <p id="error-msg" class="text-danger text-center"> </p>
                    <input type="hidden" name="action" value="connect" />
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="email">Email:</label>
                      <div class="col-sm-10">
                        <input type="email" class="form-control" id="cnct-mail" name="email" placeholder="Mail">
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="id">Numéro d'adhérent:</label>
                      <div class="col-sm-10">
                        <input type="number" class="form-control" id="id" name="id" placeholder="1234" 
                               onkeydown = "if (event.keyCode == 13)
                               document.getElementById('btnConnect').click()"    >
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                          <label><input type="checkbox"> Remember me</label>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-sm-offset-2 col-sm-10">
                          <input id="btnConnect" type="button" value="Connection" onClick="btnConnect();" class="btn btn-default"/>
                        <!--<button type="submit" class="btn btn-default">Connexion</button>-->
                      </div>
                    </div>
                  </div>
            </div>
        </div>
        
        <script>
            function btnConnect() {

                $.ajax({
                    
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: "connect",
                        email: $("#cnct-mail").val(),
                        id: $("#id").val()
                        
                    },
                    dataType: 'json'
                })
                .done(function(data) {


                })
                .fail(function() {
                    $('#listeActivites').html('ERREUR de chargement');
                })
                .always(function() {
                    //
                });

            }
            
        </script>
        
    </body>
</html>

