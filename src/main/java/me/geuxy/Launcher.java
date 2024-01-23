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

    private File directory;

    private LibraryManager libraryManager;

    private Config config;

    public void init() {
        this.directory = new File(OSUtil.getOS().getPulsar());
        this.libraryManager = new LibraryManager(directory);
        this.config = new Config(new GsonBuilder().setPrettyPrinting().create());

        if(!directory.exists()) {
            directory.mkdir();
        }

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

            Logger.debug("Minimum Ram: " + minimumRam);
            Logger.debug("Maximum Ram: " + maximumRam);

            String jarsDir = directory.getPath() + File.separator + "jars" + File.separator;
            String gameDir = OSUtil.getOS().getMinecraft();

            String ram = "-Xms" + minimumRam + "G -Xmx" + maximumRam + "G";
            String bin = "-Djava.library.path=" + directory.getPath() + File.separator + "bin";
            String classpath = "-cp " + jarsDir + "*:" + jarsDir + "Pulsar.jar net.minecraft.client.main.Main";

            String command = "java " + ram + " " + bin + " " + classpath + " -uuid N/A -version 1.8.8 --accessToken none --assetIndex 1.8 --gameDir " + gameDir;

            Logger.debug(command);

            Process process = Runtime.getRuntime().exec(command);

            String line;

            while((line = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine()) != null) {
                System.out.println(line);
            }

            while((line = new BufferedReader(new InputStreamReader(process.getErrorStream())).readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
