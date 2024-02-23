package me.geuxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.SneakyThrows;

import me.geuxy.config.ConfigManager;
import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.system.OSHelper;
import me.geuxy.utils.file.UnzipUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Getter
public final class Launcher {

    @Getter
    private static Launcher instance;

    private final LibraryManager libraryManager;
    private final ConfigManager configManager;

    private final Gson gson;

    private boolean running;

    public Launcher() {
        instance = this;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.libraryManager = new LibraryManager(gson);
        this.configManager = new ConfigManager(gson, new File("config.json"));

        new Window();
    }

    @SneakyThrows
    public void startClient(int[] ram) {
        if(running) {
            Logger.warn("Already running");
            return;
        }

        this.running = true;

        libraryManager.addLibraries();

        // Setup bin directory (natives)
        this.setupNatives();

        // Directories
        String jarsDir = "jars" + File.separator;
        String gameDir = OSHelper.getOS().getMinecraft();

        // Ram usage arguments
        String minRam = "-Xms" + ram[0] + "G";
        String maxRam = "-Xmx" + ram[1] + "G";

        Logger.debug("Minimum Ram: " + ram[0]);
        Logger.debug("Maximum Ram: " + ram[1]);

        // The command to be executed
        String exec = "java " + minRam + " " + maxRam + " -Djava.library.path=bin -cp " + jarsDir + "*:" + jarsDir + "Pulsar.jar net.minecraft.client.main.Main -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir;

        // Send command before executing
        Logger.debug(exec);

        // Run client
        ProcessBuilder builder = new ProcessBuilder(exec.split(" "));
        Process process = builder.start();

        // Print output
        String line;

        while((line = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine()) != null) {
            System.out.println(line);
        }

        while((line = new BufferedReader(new InputStreamReader(process.getErrorStream())).readLine()) != null) {
            System.out.println(line);
        }

        this.running = false;
    }

    private void setupNatives() throws IOException {
        File binDirectory = new File("bin");
        File binZip = new File("bin.zip");

        if(!binDirectory.exists()) {
            if(FileUtil.download("https://github.com/Geuxy/Pulsar/raw/main/bin.zip", binZip)) {
                Logger.info("Downloaded natives");

                UnzipUtil.unzip(binZip.getPath(), binDirectory.getPath());

                Logger.info("Unzipped natives");

            } else {
                Logger.error("Failed to download natives");
            }

        } else {
            Logger.info("Found natives");
        }

        if(binZip.delete()) {
            Logger.info("Deleted bin zip folder");

        } else {
            Logger.error("Failed to delete bin zip folder");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Launcher::new);
    }

}