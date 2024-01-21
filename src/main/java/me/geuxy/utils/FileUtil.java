package me.geuxy.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

}
