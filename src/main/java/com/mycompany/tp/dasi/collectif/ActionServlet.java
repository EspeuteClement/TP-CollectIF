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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Activite;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

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
            switch (typeRequete){
                case "getPage" :
                    response.sendRedirect(PageLinks.get(request.getParameter("page")));
                    break;
                case "getPageActivite" :
                    response.sendRedirect("activite.html?id="+request.getParameter("id"));
                    break;
                case "connect" :
                    PartieConnexion.connect(request, response);
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
