package me.geuxy;

import com.formdev.flatlaf.FlatDarkLaf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import javax.swing.*;

import lombok.Getter;

import me.geuxy.api.GithubAPI;
import me.geuxy.config.ConfigManager;
import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.system.OSHelper;

@Getter
public final class Launcher {

    @Getter
    private static Launcher instance;

    private final GithubAPI githubAPI;
    private final LibraryManager libraryManager;
    private final ConfigManager configManager;

    private final Gson gson;

    private boolean running;

    private final Window window;

    private final String name;
    private final String version;

    public Launcher() {
        instance = this;

        this.name = "Pulsar Launcher";
        this.version = "1.2.0-stable";

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.githubAPI = new GithubAPI();
        this.libraryManager = new LibraryManager(this.gson);
        this.configManager = new ConfigManager(this.gson, new File("config.json"));

        Logger.info("Initializing window...");

        this.window = new Window();
    }

    /*
     * Starts up Minecraft
     */
    public void startClient(int[] ram) {
        try {
            // Prevent multiple game instances
            if(this.running) {
                Logger.warn("Already running");
                return;
            }

            this.running = true;

            // Clear console output
            this.window.getOutput().clear();

            if(window.getSettings().isHide()) {
                this.window.setVisible(false);
            }

            // download and validate the libraries and natives
            this.libraryManager.setupLibraries();
            this.setupNatives();

            String jarsDir = "jars" + File.separator;
            String gameDir = this.window.getSettings().getMcPath().getText();
            String minRam = "-Xms" + ram[0] + "G";
            String maxRam = "-Xmx" + ram[1] + "G";

            Logger.debug("Minimum Ram: " + ram[0]);
            Logger.debug("Maximum Ram: " + ram[1]);

            // WHY THE HECK DOES THIS WORK???!?!?
            String separator = (OSHelper.getOS() == OSHelper.WINDOWS) ? ";" : ":";
            String exec = "java " + minRam + " " + maxRam + " -Djava.library.path=bin -cp " + jarsDir + "Pulsar.jar" + separator + jarsDir + "* net.minecraft.client.main.Main -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir + " --width 800 --height 500";

            // Execute the game
            Logger.debug(exec);

            // Get games console output
            ProcessBuilder builder = new ProcessBuilder(exec.split(" "));
            Process process = builder.start();

            String line;

            while((line = (new BufferedReader(new InputStreamReader(process.getInputStream()))).readLine()) != null) {
                Logger.info(line);
                this.window.getOutput().append(line);
            }

            while((line = (new BufferedReader(new InputStreamReader(process.getErrorStream()))).readLine()) != null) {
                Logger.error(line);
                this.window.getOutput().append(line);
            }

            this.running = false;
        } catch(Throwable ex) {
            throw new RuntimeException(ex);
        }

        this.window.setVisible(true);
    }

    /*
     * Downloads natives depending on the users OS
     */
    private void setupNatives() {
        File binDirectory = new File("bin");
        File binZip = new File("bin.zip");

        if(!binDirectory.exists()) {
            FileUtil.download(githubAPI.getNativesByOS(), binZip);
            FileUtil.unzip(binZip.getPath(), binDirectory.getPath());
        } else {
            Logger.info("Found natives");
        }

        if(binZip.exists()) {
            binZip.delete();
        }
    }

    /*
     * Start launcher
     */
    public static void main(String[] args) {
        if(!Stream.of(args).toList().contains("--noflatlaf")) {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch(UnsupportedLookAndFeelException e) {
                Logger.error(e.getMessage());
            }
        }
        SwingUtilities.invokeLater(Launcher::new);
    }

}
