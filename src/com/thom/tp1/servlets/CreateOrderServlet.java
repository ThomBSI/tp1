package com.thom.tp1.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thom.tp1.beans.OrderBean;
import com.thom.tp1.form.FormOrderBean;

public class CreateOrderServlet extends HttpServlet {

    public static final String ATTR_ORDER      = "order";
    public static final String ATTR_FORM_ORDER = "form";
    public static final String VIEW            = "/WEB-INF/createOrder.jsp";
    public static final String VIEW_SUCCESS    = "/WEB-INF/viewOrder.jsp";
    public static final String VIEW_FAIL       = "/WEB-INF/createOrder.jsp";
    public static final String SESSION_ORDER   = "sessionOrder";
    public static final String SESSION_CUST    = "sessionCustomer";
    public static final String TITTLE          = "Création d'une commande";

    public void doGet( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        request.setAttribute( "tittle", TITTLE );

        // M�thode appel�e quand on clique sur le lien 'nouvelle commande' dans
        // le menu
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );

    }

    public void doPost( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        // On cr�e l'objet order � partir de l'objet m�tier
        // Celui-ci fait toutes les v�rifications
        FormOrderBean form = new FormOrderBean();
        OrderBean order = form.initOrder( request );

        request.setAttribute( ATTR_ORDER, order );
        request.setAttribute( ATTR_FORM_ORDER, form );

        request.setAttribute( "tittle", TITTLE );

        // Aiguillage vers la bonne jsp en fonction du r�sultat du
        // traitement
        if ( form.getErrors().isEmpty() ) {
            HttpSession session = request.getSession();
            Map<String, Object> sessionMapOrder = (HashMap<String, Object>) session.getAttribute( SESSION_ORDER );
            Map<String, Object> sessionMapCustomer = (HashMap<String, Object>) session
                    .getAttribute( SESSION_CUST );
            if ( sessionMapOrder == null ) {
                sessionMapOrder = new HashMap<String, Object>();
            } else if ( sessionMapCustomer == null ) {
                sessionMapCustomer = new HashMap<String, Object>();
            }
            System.out.println( "order servlet.order.customer.lastname" + order.getCustomer().getLastName() );
            sessionMapOrder.put( order.getDate(), order );
            sessionMapCustomer.put( order.getCustomer().getLastName(), order.getCustomer() );
            session.setAttribute( SESSION_ORDER, sessionMapOrder );
            session.setAttribute( SESSION_CUST, sessionMapCustomer );

            this.getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VIEW_FAIL ).forward( request, response );
        }

    }
}
