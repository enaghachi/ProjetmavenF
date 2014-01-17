/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;


/**
 *
 * @author bdiop
 */

@Entity 
public class Abonnement  implements Serializable {
        
    @Id
    private Long id;
    
    private String username_ajout;
    @Temporal(javax.persistence.TemporalType.DATE)
        private Date date_ajout ;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name="Proprio_username")
        private Utilisateur user;
           
    
    public Abonnement() {
        }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the email_ajout
     */
    public String getUsername_ajout() {
        return username_ajout;
    }

    /**
     * @param email_ajout the email_ajout to set
     */
    public void setUsername_ajout(String username_ajout) {
        this.username_ajout = username_ajout;
    }

    /**
     * @return the date_ajout
     */
    public Date getDate_ajout() {
        return date_ajout;
    }

    /**
     * @param date_ajout the date_ajout to set
     */
    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    /**
     * @return the user
     */
    public Utilisateur getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Utilisateur user) {
        this.user = user;
    }

        
        
        
}