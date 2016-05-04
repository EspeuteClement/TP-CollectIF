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
import java.util.List;
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
            //System.out.println(typeRequete);
            switch (typeRequete){
                case "getPage" :
                    response.sendRedirect(request.getParameter("page"));
                    break;
                case "getPageActivite" :
                    response.sendRedirect("activite.html?id="+request.getParameter("id"));
                case "getActivites" :
                    printListeActivites(out);
                    break;
                case "getActivite" :
                    printActivite(out,Integer.parseInt(request.getParameter("id")));
                    break;
            }
            
         }
    }
    
    protected static void loadPage(HttpServletResponse response,String page)
    {
        switch (page) {
            case "listeActivites" :
                
        }
        
       
    }
    
    protected static void printListeActivites(PrintWriter out)
    {
        //String action = request.getParameter("action");
        
        JsonArray activitesJson = new JsonArray();
        List<Activite> activites = ServiceMetier.recupererActivites();
        for(Activite a : activites)
        {
            JsonObject activiteJson = new JsonObject();
            activiteJson.addProperty("denomination", a.getDenomination());
            activiteJson.addProperty("id",a.getId());
            activitesJson.add(activiteJson);
        }
        
        //Gson gson = new Gson();
        JsonObject retour = new JsonObject();
        retour.add("activites", activitesJson);
        out.println(retour);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void printActivite(PrintWriter out, int parseInt) {
        Activite activite = ServiceMetier.recupererActiviteById(parseInt);
        
        JsonObject retour = new JsonObject();
        
        retour.addProperty("denomination", activite.getDenomination());
        retour.addProperty("parEquipe", activite.isParEquipe());
        retour.addProperty("nbPart",activite.getNbParticipants());
        out.println(retour);
    }

}
