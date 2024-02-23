package me.geuxy.utils.file;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import me.geuxy.library.Library;
import me.geuxy.utils.console.Logger;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

@UtilityClass
public class FileUtil {

    public static boolean download(String url, File file) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "my-agent");
            connection.connect();

            try (InputStream stream = connection.getInputStream()) {
                Files.copy(stream, Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
            }
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public static JsonObject readJson(File file) {
        JsonObject json = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            json = (JsonObject) new JsonParser().parse(reader);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void write(File file, String output) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.println(output);
            writer.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(String file) {
        try {
            Scanner scanner = new Scanner(new URL(file).openStream());

            String text = "";
            while(scanner.hasNextLine()) {
                text += scanner.nextLine();
            }

            return text;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Creates a directory and outputs the result
     */
    public static void createDirectory(File file) {
        if(!file.exists()) {
            if(file.mkdir()) {
                Logger.info("Created new directory: " + file.getPath());

            } else {
                Logger.error("Failed to make directory: " + file.getPath());
            }
        }
    }

    /*
     * Downloads a library by url and outputs the result
     */
    public static void downloadLibrary(Library library, File file) {
        if(FileUtil.download(library.getUrl(), file)) {
            System.out.println("Downloaded " + library.getName());

        } else {
            System.err.println("Failed to download " + library.getName());
        }
    }

}
