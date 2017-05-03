package com.thom.tp1.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thom.tp1.beans.CustomerBean;
import com.thom.tp1.form.FormCustomerBean;

public class CreateCustomerServlet extends HttpServlet {
    public static final String ATTR_CUSTOMER      = "customer";
    public static final String ATTR_FORM_CUSTOMER = "form";
    public static final String VIEW_FORM          = "/WEB-INF/createCustomer.jsp";
    public static final String VIEW_SUCCESS       = "/WEB-INF/viewCustomer.jsp";
    public static final String VIEW_FAIL          = "/WEB-INF/createCustomer.jsp";
    public static final String SESSION_CUST       = "sessionCustomer";
    public static final String PATH_FILES         = "pathFiles";
    public static final String ATTR_PATH_FILE     = PATH_FILES;
    public static final String TITTLE             = "Création d'un client";

    public void doGet( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        request.setAttribute( "tittle", TITTLE );

        // M�thode appel�e quand on clique sur le lien 'nouveau client' dans le
        // menu
        this.getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );

    }

    public void doPost( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        // Récupération du chemi de sauvegarde des fichiers
        String filesPath = this.getServletConfig().getInitParameter( PATH_FILES );

        // On cr�e l'objet customer � partir de l'objet m�tier
        // Celui-ci fait toutes les v�rifications
        FormCustomerBean form = new FormCustomerBean();
        CustomerBean customer = form.initCustomer( request, filesPath );

        request.setAttribute( ATTR_CUSTOMER, customer );
        request.setAttribute( ATTR_FORM_CUSTOMER, form );

        request.setAttribute( "tittle", TITTLE );

        // Aiguillage vers la bonne jsp en fonction du r�sultat du
        // traitement
        if ( form.getErrors().isEmpty() ) {
            HttpSession session = request.getSession();
            Map<String, Object> sessionMapCustomer = (HashMap<String, Object>) session
                    .getAttribute( SESSION_CUST );
            if ( sessionMapCustomer == null ) {
                sessionMapCustomer = new HashMap<String, Object>();
            }
            sessionMapCustomer.put( customer.getLastName(), customer );
            session.setAttribute( SESSION_CUST, sessionMapCustomer );

            this.getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VIEW_FAIL ).forward( request, response );
        }

    }
}
