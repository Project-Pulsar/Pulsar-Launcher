package me.geuxy.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

}
