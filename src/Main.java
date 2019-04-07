import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
    }
}

abstract class Figure {
    private int positionX;
    private int positionY;
    private double positionXRatio;
    private double positionYRatio;

    public Figure(int positionX, int positionY, int currentWindowWidth, int currentWindowHeight) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionXRatio = currentWindowWidth / positionX;
        this.positionYRatio = currentWindowHeight / positionY;

        System.out.println(positionX);
        System.out.println(this.positionXRatio);
    }

    public void scale(int newWindowWidth, int newWindowHeight) {
        this.positionX = (int)(newWindowWidth / this.positionXRatio);
        this.positionY = (int)(newWindowHeight / this.positionYRatio);
        System.out.println("New pos");
        System.out.println(this.positionX);
        System.out.println(this.positionY);
        this.recalculateSize(newWindowWidth, newWindowHeight);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    abstract void recalculateSize(int newWindowWidth, int newWindowHeight);

    abstract void render(Graphics graphics);
}

class Circle extends Figure {
    private int radius;
    private int radiusRatio;

    public Circle(int positionX, int positionY, int currentWindowWidth, int currentWindowHeight, int radius) {
        super(positionX, positionY, currentWindowWidth, currentWindowHeight);
        this.radius = radius;
        this.radiusRatio = currentWindowWidth / this.radius;
    }

    void recalculateSize(int newWindowWidth, int newWindowHeight) {
        this.radius = newWindowWidth / this.radiusRatio;
    }

    void render(Graphics graphics) {
        int diameter = this.radius*2;
        graphics.setColor(new Color(48, 141, 255));
        graphics.drawOval(getPositionX(), getPositionY(), diameter, diameter);
    }
}

class Window extends JFrame {

    private ArrayList<Figure> figures;

    Window() {
        this.setSize(1024, 768);
        this.setTitle("Project 1");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.figures = new ArrayList<>();

        Thread changeRadius = new Thread(() -> {
            while (true) {
                this.figures.add(new Circle(200, 200, this.getWidth(), this.getHeight(), (new Random()).nextInt(100)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.repaint();
            }
        });

        changeRadius.start();
    }

    public void paint(Graphics graphics) {
        graphics.clearRect(0, 0, getWidth(), getHeight());
        for (Figure figure: this.figures) {
            figure.scale(getWidth(), getHeight());
            figure.render(graphics);
        }
    }
}