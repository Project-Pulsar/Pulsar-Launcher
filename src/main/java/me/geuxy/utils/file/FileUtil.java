package me.geuxy.utils.file;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.experimental.UtilityClass;

import me.geuxy.utils.console.Logger;

@UtilityClass
public final class FileUtil {

    /*
     *
     */
    public static void download(String url, File file) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "my-agent");
            connection.connect();

            try(InputStream stream = connection.getInputStream()) {
                Files.copy(stream, Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(IOException e) {
            Logger.error("Failed to download '" + file.getName() + "' from '" + url + "'!");
        }
    }

    public static JsonObject readJson(File file) {
        JsonObject json = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            json = (JsonObject) new JsonParser().parse(reader);

        } catch(Exception e) {
            Logger.error(e.getMessage());
        }
        return json;
    }

    public static void write(File file, String output) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println(output);
            writer.close();

        } catch(Exception e) {
            Logger.error(e.getMessage());
        }
    }

    public static String read(String file) {
        return read(file, "");
    }

    public static String read(String file, String separator) {
        try {
            Scanner scanner = new Scanner(new URL(file).openStream());

            String text = "";
            while(scanner.hasNextLine()) {
                text += scanner.nextLine() + separator;
            }

            return text;
        } catch(IOException e) {
            Logger.error(e.getMessage());
        }

        return null;
    }

    /*
     * Creates a directory and outputs the result
     */
    public static void createDirectory(File file) {
        if(!file.exists()) {
            Logger.warn("Directory '" + file.getPath() + "' already exists, skipping!");
            return;
        }

        if(file.mkdir()) {
            Logger.info("Created new directory: " + file.getPath());

        } else {
            Logger.error("Failed to make directory: " + file.getPath());
        }
    }

    /*
     * Unzips a zip file (a compressed file with ".zip" extension)
     */
    public static void unzip(String zipDir, String destination) {
        createDirectory(new File(destination));

        try {
            ZipInputStream zip = new ZipInputStream(Files.newInputStream(Paths.get(zipDir)));
            ZipEntry entry = zip.getNextEntry();

            while(entry != null) {
                String path = destination + File.separator + entry.getName();

                if(!entry.isDirectory()) {
                    BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(Paths.get(path)));

                    byte[] bytes = new byte[4096];

                    int read;

                    while((read = zip.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
                    output.close();
                } else {
                    createDirectory(new File(path));
                }

                zip.closeEntry();
                entry = zip.getNextEntry();
            }
            zip.close();

            Logger.info("Unzipped '" + zipDir + "' as '" + destination + "'!");
        } catch(IOException e) {
            Logger.error(e.getMessage());
            Logger.error("Failed to unzip file '" + zipDir + "'");
        }
    }

}
