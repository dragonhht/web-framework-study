package com.github.dragonhht.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具.
 *
 * @author: huang
 * @Date: 2018-12-28
 */
@Slf4j
public class ClassUtil {

    /**
     * 获取类加载器.
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类.
     * @param className 全类名
     * @param initialize 是否初始化(是否执行类的静态代码)
     * @return
     */
    public static Class<?> loadClass(String className, boolean initialize) throws ClassNotFoundException {
        Class<?> cls;
        try {
            cls = Class.forName(className, initialize, getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("类加载失败", e);
            throw e;
        }
        return cls;
    }

    /**
     * 获取指定包下的所有类.
     * @param packageName 包名
     * @return
     */
    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocl = url.getProtocol();
                    if ("file".equals(protocl)) {
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);
                    } else if("jar".equals(protocl)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile =  jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntris = jarFile.entries();
                                while (jarEntris.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntris.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("获取包下的类失败", e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) throws ClassNotFoundException {
        File[] files = new File(packagePath).listFiles(file -> {
           return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
        });
        for(File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(className)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) throws ClassNotFoundException {
        Class<?> cla = loadClass(className, false);
        classSet.add(cla);
    }

}
