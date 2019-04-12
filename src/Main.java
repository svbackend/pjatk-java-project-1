import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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