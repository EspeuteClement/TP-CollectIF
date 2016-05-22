/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Lieu;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class GetLieuxAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        JsonArray data = new JsonArray();
        
        List<Lieu> lieux =  ServiceMetier.recupererLieux();
        
        for (Lieu l : lieux)
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("nom", l.getDenomination());
            obj.addProperty("lat", l.getLatitude());
            obj.addProperty("lng", l.getLongitude());
            obj.addProperty("id", l.getId());
            
            data.add(obj);
        }
        
        request.setAttribute("json", data);
    }
    
}
