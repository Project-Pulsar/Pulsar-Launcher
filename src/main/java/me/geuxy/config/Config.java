package me.geuxy.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

import me.geuxy.gui.Window;
import me.geuxy.utils.file.FileUtil;

import java.io.File;

@RequiredArgsConstructor
public class Config {

    private final Gson gson;

    public void save(File directory, int minimumRam, int maximumRam, boolean hide) {
        JsonObject json = new JsonObject();

        json.addProperty("Minimum Ram", minimumRam);
        json.addProperty("Maximum Ram", maximumRam);
        json.addProperty("Hide on launch", hide);

        FileUtil.write(new File(directory, "config.json"), gson.toJson(json));
    }

    public void load(File directory, Window window) {
        File file = new File(directory, "config.json");

        if(!file.exists()) {
            this.save(directory, 1, 1, false);
            return;
        }

        JsonObject json = FileUtil.readJson(file);

        window.setValues(json.get("Minimum Ram").getAsInt(), json.get("Maximum Ram").getAsInt(), json.get("Hide on launch").getAsBoolean());
    }

}
