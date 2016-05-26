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
        <div class='container margins'>
        <h1 align="center">Collect'IF</h1>
        <br/>
        <h3 align="center">Mes demandes:</h3>
        <br/>
        <div id='liste'>
            <table align='center'><tr><td>chargement en cours...</td><tr></table>
        </div>
        <br/>
        
        <div align="center">
            <a class="btn btn-primary" href="posterDemande.jsp">Poster une demande</a>  
        </div>
        
        </div>
        
        <script>
                var QueryString = function () {
                // This function is anonymous, is executed immediately and 
                // the return value is assigned to QueryString!
                var query_string = {};
                var query = window.location.search.substring(1);
                var vars = query.split("&");
                for (var i=0;i<vars.length;i++) {
                  var pair = vars[i].split("=");
                      // If first entry with this name
                  if (typeof query_string[pair[0]] === "undefined") {
                    query_string[pair[0]] = decodeURIComponent(pair[1]);
                      // If second entry with this name
                  } else if (typeof query_string[pair[0]] === "string") {
                    var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
                    query_string[pair[0]] = arr;
                      // If third or later entry with this name
                  } else {
                    query_string[pair[0]].push(decodeURIComponent(pair[1]));
                  }
                } 
                  return query_string;
              }();
            $(function() {
                $.ajax({
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: 'getAdherentDemandes',
                        id: QueryString.id
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    var demandes = data.demandes;
                    var contenuHtml = '<table align="center" width="600" border="2">';
                    var i;
                    for (i = 0; i < demandes.length; i++) {
                        contenuHtml += '<tr><td>';
                        contenuHtml += demandes[i].activite +" - " + demandes[i].tempsE + '<br/>';
                        contenuHtml += '<ul><li>Lieu: '+ demandes[i].lieu + '</li>';
                        if(demandes[i].equipe)
                        {
                            contenuHtml += '<li>Equipe A: '+ demandes[i].equipeA + '</li>';
                            contenuHtml += '<li>Equipe B: '+ demandes[i].equipeB + '</li>';
                        }
                        else
                        {
                            contenuHtml += '<li>Participants: '+ demandes[i].participants + '</li>';
                        }
                        contenuHtml+= '</ul>';
                        contenuHtml += 'Date de demande: '+ demandes[i].tempsD;
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
