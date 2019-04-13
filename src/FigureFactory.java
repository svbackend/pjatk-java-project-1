import java.awt.*;
import java.security.InvalidParameterException;
import java.util.Random;

class FigureFactory {
    private Random random;

    FigureFactory() {
        this.random = new Random();
    }

    Figure createRandomFigure(Size windowSize) {
        Position position = new Position(
                this.random.nextInt(windowSize.width-1) + 1,
                this.random.nextInt(windowSize.height-1) + 1
        );

        Figure figure;
        switch (this.random.nextInt(2)) {
            case 0:
                figure = new Circle(position, windowSize, random.nextInt(100) + 1);
                break;
            case 1:
                figure = new Square(position, windowSize, random.nextInt(100) + 1);
                break;
            default:
                figure = new Square(position, windowSize, random.nextInt(100) + 1);
                break;
        }

        return figure;
    }

    static Figure importFigureFromString(String exportedFigure) {
        String[] figureData = exportedFigure.split(Figure.EXPORT_DELIMITER);

        Figure figure;
        Color color;
        double positionXRatio = Double.valueOf(figureData[1]);
        double positionYRatio = Double.valueOf(figureData[2]);

        // At 0 index always must be class name
        switch (figureData[0]) {
            case "Circle":
                double radiusRatio = Double.valueOf(figureData[3]);
                color = new Color(Integer.valueOf(figureData[4]), Integer.valueOf(figureData[5]), Integer.valueOf(figureData[6]));
                figure = new Circle(positionXRatio, positionYRatio, radiusRatio, color);
                break;
            case "Square":
                double sideLengthRatio = Double.valueOf(figureData[3]);
                color = new Color(Integer.valueOf(figureData[4]), Integer.valueOf(figureData[5]), Integer.valueOf(figureData[6]));
                figure = new Square(positionXRatio, positionYRatio, sideLengthRatio, color);
                break;
            default:
                throw new InvalidParameterException("Cannot create Figure from provided string!");
        }

        return figure;
    }
}
