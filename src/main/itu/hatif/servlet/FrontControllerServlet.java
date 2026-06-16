package itu.hatif.servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import itu.hatif.annotation.Controller;
import itu.hatif.util.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontControllerServlet extends HttpServlet {

    List<String> listeController;

    public void init() {

        listeController = new ArrayList<>();

        String ControllerPackage = getServletContext().getInitParameter("controller-package");
        
        try {

            List<Class<?>> listClasses = Util.getAllClasses(ControllerPackage);
            for (Class<?> clazz : listClasses) {
                
                if (clazz.isAnnotationPresent(Controller.class)) {
                    
                    listeController.add(clazz.getName());

                }       

            }
            
        } catch (Exception e) {

            e.printStackTrace();
        
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProcessRequest(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProcessRequest(request, response);

    }

    public void ProcessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>List Class Controller </title></head>");
        out.println("<body>");
        out.println("<h1> List Class Controller : </h1>");

        for (String ctrl : listeController) {
            
            out.println("<p>" + ctrl + "</p>");
        
        }
        out.println("</body>");
        out.println("</html>");

    }

}
