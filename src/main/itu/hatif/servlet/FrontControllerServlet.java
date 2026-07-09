package itu.hatif.servlet;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itu.hatif.annotation.Controller;
import itu.hatif.annotation.GetUrl;
import itu.hatif.util.Mapping;
import itu.hatif.util.UrlMethod;
import itu.hatif.util.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontControllerServlet extends HttpServlet {

    Map<UrlMethod,Mapping> mapUrlsControllers;

    @Override
    public void init() throws ServletException{
        
        mapUrlsControllers = (Map<UrlMethod, Mapping>) getServletContext().getAttribute("mapUrlsControllers");

        if (mapUrlsControllers == null) {
            throw new ServletException("Les routes n'ont pas ete chargees.");
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
                        
                        String url = request.getRequestURI().substring(request.getContextPath().length());
                        String httpmethod = request.getMethod();

                        UrlMethod methodUrl = new UrlMethod(httpmethod, url);

                        Mapping mapping = mapUrlsControllers.get(methodUrl);
                        
        if(mapping != null) {

            
            try {

                invokeMethod(mapping);
                Class<?> clazz = Class.forName(mapping.getClassName());
                
                        out.println("<h1> Controller : " + clazz.getSimpleName() + "</h1>");

                        out.println("<h2> Method : </h2>");
                        
                        for (Method method : clazz.getDeclaredMethods()) {
                            
                            if (method.isAnnotationPresent(GetUrl.class)) {
                                
                                GetUrl annotation = method.getAnnotation(GetUrl.class);
                                String urlAnnotation = annotation.url();
                                String methodhttp = annotation.method();

                                out.println("<p> -> Method : " + method.getName() + " - URL : " + methodhttp + "    / " + urlAnnotation + "</p>");
                            }
                        
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    
                    out.println("<h1> No controller found for URL: " + url + "</h1>");
                    
                }

            out.println("</body>");
            out.println("</html>");
    }

    public void invokeMethod(Mapping mapping) {

        try {

            Class<?> clazz = Class.forName(mapping.getClassName());

            Object controller = clazz.getDeclaredConstructor().newInstance();

            Method method = clazz.getDeclaredMethod(mapping.getMethodName());

            method.invoke(controller);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
