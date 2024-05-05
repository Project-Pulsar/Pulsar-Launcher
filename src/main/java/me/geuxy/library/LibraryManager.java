package me.geuxy.library;

import com.google.gson.Gson;

import java.io.File;

import me.geuxy.Launcher;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.file.FileUtil;

public final class LibraryManager {

    /*
     * List of required libraries
     */
    private final Library[] libraries;

    /*
     * GSON object for deserializing
     */
    private final Gson gson;

    public LibraryManager(Gson gson) {
        this.gson = gson;

        Logger.info("Finding libraries...");

        this.libraries = getRequiredLibraries();
    }

    /*
     * Get required libraries from JSON
     */
    public Library[] getRequiredLibraries() {
        return this.gson.fromJson(Launcher.getInstance().getGithubAPI().getLibrariesJson(), Library[].class);
    }

    /*
     * Download and validate libraries
     */
    public boolean setupLibraries() {
        File jarsDir = new File("jars");
        FileUtil.createDirectory(jarsDir);

        for(Library library : this.libraries) {
            File jar = new File(jarsDir, library.getName() + ".jar");

            if(!jar.exists()) {
                if(FileUtil.download(library.getUrl(), jar)) {
                    return true;
                }

            } else if(jar.length() != library.getBytes()) {
                jar.delete();
                FileUtil.download(library.getUrl(), jar);

            } else {
                Logger.info("Found " + library.getName());
            }
        }
        return false;
    }

}
