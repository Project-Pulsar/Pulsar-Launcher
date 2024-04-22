package me.geuxy.utils.render;

import javax.swing.*;
import java.awt.*;

public class ImageUtil {

    public static ImageIcon getScaledImage(String path, int width, int height) {
        Image icon = new ImageIcon(ClassLoader.getSystemResource(path)).getImage();

        return new ImageIcon(icon.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

}
