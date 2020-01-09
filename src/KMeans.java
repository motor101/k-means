import java.util.Random;
import java.util.Scanner;

public class KMeans {
    private static Scanner input = new Scanner(System.in);
    private static Random random = new Random();

    private final Point[] points;
    private Cluster[] clusters = null;
    private int clustersCount;
    private double variantionsSum;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    private KMeans(Point[] points, int clustersCount) {
        this.points = points;
        this.clustersCount = clustersCount;
        calculateClustersBorders();
    }

    private void calculateClustersBorders() {
        double minXLocal = Double.MAX_VALUE;
        double minYLocal = Double.MAX_VALUE;

        double maxXLocal = Double.MIN_VALUE;
        double maxYLocal = Double.MIN_VALUE;

        for (Point point : points) {
            if (point.x < minXLocal) {
                minXLocal = point.x;
            }

            if (point.y < minYLocal) {
                minYLocal = point.y;
            }

            if (point.x > maxXLocal) {
                maxXLocal = point.x;
            }

            if (point.y > maxYLocal) {
                maxYLocal = point.y;
            }
        }

        minX = (int) Math.floor(minXLocal);
        minY = (int) Math.floor(minYLocal);

        maxX = (int) Math.ceil(maxXLocal);
        maxY = (int) Math.ceil(maxYLocal);
    }

    private void calculateNewClusterDistribution() {
        clusters = new Cluster[clustersCount];
        for (int i = 0; i < clustersCount; i++) {
            double randomX = minX + random.nextDouble() * (maxX - minX);
            double randomY = minY + random.nextDouble() * (maxY - minY);

            Point initialClusterMean = new Point(randomX, randomY);

            clusters[i] = new Cluster(initialClusterMean);
        }

        boolean convergence = false;
        while (!convergence) {
            convergence = true;

            for (int i = 0; i < clustersCount; i++) {
                clusters[i].clear();
            }

            for (Point point : points) {
                double minDistance = Double.MAX_VALUE;
                int clusterIndex = -1;

                for (int i = 0; i < clustersCount; i++) {
                    double distance = clusters[i].getDistance(point);

                    if (distance < minDistance) {
                        minDistance = distance;
                        clusterIndex = i;
                    }
                }

                if (point.clusterIndex != clusterIndex) {
                    convergence = false;
                    point.clusterIndex = clusterIndex;
                }

                clusters[point.clusterIndex].add(point);
            }

            if (!convergence) {
                for (int i = 0; i < clustersCount; i++) {
                    clusters[i].calculateNewMean();
                }
            }
        }

        calculateVariationSum();
    }

    private void calculateVariationSum() {
        variantionsSum = 0.0;

        for (Cluster cluster : clusters) {
            variantionsSum += cluster.getVariation();
        }
    }

    private Cluster[] getBestClusterDistribution(int iterationsCount) {
        double minVariationSum = Double.MAX_VALUE;
        Cluster[] bestClusterDistribution = null;

        for (int i = 0; i < iterationsCount; i++) {
            calculateNewClusterDistribution();

            if (variantionsSum < minVariationSum) {
                minVariationSum = variantionsSum;
                bestClusterDistribution = clusters;
            }
        }

        return bestClusterDistribution;
    }

    private static Point[] readPoints(int pointsCount) {
        Point[] points = new Point[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            double x = Double.parseDouble(input.next());
            double y = Double.parseDouble(input.next());
            points[i] = new Point(x, y);
        }

        return points;
    }

    private void printOutput(Cluster[] bestClusterDistribution) {
        System.out.println(points.length);

        for (Cluster cluster : bestClusterDistribution) {
            for (Point point : cluster.points) {
                System.out.println(point.x);
                System.out.println(point.y);
                System.out.println(point.clusterIndex);
            }
        }
    }

    public static void main(String[] args) {
        int clustersCount = Integer.parseInt(args[0]);
        int iterationsCount = Integer.parseInt(args[1]);
        int pointsCount = Integer.parseInt(input.next());

        Point[] points = readPoints(pointsCount);
        KMeans kMeans = new KMeans(points, clustersCount);

//        System.out.println("clustersCount = " + clustersCount);
//        System.out.println("iterationsCount = " + iterationsCount);
//        System.out.println("pointsCount = " + pointsCount);
//
//        System.out.println(kMeans.maxX);
//        System.out.println(kMeans.maxY);
//        System.out.println(kMeans.minX);
//        System.out.println(kMeans.minY);


        Cluster[] bestClusterDistribution = kMeans.getBestClusterDistribution(iterationsCount);

        kMeans.printOutput(bestClusterDistribution);
    }

}