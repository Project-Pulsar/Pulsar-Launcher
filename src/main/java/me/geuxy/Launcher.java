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

import me.geuxy.repository.Repository;
import me.geuxy.config.ConfigManager;
import me.geuxy.gui.SplashWindow;
import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.math.RandomUtil;
import me.geuxy.utils.swing.SwingUtil;
import me.geuxy.utils.system.OSHelper;

@Getter
public final class Launcher {

    @Getter
    private static Launcher instance;

    private final Repository repository;
    private final LibraryManager libraryManager;
    private final ConfigManager configManager;

    private final Gson gson;

    private boolean running;

    private final Window window;

    private final String name;
    private final String version;

    private long launchTime;

    public Launcher() {
        instance = this;

        this.name = "Pulsar Launcher";
        this.version = "1.2.0-stable";

        SplashWindow splash = new SplashWindow();

        this.gson = new GsonBuilder().setPrettyPrinting().create();

        this.configManager = new ConfigManager(this.gson, new File("config.json"));

        this.repository = new Repository();
        this.repository.setRepository("https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/PulsarLauncher/repository.json");

        this.libraryManager = new LibraryManager(this.gson, gson.toJson(repository.getLibraries()));

        Logger.info("Initializing window...");

        this.window = new Window(splash);
    }

    /*
     * Starts up Minecraft
     */
    public boolean startClient(int[] ram) {
        try {
            // Prevent multiple game instances
            if(this.running) {
                Logger.warn("Already running");
                return true;
            }

            this.running = true;

            FileUtil.createDirectory(new File("clients"));
            FileUtil.createDirectory(new File("clients", repository.getName()));

            // Clear console output
            this.window.getOutput().clear();

            if(window.getSettings().isHide()) {
                this.window.setVisible(false);
            }

            // download and validate the libraries and natives
            if(libraryManager.setupLibraries(window.getSettings().isAutoRepair())) {
                SwingUtil.showErrorPopup("Failed to launch client", "Unable to setup libraries!");
                return true;
            }

            if(setupNatives()) {
                SwingUtil.showErrorPopup("Failed to launch client", "Unable to setup natives!");
                return true;
            }

            String clientDir = "clients" + File.separator + repository.getName() + File.separator;

            String jarsDir = clientDir + "jars" + File.separator;
            String gameDir = this.window.getSettings().getMcPath().getText();
            String minRam = "-Xms" + ram[0] + "G";
            String maxRam = "-Xmx" + ram[1] + "G";

            Logger.debug("Minimum Ram: " + ram[0]);
            Logger.debug("Maximum Ram: " + ram[1]);

            // WHY THE HECK DOES THIS WORK???!?!?
            String separator = (OSHelper.getOS() == OSHelper.WINDOWS) ? ";" : ":";
            String exec = "java " + minRam + " " + maxRam + " -Djava.library.path=" + clientDir + "bin" + " -cp " + jarsDir + repository.getJarName() + ".jar" + separator + jarsDir + "* " + repository.getMain() + " -uuid N/A -version " + repository.getVersion() + " --accessToken none --assetIndex " + repository.getAssetIndex() + " --gameDir " + gameDir + " " + window.getSettings().getArguments().getText();

            launchTime = System.currentTimeMillis();

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

                // disgusting way to check for error but hey it's something lmao.
                if(line.contains("UnsatisfiedLink")) {
                    SwingUtil.showErrorPopup("Failed to launch client", "Unable to find natives");
                    Logger.error("Unable to find natives!");
                    return true;
                }
            }

            this.running = false;
        } catch(Throwable ex) {
            throw new RuntimeException(ex);
        }

        this.window.setVisible(true);

        if(System.currentTimeMillis() - launchTime <= 1_000 * 10) {
            SwingUtil.showInfoPopup("Detected short launch time", "Did the game close unexpectedly? Check the console!");
        }

        launchTime = 0;

        return false;
    }

    /*
     * Downloads natives depending on the users OS
     */
    public boolean setupNatives() {
        File cache = new File("temp");

        File client = new File("clients", repository.getName());
        File binDirectory = new File(client, "bin");
        File binZip = new File(cache, "bin-" + RandomUtil.range(1, 99_999_999) + ".zip");

        boolean empty = binDirectory.list() != null && binDirectory.list().length == 0;

        if(!binDirectory.exists() || empty) {
            if(empty) {
                binDirectory.delete();
            }

            FileUtil.createDirectory(cache);

            if (FileUtil.download(repository.getNativesByOS(), binZip)) {
                return true;
            }
            FileUtil.unzip(binZip.getPath(), binDirectory.getPath());

        } else {
            Logger.info("Found natives");
        }

        return false;
    }

    public void clearTemp() {
        Stream.of(new File("temp").listFiles()).forEach(File::delete);
        SwingUtil.showInfoPopup("Successful operation", "Successfully cleared temp!");
    }

    public void regenerateNatives() {
        File client = new File("clients", repository.getName());
        File file = new File(client, "bin");

        Stream.of(file.listFiles()).forEach(File::delete);
        file.delete();

        if(Launcher.getInstance().setupNatives()) {
            SwingUtil.showErrorPopup("Unexpected Error", "Failed to setup natives!");
        } else {
            SwingUtil.showInfoPopup("Successful operation", "Successfully regenerated natives!");
        }
    }

    public void clearLogs() {
        File logs = new File("logs");

        if(logs.exists() && logs.list() != null && logs.list().length != 0) {
            Stream.of(logs.listFiles()).forEach(File::delete);
            SwingUtil.showInfoPopup("Successful Operation", "Successfully cleared logs!");

        } else {
            SwingUtil.showErrorPopup("Unexpected Error", "File does not exist or is empty!");
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
