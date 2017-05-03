package com.thom.tp1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {

    public static final String VIEW   = "/WEB-INF/index.jsp";
    public static final String TITTLE = "Accueil";

    public void doGet( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {

        request.setAttribute( "tittle", TITTLE );

        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
    }
}