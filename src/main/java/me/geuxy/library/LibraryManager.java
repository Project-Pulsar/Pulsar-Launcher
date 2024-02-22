package me.geuxy.library;

import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.console.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {

    private final List<Library> libraries = new ArrayList<>();

    public void setupLibraries() {
        try {
            Scanner scanner = new Scanner(new URL("https://raw.githubusercontent.com/Geuxy/Pulsar/main/libraries.txt").openStream());

            libraries.clear();

            while(scanner.hasNextLine()) {
                libraries.add(new Library(scanner.nextLine()));
            }
        } catch (IOException ignored) {
        }

    }

    public void addLibraries() {
        File jarsDirectory = new File("jars");

        this.createDirectory(jarsDirectory);

        libraries.forEach(library -> {
            File jar = new File(jarsDirectory, library.getName() + ".jar");

            boolean wrongSize = jar.length() != library.getBytes();

            if(!jar.exists()) {
                this.downloadLibrary(library, jar);

            } else {
                if(wrongSize) {
                    if(jar.delete()) {
                        this.downloadLibrary(library, jar);

                    } else {
                        Logger.error("Failed to delete " + library.getName());
                    }

                } else {
                    Logger.info("Found " + library.getName());
                }
            }
        });
    }

    private void createDirectory(File file) {
        if(!file.exists()) {
            if(file.mkdir()) {
                Logger.info("Created new directory: " + file.getPath());

            } else {
                Logger.error("Failed to make directory: " + file.getPath());
            }
        }
    }

    private void downloadLibrary(Library library, File file) {
        if(FileUtil.download(library.getUrl(), file)) {
            System.out.println("Downloaded " + library.getName());

        } else {
            System.err.println("Failed to download " + library.getName());
        }
    }

}
