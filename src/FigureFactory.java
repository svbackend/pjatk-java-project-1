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
}
