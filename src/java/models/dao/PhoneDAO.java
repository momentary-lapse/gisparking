/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dao;

import java.util.List;
import models.entities.Marker;
import models.entities.Phone;
import models.interfaces.IPhoneDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PhoneDAO implements IPhoneDAO {

    HibernateTemplate template;

    @Autowired
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    
   
    @Transactional
    @Override
    public void add(Phone phone) {
        template.save(phone);
        template.flush();
    }
    
    @Transactional
    @Override
    public void delete(Phone phone) {
        template.delete(phone);
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
    public void update(Phone phone) {
        template.saveOrUpdate(phone);
        template.flush();
    }
    
    @Transactional
    @Override
    public Phone getById(Long id) {
        return (Phone) template.get(Phone.class, id);
    }
    
    @Transactional
    @Override
    public Phone getByPhone(String phone) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Phone.class).add(Property.forName("phone").eq(phone));
        List<Phone> list = template.findByCriteria(criteria);
        
        if (list.isEmpty()) {
            return null;
        }
        
        return (Phone) list.get(0);
    }
    
    @Transactional
    @Override
    public List<Phone> getList() {
        
        return template.find("from Phone");
    }
    
}