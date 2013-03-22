/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;


public interface IMarkerDAO {
    public void add(Marker marker);
    public void deleteById(Long id);
    public void delete(Marker marker);
    public void update(Marker marker);
    public Marker getById(Long id);
    public List<Marker> getList();
}
