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
    private Position position;
    private Size maxSize;
    private double positionXRatio;
    private double positionYRatio;

    public Figure(Position position, Size windowSize) {
        this.position = position;
        this.maxSize = new Size(windowSize.width - this.position.x, windowSize.height - this.position.y);
        this.positionXRatio = windowSize.width / position.x;
        this.positionYRatio = windowSize.height / position.y;
    }

    public void scale(Size newSize) {
        this.position = new Position(
                (int) (newSize.width / this.positionXRatio),
                (int) (newSize.height / this.positionYRatio)
        );
        this.recalculateSize(newSize);
    }

    public Position getPosition() {
        return position;
    }

    protected Size getMaxSize() {
        return this.maxSize;
    }

    abstract Size getSize();

    abstract void recalculateSize(Size newWindowSize);

    abstract void render(Graphics graphics);
}

class Circle extends Figure {
    private int radius;
    private int radiusRatio;
    private Size size;

    public Circle(Position position, Size windowSize, int radius) {
        super(position, windowSize);

        int diameter = radius * 2;
        if (diameter > getMaxSize().width) {
            diameter = getMaxSize().width;
        }

        if (diameter > getMaxSize().height) {
            diameter = getMaxSize().height;
        }
        radius = (diameter / 2) > 0 ? (diameter / 2) : 1;

        this.radius = radius;
        this.radiusRatio = windowSize.width / this.radius;
        this.calculateSize();
    }

    public Size getSize() {
        return this.size;
    }

    private void calculateSize() {
        this.size = new Size(this.radius * 2, this.radius * 2);
    }

    void recalculateSize(Size newSize) {
        this.radius = newSize.width / this.radiusRatio;
        this.calculateSize();
    }

    void render(Graphics graphics) {
        Position position = this.getPosition();
        int diameter = this.radius * 2;
        graphics.setColor(new Color(48, 141, 255));
        graphics.drawOval(position.x, position.y, diameter, diameter);
    }
}

class Window extends JFrame {

    private ArrayList<Figure> figures;
    private Size savedSize;

    Window() {
        this.setSize(1024, 768);
        this.setTitle("Project 1");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.figures = new ArrayList<>();

        this.savedSize = this.getWindowSize();

        Thread changeRadius = new Thread(() -> {
            while (true) {
                Position position = new Position(
                        (new Random()).nextInt(this.getWidth()) + 1,
                        (new Random()).nextInt(this.getHeight()) + 1
                );
                Figure figure = new Circle(position, this.getWindowSize(), (new Random()).nextInt(100) + 1);
                this.figures.add(figure);
                this.repaint(position.x, position.y, figure.getSize().width + 1, figure.getSize().height + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        changeRadius.start();
    }

    public Size getWindowSize() {
        return new Size(this.getWidth(), this.getHeight());
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        Size currentSize = this.getWindowSize();
        boolean needToScale = false;
        if (!savedSize.equals(currentSize)) {
            needToScale = true;
        }

        for (Figure figure : this.figures) {
            if (needToScale) {
                figure.scale(currentSize);
            }

            figure.render(graphics);
        }

        System.out.println(this.figures.size());
    }
}

class Position {
    final int x;
    final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Size {
    final int width;
    final int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean equals(Size windowSize) {
        return this.width == windowSize.width && this.height == windowSize.height;
    }
}