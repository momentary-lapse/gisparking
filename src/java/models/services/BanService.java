/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.services;

import java.util.List;
import models.entities.Ban;
import models.interfaces.IBanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanService {
    
    
    private IBanDAO banDAO;

    @Autowired
    public void setBanDAO(IBanDAO banDAO) {
        this.banDAO = banDAO;
    }
    
    public void add(Ban ban) {
        banDAO.add(ban);
    }
    
    public void update(Ban ban) {
        banDAO.update(ban);
    }
    
    public void delete(Ban ban) {
        banDAO.delete(ban);
    }
    
    public void deleteById(Long id) {
        banDAO.deleteById(id);
    }
    
    public Ban getById(Long id) {
        return banDAO.getById(id);
    }
    
    public Ban getByPhone(Long phoneId) {
        return banDAO.getByPhone(phoneId);
    }
    
    public List<Ban> getList() {
        return banDAO.getList();
    }
    
}

