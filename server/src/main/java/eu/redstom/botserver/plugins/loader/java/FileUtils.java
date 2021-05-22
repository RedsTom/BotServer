package eu.redstom.botserver.plugins.loader.java;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.JarURLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {

    public static void copyJarResourcesRecursively(final File destDir,
                                                   final JarURLConnection jarConnection) throws IOException {

        JarFile jarFile = jarConnection.getJarFile();
        Enumeration<JarEntry> e = jarFile.entries();

        while (e.hasMoreElements()) {
            final JarEntry entry = e.nextElement();
            System.out.println(entry.getName());
            if (entry.getName().startsWith("config/")) {
                System.out.println("passed");
                final String filename = StringUtils.removeStart(entry.getName(), "config/");
                System.out.println(filename);
                System.out.println("---");

                final File f = new File(destDir, filename);
                if (!entry.isDirectory()) {
                    final InputStream entryInputStream = jarFile.getInputStream(entry);
                    if (!FileUtils.copyStream(entryInputStream, f)) {
                        return;
                    }
                    entryInputStream.close();
                } else {
                    if (!FileUtils.ensureDirectoryExists(f)) {
                        throw new IOException("Could not create directory: "
                            + f.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static boolean copyStream(final InputStream is, final File f) {
        try {
            return FileUtils.copyStream(is, new FileOutputStream(f));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean copyStream(final InputStream is, final OutputStream os) {
        try {
            final byte[] buf = new byte[1024];

            int len = 0;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            is.close();
            os.close();
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean ensureDirectoryExists(final File f) {
        return f.exists() || f.mkdir();
    }
}