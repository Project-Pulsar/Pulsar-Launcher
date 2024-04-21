package me.geuxy.library;

import lombok.Getter;

@Getter
public final class Library {

    private final String name;
    private final String url;

    private final int bytes;

    public Library(String line) {
        String[] info = line.split("\\|");

        this.name = info[0];
        this.url = info[1];
        this.bytes = Integer.parseInt(info[2]);
    }

}
