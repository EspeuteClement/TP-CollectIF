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
import modele.Adherent;
import modele.Demande;
import modele.Evenement;
import modele.EvenementParEquipe;
import modele.EvenementSansEquipe;
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
            HttpSession userSession;
            Action act;
            switch (typeRequete){
                case "getPage" :
                    response.sendRedirect(PageLinks.get(request.getParameter("page")));
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
                case "getAdherentDemandes" :
                    Action action = new PrintDemandesAdherentAction();
                    action.execute(request);
                    List<Demande> demandes = (List<Demande>)request.getAttribute("demandes");
                    JsonArray demandesJson = new JsonArray();
                    for(Demande d : demandes)
                    {
                        JsonObject demandeJson = new JsonObject();
                        Evenement e=d.getEvenement();
                        demandeJson.addProperty("activite",d.getActivite().getDenomination());
                        demandeJson.addProperty("tempsE",d.getDateEvenement().toString());
                        if(e!=null)
                        {
                            if (e.getLieu() != null)
                            {
                              demandeJson.addProperty("lieu",d.getEvenement().getLieu().getAdresse());  
                            }
                            
                            if(d.getEvenement().getClass().equals(EvenementParEquipe.class))
                            {
                                demandeJson.addProperty("equipe",true);
                                EvenementParEquipe epe= (EvenementParEquipe) d.getEvenement();
                                String equipeA="";
                                int i;
                                for(i=0;i<epe.getEquipeA().getParticipants().size();i++)
                                {
                                    Adherent a = epe.getEquipeA().getParticipants().get(i);
                                    equipeA += a.getPrenomNom();
                                    if(i!=epe.getEquipeA().getParticipants().size()-1)
                                    {
                                        equipeA += " & ";
                                    }
                                }
                                demandeJson.addProperty("equipeA",equipeA);
                                String equipeB="";
                                for(i=0;i<epe.getEquipeB().getParticipants().size();i++)
                                {
                                    Adherent a = epe.getEquipeB().getParticipants().get(i);
                                    equipeB += a.getPrenomNom();
                                    if(i!=epe.getEquipeB().getParticipants().size()-1)
                                    {
                                        equipeB += " & ";
                                    }
                                }
                                demandeJson.addProperty("equipeB",equipeB);
                            }
                            else
                            {
                                demandeJson.addProperty("equipe",false);
                                EvenementSansEquipe ese= (EvenementSansEquipe) d.getEvenement();
                                String participants="";
                                int i;
                                for(i=0;i<ese.getParticipants().size();i++)
                                {
                                    Adherent a = ese.getParticipants().get(i);
                                    participants += a.getPrenomNom();
                                    if(i!=ese.getParticipants().size()-1)
                                    {
                                        participants += " & ";
                                    }
                                }
                                demandeJson.addProperty("participants",participants);
                            }
                        }
                        demandeJson.addProperty("tempsD",d.getDateDemande().toString());
                        demandesJson.add(demandeJson);
                    }
                    JsonObject retour = new JsonObject();
                    retour.add("demandes", demandesJson);
                    out.println(retour);
                    break;
                case "posterDemande" :
                    action = new PosterDemandeAction();
                    action.execute(request);
                    List<Activite> activites = (List<Activite>)request.getAttribute("activites");
                    demandesJson = new JsonArray();
                    for(Activite a : activites)
                    {
                        JsonObject demandeJson = new JsonObject();
                        demandeJson.addProperty("activite",a.getDenomination());
                        demandeJson.addProperty("id", a.getId());
                        demandesJson.add(demandeJson);
                    }
                    retour = new JsonObject();
                    retour.add("activites", demandesJson);
                    out.println(retour);
                    break;
                case "printEvenements" :
                    action = new PrintEvenementsAction();
                    action.execute(request);
                    List<Evenement> eve = (List<Evenement>)request.getAttribute("evenements");
                    demandesJson = new JsonArray();
                    
                    userSession = request.getSession(true);
                    
                    Long id = ((Adherent) userSession.getAttribute("user")).getId();
                    
                    if (id == 39)
                    {
                        for(Evenement e : eve)
                    {
                        JsonObject demandeJson = new JsonObject();
                        demandeJson.addProperty("activite",e.getActivite().getDenomination());
                        demandeJson.addProperty("tempsE",e.getDate().toString());
                        demandeJson.addProperty("id",e.getId());
                        if(e.getLieu()!=null)
                        {
                            demandeJson.addProperty("avoirLieu",true);
                            demandeJson.addProperty("lieu",e.getLieu().getAdresse());
                        }
                        else
                        {
                            demandeJson.addProperty("avoirLieu",false);
                            demandeJson.addProperty("lieu","A definir");
                        }
                        if(e.getClass().equals(EvenementParEquipe.class))
                        {
                            demandeJson.addProperty("equipe",true);
                            EvenementParEquipe epe= (EvenementParEquipe) e;
                            String equipeA="";
                            int i;
                            for(i=0;i<epe.getEquipeA().getParticipants().size();i++)
                            {
                                Adherent a = epe.getEquipeA().getParticipants().get(i);
                                equipeA += a.getPrenomNom();
                                if(i!=epe.getEquipeA().getParticipants().size()-1)
                                {
                                    equipeA += " & ";
                                }
                            }
                            demandeJson.addProperty("equipeA",equipeA);
                            String equipeB="";
                            for(i=0;i<epe.getEquipeB().getParticipants().size();i++)
                            {
                                Adherent a = epe.getEquipeB().getParticipants().get(i);
                                equipeB += a.getPrenomNom();
                                if(i!=epe.getEquipeB().getParticipants().size()-1)
                                {
                                    equipeB += " & ";
                                }
                            }
                            demandeJson.addProperty("equipeB",equipeB);
                        }
                        else
                        {
                            demandeJson.addProperty("equipe",false);
                            EvenementSansEquipe ese= (EvenementSansEquipe) e;
                            String participants="";
                            int i;
                            for(i=0;i<ese.getParticipants().size();i++)
                            {
                                Adherent a = ese.getParticipants().get(i);
                                participants += a.getPrenomNom();
                                if(i!=ese.getParticipants().size()-1)
                                {
                                    participants += " & ";
                                }
                            }
                            demandeJson.addProperty("participants",participants);
                        }
                        demandesJson.add(demandeJson);
                    }
                    }
                    
                    retour = new JsonObject();
                    retour.add("evenements", demandesJson);
                    out.println(retour);
                    break;
                case "poster":
                    action = new PosterAction();
                    action.execute(request);
                    retour = new JsonObject();
                    JsonObject succes = new JsonObject();
                    succes.addProperty("succes", (Boolean) request.getAttribute("succes"));
                    retour.add("succes",succes);
                    out.println(retour);
                    break;
                case "disconnect":
                    userSession = request.getSession(true);
                    userSession.setAttribute("user", null);
                    response.sendRedirect("index.jsp");
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
        PageLinks.put("histoDomander", "histoDemandes.jsp");
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
