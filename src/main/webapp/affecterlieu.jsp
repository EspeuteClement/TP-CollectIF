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
        
        <style type="text/css">
            #map { height: 100%; }
        </style>
    </head>
    <body>
        <%@ include file="navbar.html" %> 
            <div class='container text-center'>
                <h1>Collect'IF</h1>
                <h3> Affecter un lieu </h3>
                
                <div class="container">
                   <div class="row"> 
                        <div class="event-box col-sm-8">
                        <p id="event-name"> loading ... </p>
                        <p id="event-lieu"></p>
                        <p id="event-equipeA"></p>
                        <p id="event-equipeB"></p>
                        </div>

                        <div class="col-sm-4">
                            <div class="form-group">
                                <label for="choixLieu">Lieu :</label>
                                <select class="form-control" id="choixLieu" onchange="onLieuSelect();">
                                  <option value="undefined">Chargement des lieux en cours ...</option>
                                </select>
                            </div>
                            
                            <input id="btnConnect" type="button" value="Affecter le lieu" onClick="btnAffecter();" class="btn btn-default"/>
                        </div>
                    </div> 
                    
                    
                    
                    <!--<div class="row top-buffer">
                      <div class="col-sm-12">
                          
                       
                      </div>
                    </div> -->
                </div>
                
                <div class="top-buffer">
                    <div id="map" class="map" style="height:500px;"></div>
                </div>
                
                

            </div>
        
        <script>
            
            
            ///////////////// CHARGEMENT DES DONNES / ENREGITREMENT DE L'AUTRE MACHIN ////////////////
            
            $.urlParam = function(name){
                var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                if (results==null){
                   return null;
                }
                else{
                   return results[1] || 0;
                }
            };
            var _data;
            
            // Page load
            $(function() {
                $.ajax({
                    
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: "getDataAffecterLieu",
                        id: $.urlParam("id")
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    _data = data;
                    // Charger la liste des lieux
                    var htmlData = "";
            
                    for (var i = 0; i<data.lieux.length; i++){
                        var lieu = data.lieux[i]; 
                        htmlData = htmlData + '<option value="' + lieu.id + '">';
                        htmlData = htmlData + lieu.nom;
                        htmlData = htmlData + '</option>\n';
                    }
                    $("#choixLieu").html(htmlData);
                    
                    // Charger les infos sur l'event
                    
                    $("#event-name").html("Evenement : " + data.event.nom + " le " + data.event.date);
                    $("#event-lieu").html("Lieu : " + data.event.lieu);
                    

                    var htmlEquipes = new Array(data.event.equipes.length);
                    for (var indexEquipe = 0; indexEquipe < data.event.equipes.length; indexEquipe ++)
                    {
                        htmlEquipes[indexEquipe] = "";
                        for (var indexAdherent = 0; indexAdherent < data.event.equipes[indexEquipe].length; indexAdherent ++)
                        {
                            htmlEquipes[indexEquipe] = htmlEquipes[indexEquipe] + data.event.equipes[indexEquipe][indexAdherent].prenom + " " + data.event.equipes[indexEquipe][indexAdherent].nom; 
                            if (indexAdherent != data.event.equipes[indexEquipe].length-1)
                            {
                                htmlEquipes[indexEquipe] = htmlEquipes[indexEquipe] + " / ";
                            }
                        }
                    }
                    
                    if (data.event.parEquipe == true)
                    {
                        $("#event-equipeA").html("Equipe A : " + htmlEquipes[0]);
                        $("#event-equipeB").html("Equipe B : " + htmlEquipes[1]);
                    }
                    else
                    {
                        $("#event-equipeA").html("Joueurs : " + htmlEquipes[0]);
                        $("#event-equipeB").html("");
                    }
                    
                    
                })
                .fail(function() {
                    $('#listeActivites').html('ERREUR de chargement');
                })
                .always(function() {
                    //
                });

            });
              
            function btnAffecter() {
                $.ajax({
                    
                    url: './ActionServlet',
                    type: 'POST',
                    data: {
                        action: "affecterLieu",
                        event: $.urlParam("id"),
                        lieu: $("#choixLieu").val()
                    },
                    dataType: 'json'
                })
                .done(function(data) {
                    alert("lieuValide");
                })
                .fail(function() {
                    $('#listeActivites').html('ERREUR de chargement');
                })
                .always(function() {
                    //
                });
            };
            
            
            
            
            /////////////////// GOOGLE MAPS /////////////////
            
            var map;
            var marker;
            
            function makeInfoWindow(title) {
                return new google.maps.InfoWindow({
                    content: '<div>Information: ' + title + '</div>'
                });
            }

            function attachInfoWindow(marker, infowindow, htmlDescription) {
                marker.addListener('click', function() {

                    infowindow.setContent(htmlDescription);
                    infowindow.open(map, this);
                });
            }
            
            function onLieuSelect() {
                var id = $("#choixLieu").val();
                
                if (id != "undefined")
                {
                    // find the right data
                    
                    for (var i = 0; i < _data.lieux.length; i++)
                    {
                        if (_data.lieux[i].id == ""+id)
                        {
                            if (marker != null)
                            {
                                marker.setMap(null);
                                marker = null;
                            }
                            marker = new google.maps.Marker({
                                position: {lat:_data.lieux[i].lat, lng:_data.lieux[i].lng},
                                map: map,
                                title: _data.lieux[i].nom
                            });
                            
                            map.panTo({lat:_data.lieux[i].lat, lng:_data.lieux[i].lng});
                            break;    
                        }
                    }

                }

            }
            function initMap() {

                map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: 45.7601424, lng: 4.8961779},
                    zoom: 13,
                    scrollwheel: false,
                        navigationControl: false,
                        mapTypeControl: false,
                        scaleControl: false,
                        draggable: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                });
                
                

                var infowindow = makeInfoWindow('');
                
            };
            
        </script>
        
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAklw96N9rLd93ubr-F04CN7qi2ryKayAc&callback=initMap">
        </script>
    </body>
</html>

