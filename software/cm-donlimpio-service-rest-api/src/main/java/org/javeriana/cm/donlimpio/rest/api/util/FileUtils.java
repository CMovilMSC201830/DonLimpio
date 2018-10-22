package org.javeriana.cm.donlimpio.rest.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtils {
    public static Properties loadPropertyFile(String file) throws IOException {
        Properties properties = new Properties();
        properties.load(FileUtils.class.getResourceAsStream(file));
        return properties;
    }

    public static Path write(String filepath, String data) throws IOException {
        if (Files.exists(Paths.get(filepath))) {
            Files.delete(Paths.get(filepath));
        }
        return Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.CREATE_NEW);
    }

    public static void zipFile(String sourceFile, String destFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(destFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        final byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }
}
