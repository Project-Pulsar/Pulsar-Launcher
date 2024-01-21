package me.geuxy.library;

import me.geuxy.utils.FileUtil;
import me.geuxy.utils.OSUtil;

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

    public void setup() {
        File launcherDir = new File(OSUtil.getOS().getDirectory() + "libraries");

        this.makeDirectory(launcherDir);

        libraries.forEach(library -> {
            String last = "";

            for(String path : library.getPath().split("/")) {
                File filePath = new File(launcherDir, last.isEmpty() ? path : last + "/" + path);

                if(!filePath.exists()) {
                    filePath.mkdir();
                }
                last += "/" + path;
            }
            File jar = new File(launcherDir, library.getPath().replace("/", File.separator) + File.separator + library.getName() + "-" + library.getVersion() + ".jar");

            if(!jar.exists()) {
                this.downloadLibrary(library, jar);

            } else {
                if(jar.length() != library.getBytes()) {
                    if(jar.delete()) {
                        this.downloadLibrary(library, jar);

                    } else {
                        System.err.println("Failed to delete " + library.getName());
                    }

                } else {
                    System.out.println("Found " + library.getName());
                }
            }
        });
    }

    private void makeDirectory(File file) {
        if(!file.exists()) {
            if(file.mkdir()) {
                System.out.println("Created new directory: " + file.getPath());

            } else {
                System.err.println("Failed to make directory: " + file.getPath());
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

    public List<Library> getLibraries() {
        return libraries;
    }

}
