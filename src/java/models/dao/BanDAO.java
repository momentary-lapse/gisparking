/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dao;

import java.util.List;
import models.entities.Ban;
import models.interfaces.IBanDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class BanDAO implements IBanDAO {
    
    HibernateTemplate template;

    @Autowired
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }    
    
   
    @Transactional
    @Override
    public void add(Ban ban) {
        template.save(ban);
        template.flush();
    }
    
    @Transactional
    @Override
    public void delete(Ban ban) {
        template.delete(ban);
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
    public void update(Ban ban) {
        template.saveOrUpdate(ban);
        template.flush();
    }
    
    @Transactional
    @Override
    public Ban getById(Long id) {
        return (Ban) template.get(Ban.class, id);
    }
    
    @Transactional
    @Override
    public Ban getByPhone(Long phoneId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Ban.class).add(Property.forName("pid").eq(phoneId));
        List<Ban> list =  template.findByCriteria(criteria);
        
        if (list.isEmpty()) {
            return null;
        }
        
        return list.get(0);
        
    }
    
    @Transactional
    @Override
    public List<Ban> getList() {
        
        return template.find("from Ban");
    }
    
}
