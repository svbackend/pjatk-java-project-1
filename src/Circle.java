import java.awt.*;
import java.util.Random;

class Circle extends Figure {
    private int radius;
    private double radiusRatio;
    private Color color;

    Circle(Position position, Size windowSize, int radius) {
        super(position, windowSize);

        this.setRadius(radius);

        if (windowSize.width < windowSize.height) {
            this.radiusRatio = (double) windowSize.width / this.radius;
        } else {
            this.radiusRatio = (double) windowSize.height / this.radius;
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
            radius = (int) (newWindowSize.width / this.radiusRatio);
        } else {
            radius = (int) (newWindowSize.height / this.radiusRatio);
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
