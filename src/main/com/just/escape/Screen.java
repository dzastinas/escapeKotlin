package main.com.just.escape;

import java.util.Random;

public class Screen extends BitMap {
    private BitMap testBitmap;

    public Screen(int width, int height) {
        super(width, height);

        testBitmap = new BitMap(64, 64);
        Random random = new Random();
        for (int i = 0; i < 64 * 64; i++) {
            testBitmap.pixels[i] = random.nextInt();
        }
    }

    public void render() {
        int xo = (int) (Math.sin(System.currentTimeMillis() % 1000 / 1000.0 * Math.PI * 2) * 100);
        int yo = (int) (Math.cos(System.currentTimeMillis() % 1000 / 1000.0 * Math.PI * 2) * 60);
        draw(testBitmap, (width - 64) / 2 + xo, (height - 64) / 2 + yo);
    }


    public void draw(BitMap bitMap, int xOffs, int yOffs) {
        for (int y = 0; y < bitMap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;
            for (int x = 0; x < bitMap.width; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;
                int i = xPix + yPix * width;
                pixels[i] = bitMap.pixels[x + y * bitMap.width];
            }
        }
    }
}
