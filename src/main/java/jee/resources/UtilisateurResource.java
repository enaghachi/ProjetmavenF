/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.resources;

import jee.MD5Password;
import jee.Status;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.annotation.Transactional;
import java.security.NoSuchAlgorithmException;
import jee.model.Utilisateur;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import jee.model.Abonnement;



/**
 *
 * @author IGHACHANE
 */
@Path("/user")
public class UtilisateurResource {
    
    
    @POST
    @Path("/add")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Utilisateur adduser(Utilisateur user) {
         String passwordhashe = MD5Password.getEncodedPassword(user.getPassword());
          Utilisateur saveuser = new Utilisateur();
          saveuser.setUsername(user.getUsername());
          saveuser.setPassword(passwordhashe);
          saveuser.setSexe(user.getSexe());
          saveuser.setEmail(user.getEmail());
          saveuser.setDate_inscription(new Date());
          Ebean.save(saveuser);
          System.out.println("creating user");
          return saveuser;
    }
    
    
    @GET @Path("/get")
    @Produces( MediaType.APPLICATION_JSON )
    public List<Utilisateur> getusers(){
        List<Utilisateur> users = Ebean.find(Utilisateur.class).findList();
        return users;
    }
    
    
    @GET @Path("{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
        public Response login(@CookieParam("authCookie") Cookie authenciateCookie,@PathParam("username") String username, @PathParam("password") String password) throws NoSuchAlgorithmException {
            
            if (authenciateCookie != null && authenciateCookie.getValue().equals(username)) 
            {
             return Response.status(new Status(Status.USER_ONLINE)).build();
            }
                    System.out.println("findById " + username);
                    String passwordhashe = MD5Password.getEncodedPassword(password);
                    Utilisateur user = Ebean.find(Utilisateur.class).where()
                                                                    .eq("username", username)
                                                                    .eq("password", passwordhashe).findUnique();
                    if(user != null){
                        NewCookie cookie = new NewCookie("authCookie",user.getUsername(), "/", "localhost", "", 1000, false);
                        return Response.ok(user, MediaType.APPLICATION_JSON).status(Status.OK).cookie(cookie).build();
                    }else{
                        return Response.status(new Status(Status.USER_NO_ACCOUNT)).build();
                    }

        }
        
  @GET @Path("/logout")
  @Produces( MediaType.APPLICATION_JSON)
  public Response logout() {        
    NewCookie cookie = new NewCookie("authCookie", "-1", "/", "localhost", "", 0, false);
    return Response.status(new Status(Status.OK)).cookie(cookie).build();
    
  }
    @GET @Path("/pageAmis/{username}")
    @Produces( MediaType.APPLICATION_JSON )
    public List<Abonnement> getAbonnements(@PathParam("username") String username){
         List<Abonnement> abonnements = Ebean.find(Abonnement.class).where()
                                                               .eq("Proprio_username",username).findList();
         return abonnements;
    }
    
        @GET @Path("/pageAbonne/{username}")
    @Produces( MediaType.APPLICATION_JSON )
    public List<Abonnement> getAbonne(@PathParam("username") String username){
         List<Abonnement> abonnes = Ebean.find(Abonnement.class).where()
                                                               .eq("username_ajout",username).findList();
         return abonnes;
    }
    
        
    
        
   
}
