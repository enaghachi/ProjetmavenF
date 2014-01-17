/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.resources;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import jee.Status;
import jee.model.Tweet;
import jee.model.Utilisateur;


/**
 *
 * @author ighachane
 */
@Path("/Tweet")
public class TweetResource {
    
    @POST 
    @Path("/add")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, "application/json","application/xml"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTweet(@CookieParam("authCookie") Cookie authenciateCookie,MultivaluedMap<String, String> inFormParams){
           if (authenciateCookie == null) {
			return Response.status(new Status(Status.USER_OFFLINE)).build();
		}
           String contenu = inFormParams.getFirst("areaTweet");
           String userconnecte = inFormParams.getFirst("usernameonline");
           Utilisateur usernew = Ebean.find(Utilisateur.class).where()
                                                               .eq("username",userconnecte).findUnique();
           
           Tweet saveTweet = new Tweet();
           saveTweet.setDatepublication(new Date());
           saveTweet.setUser(usernew);
            if(!contenu.contains("@")){
        	 if(!contenu.contains("#") ){
        		 saveTweet.setLabel(contenu);
        		 saveTweet.setSujet("");
        	 }
        	 else{
        		Pattern p1 = Pattern.compile("(.*) #(.*)");
                        Matcher m1 = p1.matcher(contenu);
                        while(m1.find()){
        			saveTweet.setLabel(m1.group(1));
        			saveTweet.setSujet(m1.group(2));
        				}		 
                    }
            }else{
        	 if(!contenu.contains("#") ){
		        	 Pattern p2 = Pattern.compile("(.*) @(.*)");
		        	 Matcher m2 = p2.matcher(contenu);
		        	 while(m2.find()){
		        		 saveTweet.setLabel(m2.group(1));
		        		 saveTweet.setSujet("");
		    			 saveTweet.setTaguser(m2.group(2));
		  			}
        	 }
        	 else{
        		 Pattern p3 = Pattern.compile("(.*) @(.*) #(.*)");
        		 Matcher m3 = p3.matcher(contenu);
        		 while(m3.find()){
        			 saveTweet.setLabel(m3.group(1));
        			 saveTweet.setSujet(m3.group(3));
        			 saveTweet.setTaguser(m3.group(2));
                                           }
                      }
                }	
            Ebean.save(saveTweet); 
            return Response.status(new Status(Status.OK)).build();
    }
    
    @GET
    @Path("{userconnecte}/{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tweet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @PathParam("userconnecte") String username) {
        
        int[] range = new int[]{from, to};
        Query list = Ebean.find(Tweet.class).where().eq("user_username", username).orderBy("datepublication");
        list.setMaxRows(range[1]-range[0]);
        list.setFirstRow(range[0]);
        return list.findList();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Tweet find(@PathParam("id") Long id) {
        return Ebean.find(Tweet.class).where().eq("id", id).findUnique();
    }
    
    @DELETE
    @Path("{id}")
    public String remove(@PathParam("id") Long id) {
        List<Tweet> Tweets = Ebean.find(Tweet.class).findList();
        Tweets.remove(id); 
        return ""+id;
    }
    
    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST(@CookieParam("authCookie") Cookie authenciateCookie) {
        String username = authenciateCookie.getValue();
        return String.valueOf(Ebean.find(Tweet.class).where().eq("user_username", username).findList().size());
    }
    
    @GET
    @Path("{sujet}")
    @Produces({"application/json"})
    public List<Tweet> getTweets(@CookieParam("authCookie") Cookie authenciateCookie,@PathParam("id") String sujet){
        List<Tweet> Tweets = Ebean.find(Tweet.class).where().eq("sujet",sujet).findList();
        return Tweets;
    }
    
    @GET @Path("/get")
    @Produces( MediaType.APPLICATION_JSON )
    public List<Tweet> getTweets(@CookieParam("authCookie") Cookie authenciateCookie){
        List<Tweet> Tweets;
        if (authenciateCookie == null) {
			//return Response.status(new Status(Status.USER_OFFLINE)).build();
                System.out.println("vous avez pas le droit vous etes deconnecté");
                Tweets = null;
		}
        else{
                Tweets = Ebean.find(Tweet.class).findList(); 
        }
        return Tweets;
    }
    
}
