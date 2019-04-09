import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Window window = new Window();

            window.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    window.scaleFigures();
                }
            });

            Thread addFigures = new Thread(() -> {
                Random random = new Random();
                while (true) {
                    Position position = new Position(
                            random.nextInt(window.getWidth()) + 1,
                            random.nextInt(window.getHeight()) + 1
                    );
                    Figure figure = new Circle(position, window.getWindowSize(), random.nextInt(100) + 1);
                    window.addFigure(figure);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            addFigures.start();
        });
    }
}

abstract class Figure extends JComponent {
    private Position position;
    private Size maxSize;
    private double positionXRatio;
    private double positionYRatio;

    public Figure(Position position, Size windowSize) {
        this.position = position;
        this.maxSize = new Size(windowSize.width - this.position.x, windowSize.height - this.position.y);
        this.positionXRatio = (double)windowSize.width / position.x;
        this.positionYRatio = (double)windowSize.height / position.y;
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

    abstract Size getFigureSize();

    abstract Color getColor();

    abstract void recalculateSize(Size newWindowSize);

    //abstract void render(Graphics graphics);
}

class Circle extends Figure {
    private int radius;
    private double radiusRatio;
    private Size size;
    private Color color;

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

        if (windowSize.width < windowSize.height) {
            this.radiusRatio = (double)windowSize.width / this.radius;
        } else {
            this.radiusRatio = (double)windowSize.height / this.radius;
        }

        Random random = new Random();
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        this.calculateSize();
    }

    public Size getFigureSize() {
        return this.size;
    }

    private void calculateSize() {
        this.size = new Size(this.radius * 2, this.radius * 2);
    }

    void recalculateSize(Size newWindowSize) {
        if (newWindowSize.width < newWindowSize.height) {
            this.radius = (int)(newWindowSize.width / this.radiusRatio);
        } else {
            this.radius = (int)(newWindowSize.height / this.radiusRatio);
        }

        this.calculateSize();
    }

    public Color getColor() {
        return color;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        Position position = this.getPosition();
        int diameter = this.radius * 2;
        graphics.setColor(this.getColor());
        graphics.fillOval(position.x, position.y, diameter, diameter);
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
    }

    public synchronized void addFigure(Figure figure) {
        this.figures.add(figure);
        this.add(figure);
        SwingUtilities.invokeLater(this::repaint);
    }

    public Size getWindowSize() {
        return new Size(this.getWidth(), this.getHeight());
    }

    public synchronized void scaleFigures() {
        for (Figure figure : this.figures) {
            figure.scale(new Size(this.getWidth(), this.getHeight()));
        }
    }


    public synchronized void paint(Graphics graphics) {
        //graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
        super.paint(graphics);

        for (Figure figure : this.figures) {
            figure.paint(graphics);
        }

    }
}

class Position {
    final int x;
    final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
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

    public String toString() {
        return width + "x" + height;
    }
}