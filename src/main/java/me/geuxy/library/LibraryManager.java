package me.geuxy.library;

import com.google.gson.Gson;

import java.io.File;

import com.google.gson.JsonObject;
import me.geuxy.Launcher;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.file.FileUtil;

public final class LibraryManager {

    /*
     * List of required libraries
     */
    private final Library[] libraries;

    public LibraryManager(Gson gson, String librariesJson) {
        Logger.info("Finding libraries from repository...");

        this.libraries = gson.fromJson(librariesJson, Library[].class);
    }

    /*
     * Download and validate libraries
     */
    public boolean setupLibraries(boolean autoRepair) {
        File jars = new File("clients", Launcher.getInstance().getRepository().getName());
        File jarsDir = new File(jars, "jars");
        FileUtil.createDirectory(jarsDir);

        for(Library library : this.libraries) {
            File jar = new File(jarsDir, library.getName() + ".jar");

            if(!jar.exists()) {
                if (FileUtil.download(library.getUrl(), jar)) {
                    return true;
                }

            } else if(!autoRepair) {
                Logger.warn("Auto repair is disabled, skipping check on '" + library.getName() + "'");

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
