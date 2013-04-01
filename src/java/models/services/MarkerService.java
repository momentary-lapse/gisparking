/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.services;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import models.entities.Marker;
import models.interfaces.IMarkerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarkerService {
    
    public class GeoComparator implements Comparator<Marker> {
        
        private double lat, lng;

        public GeoComparator(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }        
        
        @Override
        public int compare(Marker a, Marker b) {
            double dista = Math.abs(lat - a.getLat()) + Math.abs(lng - a.getLng());
            double distb = Math.abs(lat - b.getLat()) + Math.abs(lng - b.getLng());
            return Double.compare(dista, distb);
        }
        
        
    }
    

    private IMarkerDAO markerDAO;

    @Autowired
    public void setMarkerDAO(IMarkerDAO markerDAO) {
        this.markerDAO = markerDAO;
    }
    
    public void add(Marker marker) {
        markerDAO.add(marker);
    }
    
    public void update(Marker marker) {
        markerDAO.update(marker);
    }
    
    public void delete(Marker marker) {
        markerDAO.delete(marker);
    }
    
    public void deleteById(Long id) {
        markerDAO.deleteById(id);
    }
    
    public Marker getById(Long id) {
        return markerDAO.getById(id);
    }
    
    
    public List<Marker> getEnabledList() {
        return markerDAO.getEnabledList();
    }
    
    public List<Marker> getFullList() {
        return markerDAO.getFullList();
    }
    
    public List<Marker> getSortedList(double lat, double lng) {
        
        GeoComparator comp = new GeoComparator(lat, lng);
        List<Marker> list = markerDAO.getEnabledList();
        Collections.sort(list, comp);
        return list;
        
    }
    
    public int getPhoneRequestsNumberByTime(String phone, Timestamp timestamp) {
        
        return markerDAO.getPhoneRequestsNumberByTime(phone, timestamp);
        
    }
    
    public Marker getLastQuery(String phone) {
        
        return markerDAO.getLastQuery(phone);
        
    }
    
}
