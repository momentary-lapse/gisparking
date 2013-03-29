/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dao;

import java.util.List;
import models.entities.Phone;
import models.interfaces.IPhoneDAO;
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
    public List<Phone> getList() {
        
        return template.find("from Phone");
    }
    
}