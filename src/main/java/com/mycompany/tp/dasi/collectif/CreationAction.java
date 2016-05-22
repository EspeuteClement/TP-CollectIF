/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;
import modele.Adherent;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class CreationAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        String nom = (String) request.getParameter("nom");
        String prenom = (String) request.getParameter("prenom");
        String adresse  = (String) request.getParameter("adresse");
        String mail = (String) request.getParameter("mail");
        Adherent a;
        JsonObject data = new JsonObject();
        data.addProperty("sucess", false);
        System.out.println(nom + ", " + prenom + ", " + adresse + ", " + mail);
        if (nom != null && prenom != null && adresse != null && mail != null)
        {
            a = ServiceMetier.inscrireAdherent( nom, prenom, adresse, mail);
            if (a != null)
            {
                // inscription réussie
                System.out.println(a);
                data.addProperty("sucess", true);
            }
            else
            {
                // mail invalide
                data.addProperty("sucess", false);
            }
        }
        
        

        
        request.setAttribute("json", data);
    }
    
    
}
