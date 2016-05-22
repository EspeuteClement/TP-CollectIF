/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Adherent;
import modele.Evenement;
import modele.EvenementParEquipe;
import modele.EvenementSansEquipe;
import modele.Lieu;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class GetDataAffecterLieuAction extends Action {
    
    @Override
    public void execute(HttpServletRequest request) {
        JsonObject data = new JsonObject();
        JsonArray jsonLieux = new JsonArray();
        
        List<Lieu> lieux =  ServiceMetier.recupererLieux();
        
        for (Lieu l : lieux)
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("nom", l.getDenomination());
            obj.addProperty("lat", l.getLatitude());
            obj.addProperty("lng", l.getLongitude());
            obj.addProperty("id", l.getId());
            
            jsonLieux.add(obj);
        }
        
        data.add("lieux",jsonLieux);
        
        JsonObject jsonEvent = new JsonObject();
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Evenement event = ServiceMetier.recupererUnEvenement(id);
        
        if (event != null)
        {
            jsonEvent.addProperty("nom", event.getActivite().getDenomination());
            jsonEvent.addProperty("date", event.getDate().toString());
            if (event.getLieu() != null)
            {
                jsonEvent.addProperty("lieu", event.getLieu().getDenomination());
            }
            else
            {
                jsonEvent.addProperty("lieu", "à définir");
            }

            jsonEvent.addProperty("parEquipe", Boolean.FALSE);
            List<List<Adherent>> equipes = new ArrayList<List<Adherent>>();
            if (event.getClass() == EvenementParEquipe.class)
            {
                jsonEvent.addProperty("parEquipe", Boolean.TRUE);

                EvenementParEquipe event_ = (EvenementParEquipe) event;


                equipes.add(event_.getEquipeA().getParticipants());
                equipes.add(event_.getEquipeB().getParticipants());

            }
            else
            {
                EvenementSansEquipe event_ = (EvenementSansEquipe) event;
                equipes.add(event_.getParticipants());
            }

            JsonArray jsonEquipes = new JsonArray();
            for (List<Adherent> equipe : equipes)
            {
                JsonArray jsonJoueurs = new JsonArray();
                for (Adherent joueur : equipe)
                {
                    JsonObject jsonJoueur = new JsonObject();
                    jsonJoueur.addProperty("nom", joueur.getNom());
                    jsonJoueur.addProperty("prenom", joueur.getPrenom());

                    jsonJoueurs.add(jsonJoueur);
                }

                jsonEquipes.add(jsonJoueurs);
            }
            jsonEvent.add("equipes", jsonEquipes);

        }
        
        data.add("event",jsonEvent);
        request.setAttribute("json", data);
    }
}
