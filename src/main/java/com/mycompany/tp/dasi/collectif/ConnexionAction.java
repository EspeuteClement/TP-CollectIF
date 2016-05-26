/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Adherent;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class ConnexionAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        System.out.println(request.getParameter("email") + ", " + request.getParameter("id"));
        Adherent a = ServiceMetier.connexionAdherent(request.getParameter("email"), Integer.parseInt(request.getParameter("id")));
        
        JsonObject data = new JsonObject();

        
        if (a!=null)
        {
            System.out.println("Youpi banane");
            data.addProperty("sucess", true);
            HttpSession userSession = request.getSession(true);
            userSession.setAttribute("user", a);
            
            if (a.getId() == 39){
                data.addProperty("admin", true);
            }
            //HttpSession session = request.getSession(true);
        }
        else
        {
            System.out.println("Connexion ratée");
            data.addProperty("sucess", false);
        }
        
        request.setAttribute("json", data);
    }
    
}
