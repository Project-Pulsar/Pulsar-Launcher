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

    @SerializedName("hide on launch")
    private final boolean hide;

    @SerializedName("single threaded")
    private final boolean singleThreaded;

    public Config(int minRam, int maxRam, String mcPath, boolean hide, boolean singleThreaded) {
        this.minRam = minRam;
        this.maxRam = maxRam;
        this.mcPath = mcPath;
        this.hide = hide;
        this.singleThreaded = singleThreaded;
    }

}
