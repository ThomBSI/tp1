package com.thom.tp1.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteServlet extends HttpServlet {

    public static final String VIEW_CUST_LIST              = "/WEB-INF/listCustomer.jsp";
    public static final String VIEW_ORDER_LIST             = "/WEB-INF/listOrder.jsp";

    public static final String ATTR_SUPPR                  = "suppr";
    public static final String ATTR_TYPE_SUPPR             = "supprType";
    public static final String ATTR_VALUE_TYPE_SUPPR_CUST  = "supprCustomer";
    public static final String ATTR_VALUE_TYPE_SUPPR_ORDER = "supprOrder";

    public static final String ATTR_SESSION_ORDER          = "sessionOrder";
    public static final String ATTR_SESSION_CUST           = "sessionCustomer";

    public static final String PATH_LIST_CUST              = "/listeClients";
    public static final String PATH_LIST_ORDER             = "/listeCommandes";

    public void doGet( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        System.out.println( request.getParameter( ATTR_SUPPR ) );

        String keyMap = request.getParameter( ATTR_SUPPR );

        if ( ATTR_VALUE_TYPE_SUPPR_CUST.equals( request.getParameter( ATTR_TYPE_SUPPR ) ) ) {
            Map<String, Object> sessionMap = (HashMap<String, Object>) session.getAttribute( ATTR_SESSION_CUST );
            if ( sessionMap != null && keyMap != null ) {
                sessionMap.remove( keyMap );
                session.setAttribute( ATTR_SESSION_CUST, sessionMap );
            }

            response.sendRedirect( request.getContextPath() + PATH_LIST_CUST );

        } else if ( ATTR_VALUE_TYPE_SUPPR_ORDER.equals( request.getParameter( ATTR_TYPE_SUPPR ) ) ) {
            Map<String, Object> sessionMap = (HashMap<String, Object>) session.getAttribute( ATTR_SESSION_ORDER );
            if ( sessionMap != null && keyMap != null ) {
                sessionMap.remove( keyMap );
                session.setAttribute( ATTR_SESSION_ORDER, sessionMap );
            }

            response.sendRedirect( request.getContextPath() + PATH_LIST_ORDER );
        }
    }
}
