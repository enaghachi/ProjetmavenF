/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import org.codehaus.jackson.annotate.JsonIgnore;



@Entity
public class Tweet implements Serializable{
 
	private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        
	// TIMESTAMP, contrairement à DATE, stocke l'heure en plus du jour/mois/année
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date datepublication ;
        
	private String label;
	private String sujet;
	private String Taguser;
        
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name="user_username")
        private Utilisateur user;//@JoinColumn(name="userID")//,referencedColumnName="username")
	
        
        public Tweet(){    
        }
        public Tweet(Date datepublication, String label,Utilisateur user){
             this.datepublication = datepublication;
             this.label = label;
             this.user=user;
        }
        

        public Tweet(Date datepublication, String label, String sujet, String Taguser, Utilisateur user) {
            
            this.datepublication = datepublication;
            this.label = label;
            this.sujet = sujet;
            this.Taguser = Taguser;
            this.user = user;
        }



        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Date getDatepublication() {
            return datepublication;
        }

        public void setDatepublication(Date datepublication) {
            this.datepublication = datepublication;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getSujet() {
            return sujet;
        }

        public void setSujet(String sujet) {
            this.sujet = sujet;
        }

        public String getTaguser() {
            return Taguser;
        }

        public void setTaguser(String Taguser) {
            this.Taguser = Taguser;
        }

        public Utilisateur getUser() {
            return user;
        }

        public void setUser(Utilisateur user) {
            this.user = user;
        }



    }