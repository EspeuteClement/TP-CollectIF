/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Activite;
import service.ServiceMetier;

/**
 *
 * @author gaoyiqin95
 */
public class PosterDemandeAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        List<Activite> activites = ServiceMetier.recupererActivites();
        request.setAttribute("activites", activites);
    }
    
}
