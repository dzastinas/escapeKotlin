package main.com.just.escape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class EscapeComponent extends Canvas implements Runnable {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 120;
    private static final int SCALE = 4;
    private boolean running;
    private Thread thread;

    private Game game;
    private Screen screen;

    private BufferedImage img;
    private int[] pixels;

    public EscapeComponent() {
        Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        game = new Game();
        screen = new Screen(WIDTH, HEIGHT);

        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        joinThread();
    }

    private void joinThread() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EscapeComponent game = new EscapeComponent();
        JFrame frame = new JFrame("Escape");
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(game, BorderLayout.CENTER);

        frame.setContentPane(jPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }

    @Override
    public void run() {
        while (running) {
            tick();
            render();
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        screen.render();

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
        bufferStrategy.show();
    }

    private void tick() {
        game.tick();
    }
}
