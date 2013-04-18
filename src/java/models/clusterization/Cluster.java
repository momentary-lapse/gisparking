/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.clusterization;

import java.util.ArrayList;

class Cluster implements Comparable<Cluster> {

    private ArrayList<Integer> numberOfPointInClaster;
    private double costOfCenter;
    private int numberOfCluster;

    public Cluster(ArrayList<Integer> NumberOfPointInClaster,
            double costOfCenter) {
        this.numberOfPointInClaster = NumberOfPointInClaster;
        this.costOfCenter = costOfCenter;
    }

    public Cluster(int numberOfCluster, double costOfCenter) {
        this.costOfCenter = costOfCenter;
        this.numberOfCluster = numberOfCluster;
        this.numberOfPointInClaster = new ArrayList<Integer>();
    }

    public int getNumberOfCluster() {
        return numberOfCluster;
    }

    public void setNumberOfCluster(int numberOfCluster) {
        this.numberOfCluster = numberOfCluster;
    }

    public ArrayList<Integer> getNumberOfPointInClaster() {
        return numberOfPointInClaster;
    }

    public void setNumberOfPointInClaster(
            ArrayList<Integer> numberOfPointInClaster) {
        this.numberOfPointInClaster = numberOfPointInClaster;
    }

    public double getCostOfCenter() {
        return costOfCenter;
    }

    public void setCostOfCenter(double costOfCenter) {
        this.costOfCenter = costOfCenter;
    }

    public void addToListPoint(int number) {
        this.numberOfPointInClaster.add(new Integer(number));
    }

    @Override
    public int compareTo(Cluster arg0) {
        int result = 0;
        if (this.costOfCenter == arg0.costOfCenter) {
            result = 0;
        }
        if (this.costOfCenter < arg0.costOfCenter) {
            result = -1;
        }
        if (this.costOfCenter > arg0.costOfCenter) {
            result = 1;
        }
        return result;
    }
}
