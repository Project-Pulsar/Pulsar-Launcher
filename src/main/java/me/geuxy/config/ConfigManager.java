package me.geuxy.config;

import com.google.gson.Gson;

import java.io.File;

import me.geuxy.gui.Window;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.system.OSHelper;

public record ConfigManager(Gson gson, File file) {

    public void save(Config config) {
        FileUtil.write(this.file, this.gson.toJson(config));
    }

    public void load(Window window) {
        if(!this.file.exists()) {
            String defaultRepo = "https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/PulsarLauncher/repository.json";
            save(new Config(1, 2, OSHelper.getOS().getMinecraft(), defaultRepo, "", false, false, true));
        }

        window.getSettings().updateSettings(this.gson.fromJson(FileUtil.readJson(this.file), Config.class));
    }

}
