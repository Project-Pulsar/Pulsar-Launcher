package me.geuxy.library;

import lombok.Getter;

@Getter
public class Library {

    private final String name, url;
    private final int bytes;

    // TODO: Improve with gson
    public Library(String line) {
        String[] info = line.split("\\|");

        this.name = info[0];
        this.url = info[1];
        this.bytes = Integer.parseInt(info[2]);
    }

}
