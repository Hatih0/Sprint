package itu.hatif.listener;

import itu.hatif.annotation.Controller;
import itu.hatif.annotation.GetUrl;
import itu.hatif.util.Mapping;
import itu.hatif.util.UrlMethod;
import itu.hatif.util.Util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        Map<UrlMethod, Mapping> mapUrlsControllers = new HashMap<>();

        String controllerPackage =
                context.getInitParameter("controller-package");

        try {

            List<Class<?>> listClasses =
                    Util.getAllClasses(controllerPackage);

            for (Class<?> clazz : listClasses) {

                if (!clazz.isAnnotationPresent(Controller.class))
                    continue;

                for (Method method : clazz.getDeclaredMethods()) {

                    if (!method.isAnnotationPresent(GetUrl.class))
                        continue;

                    GetUrl annotation = method.getAnnotation(GetUrl.class);

                    String url = annotation.url();
                    String methode = annotation.method();

                    UrlMethod urlMethod = new UrlMethod(methode, url);

                    if (mapUrlsControllers.containsKey(urlMethod)) {
                        throw new Exception(
                                "Route dupliquee : "
                                        + methode + " " + url);
                    }

                    Mapping mapping = new Mapping(
                            clazz.getName(),
                            method.getName()
                    );

                    mapUrlsControllers.put(urlMethod, mapping);
                }
            }

            // On stocke la map dans le contexte
            context.setAttribute("mapUrlsControllers", mapUrlsControllers);

            System.out.println("Routes chargees : "
                    + mapUrlsControllers.size());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}