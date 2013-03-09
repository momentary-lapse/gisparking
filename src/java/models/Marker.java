/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

public class Marker {
    
    private long id;
    private double north, east;
    private String info;
    
    public Marker(double north, double east, String info) {
        
        this.north = north;
        this.east = east;
        this.info = info;
        
    }
    
    public Marker(double north, double east) {
        
        this.north = north;
        this.east = east;
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    public double getNorth() {
        return north;
    }

    public double getEast() {
        return east;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    
    public void setEast(double east) {
        this.east = east;
    }
    
    
    
    
}
