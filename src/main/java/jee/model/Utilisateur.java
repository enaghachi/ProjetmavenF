/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.model;
import com.avaje.ebean.validation.Email;
import com.google.common.collect.Constraints;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
     
public class Utilisateur implements Serializable{
        private static final long serialVersionUID = 1L;
        @Id
	private String username;
        
	private String email;
        
        private String password;
	
	private String sexe;
        
	// TIMESTAMP, contrairement à DATE, stocke l'heure en plus du jour/mois/année
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date date_inscription ;
	
	@OneToMany(mappedBy="user")  //un User pour plusieurs tweet, "mappedBy" qui référence le nom de l'attribut User dans la classe Even
        @JsonIgnore
        private List<Tweet> TweetList;
	
	public Utilisateur(){
             
	}
	
	  public Utilisateur(String username, String password, String sexe,
			String adresseemail, Date date_inscription) {
		super();
		this.username = username;
		this.password = password;
		this.sexe = sexe;
		this.email = adresseemail;
		this.date_inscription = date_inscription;
	}

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sexe
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * @return the date_inscription
     */
    public Date getDate_inscription() {
        return date_inscription;
    }

    /**
     * @param date_inscription the date_inscription to set
     */
    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the TweetList
     */
   @JsonIgnore
    public List<Tweet> getTweetList() {
        return TweetList;
    }
   
    public void setTweetList(List<Tweet> TweetList) {
        this.TweetList = TweetList;
    }
    


    

     
	

}
