/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.services;

import models.entities.Marker;
import java.util.List;
import models.entities.Phone;
import models.interfaces.IPhoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    
    
    private IPhoneDAO phoneDAO;

    @Autowired
    public void setMarkerDAO(IPhoneDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }
    
    public void add(Phone phone) {
        phoneDAO.add(phone);
    }
    
    public void update(Phone phone) {
        phoneDAO.update(phone);
    }
    
    public void delete(Phone phone) {
        phoneDAO.delete(phone);
    }
    
    public void deleteById(Long id) {
        phoneDAO.deleteById(id);
    }
    
    public Phone getById(Long id) {
        return phoneDAO.getById(id);
    }
    
    
    public List<Phone> getList() {
        return phoneDAO.getList();
    }
    
}

