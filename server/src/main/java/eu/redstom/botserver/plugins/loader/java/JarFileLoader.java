package eu.redstom.botserver.plugins.loader.java;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class JarFileLoader extends URLClassLoader {

    public JarFileLoader(URL[] urls) {
        super(urls);
    }

    public JarFileLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public JarFileLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public void addFile(String path) throws MalformedURLException {
        String urlPath = "jar:file://" + path + "!/";
        addURL(new URL(urlPath));
    }
}
