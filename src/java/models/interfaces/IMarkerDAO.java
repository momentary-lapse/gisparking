/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.interfaces;

import java.sql.Timestamp;
import java.util.List;
import models.entities.Marker;


public interface IMarkerDAO {
    public void add(Marker marker);
    public void deleteById(Long id);
    public void delete(Marker marker);
    public void update(Marker marker);
    public Marker getById(Long id);
    public List<Marker> getEnabledList();
    public List<Marker> getFullList();
    public int getPhoneRequestsNumberByTime(Long pid, Timestamp timestamp);
    public List<Marker> getOldMarkers(Timestamp timestamp);
}
