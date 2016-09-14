package main.com.just.escape;

public class BitMap {

    public final int width;
    public final int height;
    public final int[] pixels;

    public BitMap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
}
