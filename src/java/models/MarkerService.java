/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkerService {

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
    
    
    public List<Marker> getList() {
        return markerDAO.getList();
    }
    
}
