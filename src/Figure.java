import javax.swing.*;

abstract class Figure extends JComponent {
    private Position position;
    private Size maxSize;
    private double positionXRatio;
    private double positionYRatio;

    Figure(Position position, Size windowSize) {
        this.position = position;
        this.maxSize = new Size(windowSize.width - this.position.x, windowSize.height - this.position.y);
        this.positionXRatio = (double) windowSize.width / position.x;
        this.positionYRatio = (double) windowSize.height / position.y;
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

    abstract void recalculateSize(Size newWindowSize);
}
