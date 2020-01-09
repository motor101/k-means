public class Point {
    double x;
    double y;
    int clusterIndex = -1;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", centroidIndex=" + clusterIndex +
                '}';
    }

    double getDistanceSquare(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;

        return dx * dx + dy * dy;
    }

    double getDistance(Point other) {
        return Math.sqrt(getDistanceSquare(other));
    }
}
