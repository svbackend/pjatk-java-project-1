import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Window extends JFrame {

    private ArrayList<Figure> figures;

    Window() {
        this.setSize(1024, 768);
        this.setTitle("Project 1");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.figures = new ArrayList<>();
    }

    synchronized void addFigure(Figure figure) {
        this.figures.add(figure);
        this.add(figure);
        this.repaint();
    }

    Size getWindowSize() {
        return new Size(this.getWidth(), this.getHeight());
    }

    synchronized void scaleFigures() {
        for (Figure figure : this.figures) {
            figure.scale(new Size(this.getWidth(), this.getHeight()));
        }
    }


    public synchronized void paint(Graphics graphics) {
        graphics.clearRect(0, 0, this.getWidth(), this.getHeight());

        System.out.println("--START--");

        for (Figure figure : this.figures) {
            figure.paint(graphics);
            System.out.println(figure);
        }

        System.out.println("--END--");
    }
}
