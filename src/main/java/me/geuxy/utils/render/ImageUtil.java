package me.geuxy.utils.render;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

@UtilityClass
public class ImageUtil {

    /**
     * Get scaled version of an image
     *
     * @param path image location
     * @param width new image width
     * @param height new image height
     *
     * @return scaled version of image
     */
    public static ImageIcon getScaledImage(String path, int width, int height) {
        Image icon = new ImageIcon(ClassLoader.getSystemResource(path)).getImage();

        return new ImageIcon(icon.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

}
