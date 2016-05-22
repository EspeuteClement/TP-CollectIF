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
                    <option value="enCharge">chargement en cours...</option>
                    </select>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td><label class="control-label col-sm-2" for="date">Date:</label></td>
                <td><input id="jour" value="JJ"/> - <input id="mois" value="MM"/> - <input id="annee" value="AAAA"/></td>
            </tr>
            <tr><td>
                <table align='center'><tr><td><button type="submit" class="btn btn-default">Poster une demande</button></td></tr></table>
            </td></tr>
        </table>
    
        
        
        <script>
            $(function() {
                $.ajax({
                    url: './ActionServletCopie',
                    type: 'POST',
                    data: {
                        action: 'posterDemande',
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    var activites = data.activites;
                    var contenuHtml = '<select>';
                    var i;
                    for (i = 0; i < activites.length; i++) {
                        contenuHtml += '<option value="'+ activites[i].id +'">' + activites[i].activite + '</option>';
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
        </script>    
    </body>
</html>
