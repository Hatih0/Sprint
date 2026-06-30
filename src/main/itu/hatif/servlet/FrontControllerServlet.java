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

    public void init() {

    mapUrlsControllers = new HashMap<>();

    String controllerPackage =
            getServletContext().getInitParameter("controller-package");

        try {

            List<Class<?>> listClasses = Util.getAllClasses(controllerPackage);

            for (Class<?> clazz : listClasses) {

                if (clazz.isAnnotationPresent(Controller.class)) {

                    for (Method method : clazz.getDeclaredMethods()) {

                        if (method.isAnnotationPresent(GetUrl.class)) {

                            GetUrl annotation =
                                    method.getAnnotation(GetUrl.class);

                            String url = annotation.url();
                            String methode = annotation.method();

                            UrlMethod url_methode = new UrlMethod(methode,url);

                            Mapping mapping = new Mapping(
                                    clazz.getName(),
                                    method.getName()
                            );

                            mapUrlsControllers.put(url_methode, mapping);
                        }
                    }
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
                        
                        String url = request.getRequestURI().substring(request.getContextPath().length());
                        String httpmethod = request.getMethod();

                        UrlMethod methodUrl = new UrlMethod(httpmethod, url);

                        Mapping mapping = mapUrlsControllers.get(methodUrl);
                        
        if(mapping != null) {
            try {
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

}
