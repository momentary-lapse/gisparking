/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.clusterization;

import java.util.Arrays;
import java.util.Iterator;
import models.entities.ClusterizedMarker;

public class Clusterizator {

    class Point {

        ClusterizedMarker marker;
        double cost = 0;

        public Point(ClusterizedMarker marker) {
            this.marker = marker;
        }

        public Point() {
        }
    }
    private ClusterizedMetricAssistant assist = new ClusterizedMetricAssistant();
    private Cluster firstCluster = new Cluster(0, 0);
    private Cluster secondCluster = new Cluster(0, 0);
    private Cluster thirdCluster = new Cluster(0, 0);
    private boolean isThreeClusters = false;
    private double thresholdChangeCenter = 0.1;

    public void clusterize(ClusterizedMarker[] markers) {
        boolean isNeedRecalculate = true;
        double oldCostOfCenter = 0.0;

        if (markers.length <= 2) {
            for (int i = 0; i < markers.length; i++) {
                markers[i].setCluster(1);
            }
            return;
        }

        Point[] points = initialize(markers);
        assist.initialize(points);
        if (markers.length > 5) {
            isThreeClusters = true;
        }

        firstCluster.setCostOfCenter(points[0].cost);
        secondCluster.setCostOfCenter(points[1].cost);

        if (isThreeClusters) {
            thirdCluster.setCostOfCenter(points[2].cost);
        }

        fillTheList(points);

        while (isNeedRecalculate) {
            isNeedRecalculate = false;
            oldCostOfCenter = firstCluster.getCostOfCenter();
            firstCluster.setCostOfCenter(recalculateTheMassCenters(points,
                    firstCluster));
            if (Math.abs(firstCluster.getCostOfCenter() - oldCostOfCenter) > thresholdChangeCenter) {
                isNeedRecalculate = true;
            }

            oldCostOfCenter = secondCluster.getCostOfCenter();
            secondCluster.setCostOfCenter(recalculateTheMassCenters(points,
                    secondCluster));
            if (Math.abs(secondCluster.getCostOfCenter() - oldCostOfCenter) > thresholdChangeCenter) {
                isNeedRecalculate = true;
            }

            if (isThreeClusters) {
                oldCostOfCenter = thirdCluster.getCostOfCenter();
                thirdCluster.setCostOfCenter(recalculateTheMassCenters(points,
                        thirdCluster));
                if (Math.abs(thirdCluster.getCostOfCenter() - oldCostOfCenter) > thresholdChangeCenter) {
                    isNeedRecalculate = true;
                }
            }

            firstCluster.getNumberOfPointInClaster().clear();
            secondCluster.getNumberOfPointInClaster().clear();
            thirdCluster.getNumberOfPointInClaster().clear();

            fillTheList(points);
        }

        Cluster[] clusterArray = {firstCluster, secondCluster, thirdCluster};
        Arrays.sort(clusterArray);
        int number = 1;
        for (int i = 0; i < 3; i++) {
            if (clusterArray[i].getCostOfCenter() != 0) {
                clusterArray[i].setNumberOfCluster(number);
                number++;
            }
        }

        if (firstCluster.getNumberOfCluster() != 0) {
            Iterator<Integer> iterator = firstCluster
                    .getNumberOfPointInClaster().iterator();
            while (iterator.hasNext()) {
                Integer numberOfPoint = iterator.next();
                markers[numberOfPoint].setCluster(firstCluster
                        .getNumberOfCluster());
            }
        }

        if (secondCluster.getNumberOfCluster() != 0) {
            Iterator<Integer> iterator_2 = secondCluster
                    .getNumberOfPointInClaster().iterator();
            while (iterator_2.hasNext()) {
                Integer numberOfPoint = iterator_2.next();
                markers[numberOfPoint].setCluster(secondCluster
                        .getNumberOfCluster());
            }
        }

        if (thirdCluster.getNumberOfCluster() != 0) {
            Iterator<Integer> iterator_3 = thirdCluster
                    .getNumberOfPointInClaster().iterator();
            while (iterator_3.hasNext()) {
                Integer numberOfPoint = iterator_3.next();
                markers[numberOfPoint].setCluster(thirdCluster
                        .getNumberOfCluster());
            }
        }
    }

    private Point[] initialize(ClusterizedMarker[] markers) {
        Point[] points = new Point[markers.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(markers[i]);
        }
        return points;
    }

    private int findNearestCenter(Point points[], int x) {
        double min;
        int number = 0;
        min = Math.abs(points[x].cost - firstCluster.getCostOfCenter());
        number = 1;
        if ((Math.abs(points[x].cost - secondCluster.getCostOfCenter())) < min) {
            min = Math.abs(points[x].cost - secondCluster.getCostOfCenter());
            number = 2;
        }
        if (isThreeClusters) {
            if ((Math.abs(points[x].cost - thirdCluster.getCostOfCenter())) < min) {
                min = Math.abs(points[x].cost - thirdCluster.getCostOfCenter());
                number = 3;
            }
        }
        return number;
    }

    private double recalculateTheMassCenters(Point points[], Cluster cluster) {
        double sum = 0, finalCost = 0;

        Iterator<Integer> iterator = cluster.getNumberOfPointInClaster()
                .iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            sum = sum + points[number].cost;
        }
        finalCost = sum / cluster.getNumberOfPointInClaster().size();
        return finalCost;
    }

    private void fillTheList(Point points[]) {
        int NumberOfNearestCenter = 0;
        for (int i = 0; i < points.length; i++) {
            NumberOfNearestCenter = findNearestCenter(points, i);
            switch (NumberOfNearestCenter) {
                case 1:
                    firstCluster.addToListPoint(i);
                    break;
                case 2:
                    secondCluster.addToListPoint(i);
                    break;
                case 3:
                    thirdCluster.addToListPoint(i);
            }
        }
    }
}
