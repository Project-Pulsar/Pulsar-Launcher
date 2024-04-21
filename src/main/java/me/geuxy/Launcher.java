package me.geuxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;

import lombok.Getter;

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

    private final LibraryManager libraryManager;
    private final ConfigManager configManager;

    private final Gson gson;

    private boolean running;

    public Launcher() {
        instance = this;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.libraryManager = new LibraryManager(this.gson);
        this.configManager = new ConfigManager(this.gson, new File("config.json"));

        new Window();
    }

    public void startClient(int[] ram) {
        try {
            if(this.running) {
                Logger.warn("Already running");
                return;
            }
            this.running = true;

            this.libraryManager.addLibraries();
            setupNatives();

            String jarsDir = "jars" + File.separator;
            String gameDir = OSHelper.getOS().getMinecraft();
            String minRam = "-Xms" + ram[0] + "G";
            String maxRam = "-Xmx" + ram[1] + "G";

            Logger.debug("Minimum Ram: " + ram[0]);
            Logger.debug("Maximum Ram: " + ram[1]);

            // WHY THE HECK DOES THIS WORK???!?!?
            String separator = (OSHelper.getOS() == OSHelper.WINDOWS) ? ";" : ":";
            String exec = "java " + minRam + " " + maxRam + " -Djava.library.path=bin -cp " + jarsDir + "Pulsar.jar" + separator + jarsDir + "* net.minecraft.client.main.Main -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir + " --width 800 --height 500";

            Logger.debug(exec);

            ProcessBuilder builder = new ProcessBuilder(exec.split(" "));
            Process process = builder.start();

            String line;

            while((line = (new BufferedReader(new InputStreamReader(process.getInputStream()))).readLine()) != null)
                System.out.println(line);

            while((line = (new BufferedReader(new InputStreamReader(process.getErrorStream()))).readLine()) != null)
                System.out.println(line);

            this.running = false;
        } catch(Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    private void setupNatives() throws IOException {
        File binDirectory = new File("bin");
        File binZip = new File("bin.zip");

        if(!binDirectory.exists()) {
            if(FileUtil.download("https://github.com/Project-Pulsar/Cloud/raw/main/PulsarLauncher/bin/" + OSHelper.getOSName() + ".zip", binZip)) {
                Logger.info("Downloaded natives");

                FileUtil.unzip(binZip.getPath(), binDirectory.getPath());

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
