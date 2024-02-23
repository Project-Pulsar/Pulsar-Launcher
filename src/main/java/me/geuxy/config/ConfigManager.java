package me.geuxy.config;

import com.google.gson.Gson;

import me.geuxy.gui.Window;
import me.geuxy.utils.file.FileUtil;

import java.io.File;

public record ConfigManager(Gson gson, File file) {

    /*
     * Serializes Config object to JSON
     */
    public void save(Config config) {
        FileUtil.write(file, gson.toJson(config));
    }

    /*
     * Deserializes JSON to Config object
     */
    public void load(Window window) {
        if(!file.exists()) {
            this.save(new Config(1, 2, false));
        }

        window.setConfig(gson.fromJson(FileUtil.readJson(file), Config.class));
    }

}
