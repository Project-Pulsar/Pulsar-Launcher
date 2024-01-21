package me.geuxy;

import me.geuxy.library.Library;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.OSUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public enum Launcher {

    INSTANCE;

    private LibraryManager libraryManager;

    public void init() {
        this.libraryManager = new LibraryManager();
        libraryManager.setupLibraries();
        libraryManager.setup();

        String mcDir = OSUtil.getOS().getDirectory();

        String exec = "java -Xms1G -Xmx4G -Djava.library.path=\"" + mcDir + "bin" + File.separator + "2c19601cb32d7c6f40bcc585b310b4adf8754b81" + "\" -cp " + "\"";

        int i = 0;
        for(Library library : libraryManager.getLibraries()) {
            exec += (i == 0 ? "" : i == libraryManager.getLibraries().size() ? "" : ";") + mcDir + "libraries" + File.separator + library.getPath() + File.separator + library.getName() + "-" + library.getVersion() + ".jar";
            i++;
        }

        exec += "\" net.minecraft.client.main.Main -uuid N/A -accessToken none --assetIndex 1.8 -gameDir " + mcDir;

        // Prints the command
        System.out.println(exec);

        try {
            Process process = Runtime.getRuntime().exec(exec);

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s;

            while((s = input.readLine()) != null) {
                System.out.println(s);
            }

            while((s = error.readLine()) != null) {
                System.out.println(s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }

    public static void main(String[] args) {
        INSTANCE.init();

        Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE::stop));
    }

}
