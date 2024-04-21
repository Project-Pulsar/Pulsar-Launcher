package me.geuxy.config;

import com.google.gson.Gson;

import java.io.File;

import me.geuxy.gui.Window;
import me.geuxy.utils.file.FileUtil;

public record ConfigManager(Gson gson, File file) {

    public void save(Config config) {
        FileUtil.write(this.file, this.gson.toJson(config));
    }

    public void load(Window window) {
        if(!this.file.exists())
            save(new Config(1, 2, false));

        window.setConfig(this.gson.fromJson(FileUtil.readJson(this.file), Config.class));
    }

}
