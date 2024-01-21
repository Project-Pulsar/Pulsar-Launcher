package me.geuxy;

import com.formdev.flatlaf.FlatDarculaLaf;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.geuxy.config.Config;
import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.utils.FileUtil;
import me.geuxy.utils.Logger;
import me.geuxy.utils.OSUtil;
import me.geuxy.utils.UnzipUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Getter
public enum Launcher {

    INSTANCE;

    private File directory;

    private LibraryManager libraryManager;

    private Config config;

    public void init() {
        this.directory = new File(OSUtil.getOS().getPulsar());
        this.libraryManager = new LibraryManager(directory);
        this.config = new Config(new GsonBuilder().setPrettyPrinting().create());

        new Window();
    }

    public void startClient(int minimumRam, int maximumRam) {
        try {
            libraryManager.setupLibraries();
            libraryManager.addLibraries();

            File binDirectory = new File(directory, "bin");
            File binZip = new File(directory, "bin.zip");

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

            String jarsDir = directory.getPath() + File.separator + "jars" + File.separator;
            String gameDir = OSUtil.getOS().getMinecraft();
            Process process = Runtime.getRuntime().exec("java -Xms" + minimumRam + "G -Xmx" + maximumRam + "G -Djava.library.path=" + OSUtil.getOS().getPulsar() + File.separator + "bin -cp " + jarsDir + "*:" + jarsDir + "Pulsar.jar net.minecraft.client.main.Main -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir);

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;

            while((line = input.readLine()) != null) {
                System.out.println(line);
            }

            while((line = error.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            if(OSUtil.isLinux()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }

        } catch(Exception e) {
            Logger.error("Failed to apply theme");
        }

        INSTANCE.init();
    }

}
