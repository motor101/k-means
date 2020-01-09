import java.util.HashSet;
import java.util.Set;

class Cluster {
    private Point mean;
    Set<Point> points;

    Cluster(Point mean) {
        this.mean = mean;
        this.points = new HashSet<>();
    }

    void clear() {
        points.clear();
    }

    void add(Point point) {
        points.add(point);
    }

    double getDistance(Point point) {
        return mean.getDistance(point);
    }

    void calculateNewMean() {
        int pointsCount = points.size();

        if (pointsCount == 0) {
            return;
        }

        double sumX = 0;
        double sumY = 0;

        for (Point point : points) {
            sumX += point.x;
            sumY += point.y;
        }

        mean = new Point(sumX / pointsCount, sumY / pointsCount);
    }

    double getVariation() {
        int pointsCount = points.size();

        if (pointsCount == 0) {
            return 0.0;
        }

        double variation = 0;

        for (Point point : points) {
            variation += point.getDistanceSquare(mean);
        }

        return variation / pointsCount;
    }
}
