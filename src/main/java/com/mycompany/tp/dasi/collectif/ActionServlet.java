/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.Activite;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    HttpSession userSession;
            
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
         try (PrintWriter out = response.getWriter()) {
            String typeRequete = request.getParameter("action");
            System.out.println(typeRequete);
            Action act;
            switch (typeRequete){
                case "getPage" :
                    response.sendRedirect(PageLinks.get(request.getParameter("page")));
                    break;
                case "getAdherentDemandes" :
                    PartieDemandes.printDemandesAdherent(out,Integer.parseInt(request.getParameter("id")));
                    break;
                case "connect" :
                    act = new ConnexionAction();
                    act.execute(request);
                    out.println(request.getAttribute("json"));
                    break;
                case "getUser" :
                    act = new GetUserAction();
                    act.execute(request);
                    out.println(request.getAttribute("json"));
                    break;
                case "affecterLieu" :
                    act = new AffecterLieuAction();
                    act.execute(request);
                    out.println(request.getAttribute("json"));
                    break;
                case "getDataAffecterLieu" :
                    act = new GetDataAffecterLieuAction();
                    act.execute(request);
                    out.println(request.getAttribute("json"));
                    break;
                case "create" :                   
                    act = new CreationAction();
                    act.execute(request);
                    out.println(request.getAttribute("json"));
                    break;
                case "testConnect" :
                    out.println("Test de connexion");
                    userSession = request.getSession(true);
                    out.println(userSession.getAttribute("user"));
                    break;
                    //RequestDispatcher rd = request.getRequestDispatcher("/connexion.jsp");
                    //rd.forward(request, response);
                default:
                    break;
            }
            
         }
    }
    
    private static final Map<String, String> PageLinks;
    static
    {
        PageLinks = new HashMap<String, String>();
        PageLinks.put("connexion", "connexion.jsp");
        PageLinks.put("enregistrement", "enregistrement.jsp");
        PageLinks.put("home", "index.jsp");
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
    {
        System.out.println("POST request");
        processRequest(request, response);
    }
}
