/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MarkerDAO implements IMarkerDAO {

    HibernateTemplate template;

    @Autowired
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
   
    @Transactional
    @Override
    public void add(Marker marker) {
        template.save(marker);
        template.flush();
    }
    
    @Transactional
    @Override
    public void delete(Marker marker) {
        template.delete(marker);
        template.flush();
    }
    
    @Transactional
    @Override
    public void deleteById(Long id) {
        List<Marker> markers = template.findByNamedParam("from MARKERS", "id", id);
        if (markers.isEmpty()) {
            return;
        }
        template.delete(markers.get(0));
        template.flush();
    }
    
    @Transactional
    @Override
    public void update(Marker marker) {
        template.saveOrUpdate(marker);
        template.flush();
    }
    
    @Transactional
    @Override
    public Marker getById(Long id) {
        return (Marker) template.get(Marker.class, id);
    }
    
    @Transactional
    @Override
    public List<Marker> getList() {
        return template.find("from Marker");
    }
    
}
