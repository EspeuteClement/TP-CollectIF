<%-- 
    Document   : enregistrement
    Created on : May 13, 2016, 5:27:09 PM
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
            <div class='jumbotron text-center'>
                <h1>Collect'IF</h1>
                <h3>Enregistrez vous pour participer à nos évènements !</h3>
                
                <form class="form-horizontal login-custom" role="form">
                    <p id="error-msg" class="danger"> </p>
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="nom">Nom:</label>
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="nom" placeholder="Dupont">
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="prenom">Prénom:</label>
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="prenom" placeholder="Paul">
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="adresse">Adresse:</label>
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="adresse" placeholder="20 avenue albert einstein, 69100, Villeurbanne">
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="control-label col-sm-2" for="mail">Mail:</label>
                      <div class="col-sm-10">
                        <input type="email" class="form-control" id="mail" placeholder="paul.dupont@mail.com">
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <div class="col-sm-offset-2 col-sm-10">
                        <input id="btnConnect" type="button" value="S'inscrite" onClick="btnCreate();" class="btn btn-default"/>
                      </div>
                    </div>
                  </form>
            </div>
        </div>
        
        <script>
            function btnCreate() {

                $.ajax({
                    
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: "create",
                        nom: $("#nom").val(),
                        prenom: $("#prenom").val(),
                        adresse: $("#adresse").val(),
                        mail: $("#mail").val()
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    if (data.sucess)
                    {
                        window.location.replace("connexion.jsp");
                    }
                    else
                    {
                        $("#mail").addClass("error");
                        $("#error-msg").html("Erreur, cette adresse mail est déjà utilisée") ;
                    }

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

