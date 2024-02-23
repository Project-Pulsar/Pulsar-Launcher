package me.geuxy.utils.file;

import lombok.experimental.UtilityClass;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
public class UnzipUtil {

    public static void unzip(String zipDirectory, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);

        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zip = new ZipInputStream(Files.newInputStream(Paths.get(zipDirectory)));
        ZipEntry entry = zip.getNextEntry();

        while (entry != null) {
            String path = destDirectory + File.separator + entry.getName();

            if (!entry.isDirectory()) {
                BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(Paths.get(path)));

                byte[] bytes = new byte[4096];

                int read;

                while ((read = zip.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }
                output.close();
            } else {
                File file = new File(path);
                file.mkdirs();
            }

            zip.closeEntry();
            entry = zip.getNextEntry();
        }
        zip.close();
    }

}