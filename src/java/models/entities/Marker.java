/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MARKERS")
public class Marker implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private Long id;
    
    @Column(name="north", nullable=false)
    private double north;
    
    @Column(name="east", nullable=false)
    private double east;
    
    @Column(name="phone", nullable=false)
    private String phone;
    
    @Column(name="url", nullable=false)
    private String url;
    
    @Column(name="stamp", nullable=true)
    private Timestamp stamp;
    
    @Column(name="enabled", nullable=false)
    private int enabled;
    
    @Column(name="address", nullable=false)
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getStamp() {
        return stamp;
    }

    public void setStamp(Timestamp stamp) {
        this.stamp = stamp;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
        
}
