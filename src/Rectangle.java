import java.awt.*;
import java.util.Random;

class Rectangle extends Figure {
    private int aSideLength;
    private int bSideLength;
    private double aSideLengthRatio;
    private double bSideLengthRatio;
    private Color color;

    Rectangle(Position position, Size windowSize, int aSideLength, int bSideLength) {
        super(position, windowSize);

        this.setASideLength(aSideLength);
        this.setBSideLength(bSideLength);

        if (windowSize.width < windowSize.height) {
            this.aSideLengthRatio = (double)windowSize.width / this.aSideLength;
            this.bSideLengthRatio = (double)windowSize.width / this.bSideLength;
        } else {
            this.aSideLengthRatio = (double)windowSize.height / this.aSideLength;
            this.bSideLengthRatio = (double)windowSize.height / this.bSideLength;
        }

        Random random = new Random();
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    Rectangle(double positionXRatio, double positionYRatio, double aSideLengthRatio, double bSideLengthRatio, Color color) {
        super(positionXRatio, positionYRatio);
        this.aSideLengthRatio = aSideLengthRatio;
        this.bSideLengthRatio = bSideLengthRatio;
        this.color = color;
    }

    private void setASideLength(int sideLength) {
        if (sideLength > getMaxSize().width) {
            sideLength = getMaxSize().width;
        }

        this.aSideLength = sideLength;
    }

    private void setBSideLength(int sideLength) {
        if (sideLength > getMaxSize().height) {
            sideLength = getMaxSize().height;
        }

        this.bSideLength = sideLength;
    }

    void recalculateSize(Size newWindowSize) {
        int aSideLength;
        int bSideLength;

        if (newWindowSize.width < newWindowSize.height) {
            aSideLength = (int) (newWindowSize.width / this.aSideLengthRatio);
            bSideLength = (int) (newWindowSize.width / this.bSideLengthRatio);
        } else {
            aSideLength = (int) (newWindowSize.height / this.aSideLengthRatio);
            bSideLength = (int) (newWindowSize.height / this.bSideLengthRatio);
        }

        this.setASideLength(aSideLength);
        this.setBSideLength(bSideLength);
    }

    public void paint(Graphics graphics) {
        Position position = this.getPosition();
        graphics.setColor(this.color);
        graphics.fillRect(position.x, position.y, this.aSideLength, this.bSideLength);
    }

    public String exportAsString() {
        String figureName = this.getClass().getSimpleName();

        String[] exportData = {
                figureName,
                Double.toString(getPositionXRatio()),
                Double.toString(getPositionYRatio()),
                Double.toString(this.aSideLengthRatio),
                Double.toString(this.bSideLengthRatio),
                Integer.toString(this.color.getRed()),
                Integer.toString(this.color.getGreen()),
                Integer.toString(this.color.getBlue()),
        };

        return this.generateExportStringFromData(exportData);
    }
}
