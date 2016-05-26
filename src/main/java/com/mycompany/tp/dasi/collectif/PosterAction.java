/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import modele.Demande;
import service.ServiceMetier;

/**
 *
 * @author gaoyiqin95
 */
public class PosterAction extends Action{
    @Override
    public void execute(HttpServletRequest request) {
        int annee = Integer.parseInt(request.getParameter("annee"));
        int mois = Integer.parseInt(request.getParameter("mois"));
        int jour = Integer.parseInt(request.getParameter("jour"));
        int activite = Integer.parseInt(request.getParameter("activite"));
        int adherent =Integer.parseInt(request.getParameter("adherent"));
        Date date = new Date(annee,mois,jour);
        boolean s=ServiceMetier.posterDemande(adherent, activite, date);
        request.setAttribute("succes",s);
        System.out.println(s);
    }
    
}
