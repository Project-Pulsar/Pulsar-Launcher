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

    /**
     * Downloads a file from the given url
     *
     * @param url link to download file from
     * @param file location to save file to
     */
    public static boolean download(String url, File file) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "my-agent");
            connection.connect();

            try(InputStream stream = connection.getInputStream()) {
                Files.copy(stream, Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(IOException e) {
            Logger.error("Failed to download '" + file.getName() + "' from '" + url + "'!");
            return true;
        }

        return false;
    }

    /**
     * Reads a JSON file and for deserialization
     *
     * @param file JSON file location
     *
     * @return deserialized JSON
     */
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

    /**
     * Creates and writes a file
     *
     * @param file location to save file
     * @param output text to be written inside the file
     */
    public static void write(File file, String output) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println(output);
            writer.close();

        } catch(Exception e) {
            Logger.error(e.getMessage());
        }
    }

    /**
     * Reads the contents of a file from url
     *
     * @param url link to read from
     *
     * @return contents from file that was read
     */
    public static String read(String url) {
        return read(url, "");
    }

    /**
     * Reads the contents of a file from url
     *
     * @param url link to read from
     * @param separator string to separate each line
     *
     * @return contents from file that was read
     */
    public static String read(String url, String separator) {
        try {
            Scanner scanner = new Scanner(new URL(url).openStream());

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

    /**
     * Creates a directory
     *
     * @param file directory location to be created
     */
    public static void createDirectory(File file) {
        if(file.exists()) {
            Logger.warn("Directory '" + file.getAbsolutePath() + "' already exists, skipping!");
            return;
        }

        if(file.mkdir()) {
            Logger.info("Created new directory: " + file.getAbsolutePath());

        } else {
            Logger.warn("Failed to make directory: " + file.getAbsolutePath());
        }
    }

    /**
     * Unzips a compressed zip file
     *
     * @param zipDir zip file location to unzip
     * @param destination location to keep unzipped folder
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
