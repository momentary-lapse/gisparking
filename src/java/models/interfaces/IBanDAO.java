/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.interfaces;

import java.util.List;
import models.entities.Ban;

public interface IBanDAO {
    
    public void add(Ban ban);
    public void deleteById(Long id);
    public void delete(Ban ban);
    public void update(Ban ban);
    public Ban getById(Long id);
    public Ban getByPhone(Long phoneId);
    public List<Ban> getList();
    
}
