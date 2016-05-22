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
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class GetEventAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        JsonObject data = new JsonObject();
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Evenement event = ServiceMetier.recupererUnEvenement(id);
        
        if (event != null)
        {
            data.addProperty("nom", event.getActivite().getDenomination());
        
            if (event.getLieu() != null)
            {
                data.addProperty("lieu", event.getLieu().getDenomination());
            }
            else
            {
                data.addProperty("lieu", "à définir");
            }

            data.addProperty("parEquipe", Boolean.FALSE);
            List<List<Adherent>> equipes = new ArrayList<List<Adherent>>();
            if (event.getClass() == EvenementParEquipe.class)
            {
                data.addProperty("parEquipe", Boolean.TRUE);

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
            data.add("equipes", jsonEquipes);

            request.setAttribute("json", data);
        }
        
    }
    
}
