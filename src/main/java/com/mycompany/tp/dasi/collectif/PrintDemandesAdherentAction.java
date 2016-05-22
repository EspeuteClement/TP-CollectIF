/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Demande;
import service.ServiceMetier;

/**
 *
 * @author gaoyiqin95
 */
public class PrintDemandesAdherentAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        long id = Integer.parseInt(request.getParameter("id"));
        List<Demande> demandes = ServiceMetier.recupererAdherentDemandes(id);
        request.setAttribute("demandes", demandes);
    }

}
