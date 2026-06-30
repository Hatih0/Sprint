package itu.hatif.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.net.URL;

public class Util {
    
public static List<Class<?>> getAllClasses(String packageName)
        throws Exception {

    List<Class<?>> classes = new ArrayList<>();

    String path = packageName.replace('.','/');

    ClassLoader classLoader =
            Thread.currentThread().getContextClassLoader();

    URL resource = classLoader.getResource(path);

    if (resource == null) {
        return classes;
    }

    File directory = new File(resource.toURI());

    findClasses(directory, packageName, classes);

    return classes;
}

private static void findClasses(
        File directory,
        String packageName,
        List<Class<?>> classes)
        throws Exception {

    for (File file : directory.listFiles()) {

        if (file.isDirectory()) {

            findClasses(
                    file,
                    packageName + "." + file.getName(),
                    classes
            );

        } else if (file.getName().endsWith(".class")) {

            String className =
                    packageName + "."
                    + file.getName().replace(".class", "");

            classes.add(Class.forName(className));
        }
    }
}

}
