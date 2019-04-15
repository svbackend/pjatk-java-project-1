import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

class Window extends JFrame {

    private ArrayList<Figure> figures;

    private Button stopAndExportBtn;
    private Button clearScreenBtn;
    private Button importFromFileBtn;

    private final String EXPORT_FILE_PATH;

    boolean isAddFiguresThreadRunning = true;

    Window() {
        this.setSize(1024, 768);
        this.setTitle("Project 1");
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.EXPORT_FILE_PATH = "./figures.txt";
        this.figures = new ArrayList<>();

        this.registerButtons();
    }

    private void registerButtons() {
        this.stopAndExportBtn = new Button("Stop and Export to file");
        this.stopAndExportBtn.setBounds(5, 25, 200, 30);
        this.stopAndExportBtn.addActionListener((event) -> {
            try {
                FigureExporter.exportToFile(this.figures, this.EXPORT_FILE_PATH);
                this.isAddFiguresThreadRunning = false;
                this.stopAndExportBtn.setEnabled(false);
                this.importFromFileBtn.setEnabled(true);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        this.clearScreenBtn = new Button("Clear screen");
        this.clearScreenBtn.setBounds(205, 25, 100, 30);
        this.clearScreenBtn.addActionListener((e) -> {
            this.figures.clear();
            this.repaint();
        });

        this.importFromFileBtn = new Button("Import from file");
        this.importFromFileBtn.setEnabled(false);
        this.importFromFileBtn.setBounds(315, 25, 200, 30);
        this.importFromFileBtn.addActionListener((event) -> {
            try {
                this.figures = FigureImporter.importFromFile(EXPORT_FILE_PATH);
                this.scaleFigures();
                this.repaint();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        this.add(this.stopAndExportBtn);
        this.add(this.clearScreenBtn);
        this.add(this.importFromFileBtn);
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
