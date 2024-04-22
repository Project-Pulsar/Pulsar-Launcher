package me.geuxy.library;

import com.google.gson.Gson;

import java.io.File;

import me.geuxy.utils.console.Logger;
import me.geuxy.utils.file.FileUtil;

public final class LibraryManager {

    private final Library[] libraries;

    private final Gson gson;

    public LibraryManager(Gson gson) {
        this.gson = gson;

        Logger.info("Finding libraries...");

        this.libraries = getRequiredLibraries();
    }

    public Library[] getRequiredLibraries() {
        String json = FileUtil.read("https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/PulsarLauncher/libraries.json");
        return this.gson.fromJson(json, Library[].class);
    }

    public void addLibraries() {
        File jarsDir = new File("jars");
        FileUtil.createDirectory(jarsDir);

        for(Library library : this.libraries) {
            File jar = new File(jarsDir, library.getName() + ".jar");

            if(!jar.exists()) {
                FileUtil.download(library.getUrl(), jar);

            } else if(jar.length() != library.getBytes()) {
                if(jar.delete()) {
                    FileUtil.download(library.getUrl(), jar);

                } else {
                    Logger.error("Failed to delete " + library.getName());
                }
            } else {
                Logger.info("Found " + library.getName());
            }
        }
    }

}
