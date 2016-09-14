package main.com.just.escape;

import java.util.Random;

public class Screen extends BitMap {
    public Screen(int width, int height) {
        super(width, height);

        testBitmap = new BitMap(64, 64);
        Random random = new Random();
        for (int i = 0; i < 64 * 64; i++) {
            testBitmap.pixels[i] = random.nextInt();
        }
    }

    public void render() {
        draw(testBitmap,  (width - 64)/4, (height - 64)/2);
    }

    private BitMap testBitmap;



    public void draw(BitMap bitMap, int xOffs, int yOffs) {
        for (int y = 0; y < bitMap.height; y++) {
                     int yPix = y+yOffs;
            if (yPix < 0 || yPix == height) continue;
            for (int x = 0; x <bitMap.width; x++){
                int xPix= x+xOffs;
                if (xPix < 0 || xPix == width) continue;
                pixels[xPix + yPix*width] = bitMap.pixels[x + y * bitMap.width];
            }
        }
    }
}
