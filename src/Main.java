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

            Thread addFiguresThread = new Thread(() -> {
                FigureFactory figureFactory = new FigureFactory();
                while (true) {
                    Figure figure = figureFactory.createRandomFigure(window.getWindowSize());
                    window.addFigure(figure);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            addFiguresThread.start();
        });
    }
}

class FigureFactory {
    private Random random;

    FigureFactory() {
        this.random = new Random();
    }

    public Figure createRandomFigure(Size windowSize) {
        Position position = new Position(
                this.random.nextInt(windowSize.width-1) + 1,
                this.random.nextInt(windowSize.height-1) + 1
        );

        Figure figure = new Circle(position, windowSize, random.nextInt(100) + 1);

        return figure;
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

    public void scale(Size newWindowSize) {
        this.position = new Position(
                (int) (newWindowSize.width / this.positionXRatio),
                (int) (newWindowSize.height / this.positionYRatio)
        );
        this.maxSize = new Size(newWindowSize.width - this.position.x, newWindowSize.height - this.position.y);
        this.recalculateSize(newWindowSize);
    }

    public Position getPosition() {
        return position;
    }

    protected Size getMaxSize() {
        return this.maxSize;
    }

    abstract void recalculateSize(Size newWindowSize);
}

class Circle extends Figure {
    private int radius;
    private double radiusRatio;
    private Color color;

    public Circle(Position position, Size windowSize, int radius) {
        super(position, windowSize);

        this.setRadius(radius);

        if (windowSize.width < windowSize.height) {
            this.radiusRatio = (double)windowSize.width / this.radius;
        } else {
            this.radiusRatio = (double)windowSize.height / this.radius;
        }

        Random random = new Random();
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private void setRadius(int radius) {
        int diameter = radius * 2;
        if (diameter > getMaxSize().width) {
            diameter = getMaxSize().width;
        }

        if (diameter > getMaxSize().height) {
            diameter = getMaxSize().height;
        }

        this.radius = (diameter / 2) > 0 ? (diameter / 2) : 1;
    }

    void recalculateSize(Size newWindowSize) {
        int radius;
        if (newWindowSize.width < newWindowSize.height) {
            radius = (int)(newWindowSize.width / this.radiusRatio);
        } else {
            radius = (int)(newWindowSize.height / this.radiusRatio);
        }

        this.setRadius(radius);
    }

    public void paint(Graphics graphics) {
        Position position = this.getPosition();
        int diameter = this.radius * 2;
        graphics.setColor(this.color);
        graphics.fillOval(position.x, position.y, diameter, diameter);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "position=" + getPosition() +
                "radius=" + radius +
                ", radiusRatio=" + radiusRatio +
                ", color=" + color +
                '}';
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
        this.repaint();
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
        graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
        //super.paint(graphics);

        System.out.println("--START--");

        for (Figure figure : this.figures) {
            figure.paint(graphics);
            System.out.println(figure);
        }

        System.out.println("--END--");
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

    public String toString() {
        return width + "x" + height;
    }
}