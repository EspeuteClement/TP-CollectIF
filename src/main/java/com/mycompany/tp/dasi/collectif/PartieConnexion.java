/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.Adherent;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public class PartieConnexion {
    static public void connect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println(request.getParameter("email") + ", " + request.getParameter("id"));
        Adherent a = ServiceMetier.connexionAdherent(request.getParameter("email"), Integer.parseInt(request.getParameter("id")));
        if (a!=null)
        {
            System.out.println("Youpi banane");
            //HttpSession session = request.getSession(true);
        }
        else
        {
            //response.getWriter().print("Sorry UserName or Password Error!");
            response.sendRedirect("connexion.jsp?error=true");
        }
        
        
    }
}
