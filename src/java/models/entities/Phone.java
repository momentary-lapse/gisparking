/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PHONES")
public class Phone implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private Long id;
    
    @Column(name="phone", nullable=false)
    private String phone;
    
    @Column(name="priority", nullable=false)
    private int priority;
    
        
}