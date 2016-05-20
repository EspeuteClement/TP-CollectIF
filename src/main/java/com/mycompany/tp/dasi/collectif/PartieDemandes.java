/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import modele.Adherent;
import modele.Demande;
import modele.Evenement;
import modele.EvenementParEquipe;
import modele.EvenementSansEquipe;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class PartieDemandes {
    
    protected static void printDemandesAdherent(PrintWriter out, long id)
    {
        JsonArray demandesJson = new JsonArray();
        List<Demande> demandes = ServiceMetier.recupererAdherentDemandes(id);
        for(Demande d : demandes)
        {
            JsonObject demandeJson = new JsonObject();
            Evenement e=d.getEvenement();
            demandeJson.addProperty("activite",d.getActivite().getDenomination());
            demandeJson.addProperty("tempsE",d.getDateEvenement().toString());
            if(e==null)
            {
                demandeJson.addProperty("creerE",false);
            }
            else
            {
                demandeJson.addProperty("creerE",true);
                demandeJson.addProperty("lieu",d.getEvenement().getLieu().getAdresse());
                if(d.getEvenement().getClass().equals(EvenementParEquipe.class))
                {
                    demandeJson.addProperty("equipe",true);
                    EvenementParEquipe epe= (EvenementParEquipe) d.getEvenement();
                    String equipeA="";
                    for(Adherent a : epe.getEquipeA().getParticipants())
                    {
                        equipeA += a.getPrenomNom();
                        equipeA += "  ";
                    }
                    demandeJson.addProperty("equipeA",equipeA);
                    String equipeB="";
                    for(Adherent a : epe.getEquipeB().getParticipants())
                    {
                        equipeB += a.getPrenomNom();
                        equipeB += "  ";
                    }
                    demandeJson.addProperty("equipeB",equipeB);
                }
                else
                {
                    demandeJson.addProperty("equipe",false);
                    EvenementSansEquipe ese= (EvenementSansEquipe) d.getEvenement();
                    String participants="";
                    for(Adherent a : ese.getParticipants())
                    {
                        participants += a.getPrenomNom();
                        participants += "  ";
                    }
                    demandeJson.addProperty("participants",participants);
                }
            }
            demandeJson.addProperty("tempsD",d.getDateDemande().toString());
            demandesJson.add(demandeJson);
        }
        JsonObject retour = new JsonObject();
        retour.add("demandes", demandesJson);
        out.println(retour);
    }
}
