package me.geuxy.config;

import com.google.gson.annotations.SerializedName;

// Do NOT convert to record because GSON will fail to set the values properly
public final class Config {

    @SerializedName("minimum ram")
    private final int minRam;

    @SerializedName("maximum ram")
    private final int maxRam;

    @SerializedName("hide on launch")
    private final boolean hide;

    public Config(int minRam, int maxRam, boolean hide) {
        this.minRam = minRam;
        this.maxRam = maxRam;
        this.hide = hide;
    }

    public int getMinRam() {
        return this.minRam;
    }

    public int getMaxRam() {
        return this.maxRam;
    }

    public boolean isHide() {
        return this.hide;
    }

}
