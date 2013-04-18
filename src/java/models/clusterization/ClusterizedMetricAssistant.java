/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.clusterization;

import java.util.Date;
import models.clusterization.Clusterizator.Point;
import models.entities.ClusterizedMarker;

/**
 *
 * @author Админ
 */
class ClusterizedMetricAssistant {

    public void initialize(Point[] points) {
        long t = (new Date()).getTime();
        for (Point p : points) {
            ClusterizedMarker m = p.marker;
            p.cost = m.getDistance();
            p.cost += getTimeCost((double) (t - m.getStamp().getTime())
                    / (1000 * 60 * 60));
            p.cost += getPriorityCost(m.getPriority());
            p.cost += getZoneCost(m.getLat(), m.getLng());
        }
    }

    private double getZoneCost(double lat, double lng) {
        double priority = 3;
        for (Rectangle r : areas) {
            if (r.coord[0] >= lat && r.coord[2] <= lat && r.coord[1] >= lng
                    && r.coord[3] <= lng) {
                priority = r.priority;
            }
        }
        return (--priority * 1.2);
    }

    private double getTimeCost(double hours) {
        if (hours < 0.6) {
            return (5 * hours * (75 * (9 - 10 * hours) * hours + 521) - 164) / 1640;
        } else if (hours < 1) {
            return (hours * (5 * hours * (385 * hours - 1233) + 7403) - 1195) / 1312;
        } else {
            return (hours * (65 * (hours - 9) * hours + 1823) + 665) / 1312;
        }

    }

    private double getPriorityCost(int priority) {
        return (--priority * 0.5);
    }
    private Rectangle[] areas = new Rectangle[]{
        new Rectangle(1, new double[]{55.170153, 61.389421, 55.158163,
            61.417573}),
        new Rectangle(2, new double[]{55.161407, 61.3635, 55.157181,
            61.38882}),
        new Rectangle(2, new double[]{55.147744, 61.408818, 55.139337,
            61.417058}),
        new Rectangle(2, new double[]{55.17872, 61.391801, 55.170566,
            61.405534})};

    private static class Rectangle {
        public double[] coord;
        public int priority;

        public Rectangle(int priority, double[] coord) {
            this.priority = priority;
            this.coord = coord;
        }
    }
}
