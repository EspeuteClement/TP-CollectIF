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
        <div class='jumbotron'>
        <h1 align="center">Collect'IF</h1>
        <br/>
        <h3 align="center">Poster une demande:</h3>
        <br/>
        <table id='tab' align='center'>
            <tr>
                <td><label class="control-label col-sm-2" for="activites">Activité:</label></td>
                <td>
                    <div id='activites'>
                    <select>
                        <option value="0">chargement en cours...</option>
                    </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td><label class="control-label col-sm-2" for="date">Date:</label></td>
                <td><input maxlength="2" id="jour" placeholder="JJ"/> - <input maxlength="2" id="mois" placeholder="MM"/> - <input maxlength="4" id="annee" placeholder="AAAA"/></td>
            </tr>
            <tr><td>
                <tr><td></td></tr>
            </td></tr>
        </table>
        <br/>
        <div align="center" >
            <input class="btn btn-primary" id="btnPoster" type="button" value="Poster une demande" onClick="btnPoster();" class="btn btn-default"/>
        </div>
    
        
        
        <script>
            $.urlParam = function(name){
                var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                if (results===null){
                   return null;
                }
                else{
                   return results[1] || 0;
                }
            };
            
            
            $(function() {
                $.ajax({
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: 'posterDemande',
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    var activites = data.activites;
                    var contenuHtml = '<select id="choixActivite">';
                    var i;
                    for (i = 0; i < activites.length; i++) {
                        contenuHtml += '<option value="'+ activites[i].id +'">[' +activites[i].id+'] '+ activites[i].activite + '</option>';
                    }
                    contenuHtml +="</select>";
                    $('#activites').html(contenuHtml);
                })
                .fail(function() {
                    $('#tab').html('ERREUR de chargement');
                })
                .always(function() {
                    //
                });

            });
            
            
            function btnPoster() {
                $.ajax({
                    
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: "poster",
                        adherent: $.urlParam("id"),
                        activite: $("#choixActivite").val(),
                        jour: $("#jour").val(),
                        mois: $("#mois").val(),
                        annee: $("#annee").val()
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    if (data.succes.succes)
                    {
                        var replace="histoDemandes.jsp?id="+$.urlParam("id");
                        window.location.replace(replace);
                    }
                    else
                    {
                        $("#tab").html("Erreur de Post") ;
                    }
                })
                .fail(function() {
                    $('#liste').html('ERREUR de Post');
                })
                .always(function() {
                    //
                });
            };
        </script>    
    </body>
</html>
