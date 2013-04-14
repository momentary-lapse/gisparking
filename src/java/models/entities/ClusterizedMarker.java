/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

public class ClusterizedMarker extends Marker {
    
    int cluster;
    double distance;

    /**
     * Рассчитать дистанцию от точки (например, эвакуатора) до маркера.<br>
     * Используется географическая формула, расстояние по прямой между
     * десятичными координатами
     *
     * @param lat Широта
     * @param lng Долгота
     */
    public void calculateDistance(double lat, double lng) {
        double dLat = Math.toRadians(lat - getLat());
        double dLon = Math.toRadians(lng - getLng());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
                * Math.sin(dLon / 2) * Math.cos(Math.toRadians(getLat())) * Math.cos(Math.toRadians(lat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance = 6371 * c;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
    
    
    
}
