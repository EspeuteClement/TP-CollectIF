/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import javax.servlet.http.HttpServletRequest;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class AffecterLieuAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        System.out.println("Test execute->AffecteLieuAction");
        int event = Integer.parseInt(request.getParameter("event"));
        int lieu = Integer.parseInt(request.getParameter("lieu"));
        
        ServiceMetier.affecterLieuEvenement(event, lieu);
    }
    
}
