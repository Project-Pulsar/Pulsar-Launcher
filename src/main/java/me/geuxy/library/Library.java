package me.geuxy.library;

public class Library {

    private final String name, version, path, url;
    private final int bytes;

    public Library(String line) {
        String[] info = line.split("\\|");

        String[] path = info[0].split(":");

        this.path = path[0].replace(".", "/");
        this.name = path[1];
        this.version = path[2];
        this.url = info[1];
        this.bytes = Integer.parseInt(info[2]);
    }

    public String getPath() {
        return path + "/" + name + "/" + version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public int getBytes() {
        return bytes;
    }

}
