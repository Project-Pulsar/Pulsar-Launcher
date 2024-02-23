package me.geuxy.library;

import com.google.gson.Gson;

import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.console.Logger;

import java.io.File;

public final class LibraryManager {

    private final Library[] libraries;

    private final Gson gson;

    public LibraryManager(Gson gson) {
        this.gson = gson;

        this.libraries = getRequiredLibraries();
    }

    /*
     * Deserializes JSON to library array
     */
    public Library[] getRequiredLibraries() {
        String json = FileUtil.read("https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/PulsarLauncher/libraries.json");
        return gson.fromJson(json, Library[].class);
    }

    /*
     * Checks if libraries aren't downloaded yet or were modified / corrupted
     */
    public void addLibraries() {
        File jarsDir = new File("jars");

        // Create /jars directory
        FileUtil.createDirectory(jarsDir);

        for(Library library : libraries) {
            File jar = new File(jarsDir, library.getName() + ".jar");

            // If library does not exist, download it
            if(!jar.exists()) {
                FileUtil.downloadLibrary(library, jar);

            } else {

                // Corrupt jar check
                if(jar.length() != library.getBytes()) {
                    if(jar.delete()) {
                        FileUtil.downloadLibrary(library, jar);

                    } else {
                        Logger.error("Failed to delete " + library.getName());
                    }

                } else {
                    Logger.info("Found " + library.getName());
                }
            }
        }
    }

}
