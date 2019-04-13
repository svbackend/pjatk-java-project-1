import javax.swing.*;

abstract class Figure extends JComponent implements IExportable {
    private Position position;
    private Size maxSize;
    private double positionXRatio;
    private double positionYRatio;
    public static final String EXPORT_DELIMITER = getExportDelimiter();

    Figure(Position position, Size windowSize) {
        this.position = position;
        this.maxSize = new Size(windowSize.width - this.position.x, windowSize.height - this.position.y);
        this.positionXRatio = (double) windowSize.width / position.x;
        this.positionYRatio = (double) windowSize.height / position.y;
    }

    Figure(double positionXRatio, double positionYRatio) {
        this.positionXRatio = positionXRatio;
        this.positionYRatio = positionYRatio;
    }

    void scale(Size newWindowSize) {
        this.position = new Position(
                (int) (newWindowSize.width / this.positionXRatio),
                (int) (newWindowSize.height / this.positionYRatio)
        );
        this.maxSize = new Size(newWindowSize.width - this.position.x, newWindowSize.height - this.position.y);
        this.recalculateSize(newWindowSize);
    }

    Position getPosition() {
        return position;
    }

    Size getMaxSize() {
        return this.maxSize;
    }

    public double getPositionXRatio() {
        return positionXRatio;
    }

    public double getPositionYRatio() {
        return positionYRatio;
    }

    private static String getExportDelimiter() {
        return ";";
    }

    public String generateExportStringFromData(String[] params) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String param : params) {
            stringBuilder
                    .append(param)
                    .append(getExportDelimiter());
        }

        return stringBuilder.toString();
    }

    abstract void recalculateSize(Size newWindowSize);

    public abstract String exportAsString();
}
