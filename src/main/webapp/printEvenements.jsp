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
        <h3 align="center">Les evenements:</h3>
        <br/>
        <div id='liste'>
            <table align='center'><tr><td>chargement en cours...</td><tr></table>
        </div>
        </div>
        
        <script>
            $(function() {
                $.ajax({
                    url: './ActionServletCopie',
                    type: 'POST',
                    data: {
                        action: 'printEvenements',
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    var evenements = data.evenements;
                    var contenuHtml = '<table align="center" width="600" border="2">';
                    var i;
                    for (i = 0; i < evenements.length; i++) {
                        contenuHtml += '<tr><td>';
                        contenuHtml += evenements[i].activite +" - " + evenements.tempsE + '<br/>';
                        contenuHtml += '<ul><li>Lieu: '+ evenements[i].lieu + '</li>';
                        if(evenements[i].equipe)
                        {
                            contenuHtml += '<li>Equipe A: '+ demandes[i].equipeA + '</li>';
                            contenuHtml += '<li>Equipe B: '+ demandes[i].equipeB + '</li>';
                        }
                        else
                        {
                            contenuHtml += '<li>Participants: '+ demandes[i].participants + '</li>';
                        }
                        contenuHtml+= '</ul>';
                        if(!evenements[i].avoirLieu)
                        {
                            contenuHtml += "<button type="submit" class="btn btn-default">Affecter un lieu</button>";
                        }
                        contenuHtml += '</td></tr>';
                    }
                    contenuHtml += '</table>';
                    $('#liste').html(contenuHtml);
                })
                .fail(function() {
                    $('#liste').html('ERREUR de chargement');
                })
                .always(function() {
                    //
                });

            });
        </script>
        
        
    </body>
</html>
