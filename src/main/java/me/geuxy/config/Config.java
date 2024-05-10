package me.geuxy.config;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

// Do NOT convert to record because GSON will fail to set the values properly
@Getter
public final class Config {

    @SerializedName("minimum ram")
    private final int minRam;

    @SerializedName("maximum ram")
    private final int maxRam;

    @SerializedName("mc path")
    private final String mcPath;

    @SerializedName("repository")
    private final String repository;

    @SerializedName("arguments")
    private final String arguments;

    @SerializedName("hide on launch")
    private final boolean hide;

    @SerializedName("single threaded")
    private final boolean singleThreaded;

    @SerializedName("auto repair libraries")
    private final boolean autoRepairLibraries;

    public Config(int minRam, int maxRam, String mcPath, String repository, String arguments, boolean hide, boolean singleThreaded, boolean autoRepairLibraries) {
        this.minRam = minRam;
        this.maxRam = maxRam;
        this.mcPath = mcPath;
        this.repository = repository;
        this.arguments = arguments;
        this.hide = hide;
        this.singleThreaded = singleThreaded;
        this.autoRepairLibraries = autoRepairLibraries;
    }

}
