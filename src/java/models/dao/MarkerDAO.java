package models.dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Timestamp;
import java.util.List;
import models.entities.Marker;
import models.interfaces.IMarkerDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
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
        template.delete(getById(id));
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
    public List<Marker> getEnabledList() {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Marker.class).add(Property.forName("enabled").eq(1));
        return template.findByCriteria(criteria);
    }
    
    @Transactional
    @Override
    public List<Marker> getFullList() {
        
        return template.find("from Marker");
    }
    
    @Transactional
    @Override
    public int getPhoneRequestsNumberByTime(String phone, Timestamp timestamp) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Marker.class).add(Property.forName("phone").eq(phone));
        criteria.add(Property.forName("stamp").ge(timestamp));
        return template.findByCriteria(criteria).size();
        
    }
    
    @Transactional
    @Override
    public Marker getLastQuery(String phone) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Marker.class).add(Property.forName("phone").eq(phone));
        List<Marker> list =  template.findByCriteria(criteria);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
        
    }
    
    @Transactional
    @Override
    public List<Marker> getOldMarkers(Timestamp timestamp) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Marker.class).add(Property.forName("stamp").le(timestamp));
        return template.findByCriteria(criteria);
        
    }
    
}
