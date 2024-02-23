package me.geuxy.config;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Config {

    @SerializedName("minimum ram")
    private final int minRam;

    @SerializedName("maximum ram")
    private final int maxRam;

    @SerializedName("hide on launch")
    private final boolean hide;

}
