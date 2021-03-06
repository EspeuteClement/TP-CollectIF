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
        <h3 align="center">Les évènements:</h3>
        <br/>
        <div id='liste'>
            <table align='center'><tr><td>chargement en cours...</td><tr></table>
        </div>
        </div>
        <br/>
        <br/>
        <br/>
        
        <script>
            $(function() {
                $.ajax({
                    url: './ActionServlet',
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
                        contenuHtml += evenements[i].activite +" - " + evenements[i].tempsE + '<br/>';
                        contenuHtml += '<ul><li>Lieu: '+ evenements[i].lieu + '</li>';
                        if(evenements[i].equipe)
                        {
                            contenuHtml += '<li>Equipe A: '+ evenements[i].equipeA + '</li>';
                            contenuHtml += '<li>Equipe B: '+ evenements[i].equipeB + '</li>';
                        }
                        else
                        {
                            contenuHtml += '<li>Participants: '+ evenements[i].participants + '</li>';
                        }
                        contenuHtml+= '</ul>';
                        if(evenements[i].avoirLieu === false)
                        {
                            contenuHtml += '<div align="center"><a class="btn btn-primary" href="affecterlieu.jsp?id=' + evenements[i].id + '"> Affecter un lieu</a></div>';
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
