class Size {
    final int width;
    final int height;

    Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String toString() {
        return width + "x" + height;
    }
}
