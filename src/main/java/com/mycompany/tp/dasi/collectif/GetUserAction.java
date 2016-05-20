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

/**
 *
 * @author Element
 */
class GetUserAction extends Action {

    public GetUserAction() {
    }

    @Override
    public void execute(HttpServletRequest request) {
        JsonObject data = new JsonObject();
        data.addProperty("null", false);
        HttpSession userSession = request.getSession();
        Adherent a = (Adherent) userSession.getAttribute("user");
        
        if (a != null)
        {
            JsonObject user = new JsonObject();
            user.addProperty("name", a.getNom());
            data.add("user", user);
        }
        
        request.setAttribute("json", data);
    }
    
}
