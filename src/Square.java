import java.awt.*;
import java.util.Random;

class Square extends Figure {
    private int sideLength;
    private double sideLengthRatio;
    private Color color;

    Square(Position position, Size windowSize, int sideLength) {
        super(position, windowSize);

        this.setSideLength(sideLength);

        if (windowSize.width < windowSize.height) {
            this.sideLengthRatio = (double) windowSize.width / this.sideLength;
        } else {
            this.sideLengthRatio = (double) windowSize.height / this.sideLength;
        }

        Random random = new Random();
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    Square(double positionXRatio, double positionYRatio, double sideLengthRatio, Color color) {
        super(positionXRatio, positionYRatio);
        this.sideLengthRatio = sideLengthRatio;
        this.color = color;
    }

    private void setSideLength(int sideLength) {
        if (sideLength > getMaxSize().width) {
            sideLength = getMaxSize().width;
        }

        if (sideLength > getMaxSize().height) {
            sideLength = getMaxSize().height;
        }

        this.sideLength = sideLength;
    }

    void recalculateSize(Size newWindowSize) {
        int sideLength;

        if (newWindowSize.width < newWindowSize.height) {
            sideLength = (int) (newWindowSize.width / this.sideLengthRatio);
        } else {
            sideLength = (int) (newWindowSize.height / this.sideLengthRatio);
        }

        this.setSideLength(sideLength);
    }

    public void paint(Graphics graphics) {
        Position position = this.getPosition();
        graphics.setColor(this.color);
        graphics.fillRect(position.x, position.y, this.sideLength, sideLength);
    }

    public String exportAsString() {
        String figureName = this.getClass().getSimpleName();

        String[] exportData = {
                figureName,
                Double.toString(getPositionXRatio()),
                Double.toString(getPositionYRatio()),
                Double.toString(this.sideLengthRatio),
                Integer.toString(this.color.getRed()),
                Integer.toString(this.color.getGreen()),
                Integer.toString(this.color.getBlue()),
        };

        return this.generateExportStringFromData(exportData);
    }
}
