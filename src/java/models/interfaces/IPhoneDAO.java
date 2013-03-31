/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.interfaces;

import java.util.List;
import models.entities.Phone;


public interface IPhoneDAO {
    public void add(Phone phone);
    public void deleteById(Long id);
    public void delete(Phone phone);
    public void update(Phone phone);
    public Phone getById(Long id);
    public Phone getByPhone(String phone);
    public List<Phone> getList();
}