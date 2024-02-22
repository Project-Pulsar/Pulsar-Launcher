package me.geuxy;

import com.google.gson.GsonBuilder;

import lombok.Getter;

import me.geuxy.config.Config;
import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.file.FileUtil;
import me.geuxy.utils.console.Logger;
import me.geuxy.utils.system.OSUtil;
import me.geuxy.utils.file.UnzipUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Getter
public enum Launcher {

    INSTANCE;

    private LibraryManager libraryManager;

    private Config config;

    private boolean running;

    public void init() {
        this.libraryManager = new LibraryManager();
        this.config = new Config(new GsonBuilder().setPrettyPrinting().create());

        new Window();
    }

    public void startClient(int minimumRam, int maximumRam) {
        if(running) {
            Logger.warn("Already running");
            return;
        }

        this.running = true;
        try {
            libraryManager.setupLibraries();
            libraryManager.addLibraries();

            File binDirectory = new File("bin");
            File binZip = new File("bin.zip");

            if(!binDirectory.exists()) {
                if(FileUtil.download("https://github.com/Geuxy/Pulsar/raw/main/bin.zip", binZip)) {
                    Logger.info("Downloaded natives");

                    UnzipUtil unzipper = new UnzipUtil();

                    unzipper.unzip(binZip.getPath(), binDirectory.getPath());
                } else {
                    Logger.error("Failed to download natives");
                }

            } else {
                Logger.info("Found natives");
            }

            Logger.debug("Minimum Ram: " + minimumRam);
            Logger.debug("Maximum Ram: " + maximumRam);

            String jarsDir = "jars" + File.separator;
            String gameDir = OSUtil.getOS().getMinecraft();

            String minRam = "-Xms" + minimumRam + "G";
            String maxRam = "-Xmx" + maximumRam + "G";

            String exec = "java " + minRam + " " + maxRam + " -Djava.library.path=bin -cp " + jarsDir + "*:" + jarsDir + "Pulsar.jar net.minecraft.client.main.Main -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir;

            Logger.debug(exec);

            ProcessBuilder builder = new ProcessBuilder(exec.split(" "));
            Process process = builder.start();

            String line;

            while((line = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine()) != null) {
                System.out.println(line);
            }

            while((line = new BufferedReader(new InputStreamReader(process.getErrorStream())).readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.running = false;
    }

}