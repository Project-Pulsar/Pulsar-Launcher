package me.geuxy.utils.math;

public class RandomUtil {

    public static int range(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

}
