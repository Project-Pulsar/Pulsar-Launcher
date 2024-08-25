package me.geuxy.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

import me.geuxy.Launcher;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.system.OSHelper;

@Getter
public final class Repository {

    /*
     * All URLs the launcher uses
     */
    private String name;
    private String jarName;
    private String changelogUrl;
    private String version;
    private String assetIndex;
    private String main;

    private JsonArray libraries;
    private JsonObject natives;

    /**
     * Updates the raw repository url
     *
     * @param rawUrl new raw repository URL
     */
    public void setRepository(String rawUrl) {
        JsonObject json = Launcher.getInstance().getGson().fromJson(FileUtil.read(rawUrl), JsonObject.class);

        JsonObject infoJson = json.get("Info").getAsJsonObject();
        JsonObject nativesJson = json.get("Natives").getAsJsonObject();
        JsonArray librariesJson = json.get("Libraries").getAsJsonArray();

        this.name = infoJson.get("name").getAsString();
        this.jarName = infoJson.get("jar").getAsString();
        this.changelogUrl = infoJson.get("changelog").getAsString();
        this.version = infoJson.get("version").getAsString();
        this.assetIndex = infoJson.get("assetIndex").getAsString();
        this.main = infoJson.get("main").getAsString();
        this.natives = nativesJson;
        this.libraries = librariesJson;
    }

    /**
     * Gets the required natives depending on the users OS
     *
     * @return url link to the compressed bin zip file
     */
    public String getNativesByOS() {
        return natives.get(OSHelper.getOSName()).getAsString();
    }

}
