/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// The root URL for the RESTful services
var rootURL = "http://localhost:9000/Tweety/resources";

function getCookieVal(offset) {
	var endstr=document.cookie.indexOf (";", offset);
	if (endstr==-1)
      		endstr=document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

 function GetCookie (name) {
      var arg = name + "=";
      var alen = arg.length;
      var clen = document.cookie.length;
      var i = 0;
      while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
          return getCookieVal (j);
    	i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break; 
      }
      return null;
    }

//inscription d’un user 
function bindEventsOnReady() {
                      
                        bindLogoutEvent();    
                        bindaddTweetEvent();
                        var CookieValue = GetCookie("authCookie");
                        bindListTweetEvent(CookieValue);
                        removeLoadMore(CookieValue);
                        binddeleteTweetEvent();
                        bindPassPageAmisEvent(CookieValue);
}
function bindLogoutEvent() {
$('#Logout').click(function(){
    logout();
    return false;
 });
}

//fonction de deconnection
function logout(){
    console.log('deconnection');
    $.ajax({
         type: 'GET',
         url: rootURL +'/user/logout',
         dataType: "json",
         success: function(data){
                    console.log("vous etes deconnecté");
                    window.location.href="index.html";
                  
                },
         error: function(jqXHR, textStatus, errorThrown){
             console.log("problème de deconnection:"+errorThrown);
         }
    });
}


//------------------------------------Les amies-------------------------------------
//mes abonnement
function bindPassPageAmisEvent(username){
     console.log("bindPassPageAmisEvent:"+username);
    $('#mes_amis').click(function(){
       alert('mes_aime'+username);
       pageAmis(username);
       return false;
    });
}
//function pageGetAmis(){
//    var username = $('#usernamePageAmis').val();
//    pageAmis(username)
//}
function pageAmis(username){
    console.log('pageAmis');
    $.ajax({
        type:'GET',
        url: rootURL +'/user/pageAmis/'+username,
        dataType: "json",
        success :  function(data, textStatus, jqXHR){
           $('#center_frame').html('<p></p>');
           $('#main_contant').html('      <div class="center_frame">'+
            '<h2>Mes abonnements</h2>'+
             '<div class="box_2">'+ 
                '<div id="List_Abonnement">'+
                    '<div class="text">'+
                         ' <table class="table-striped">'+
                            ' <tr><th>Username</th></tr>'+ 
                            '<div id="les_name"></div>'+
                            ' </table>'+
                        '</div> '+
                 '</div>'+
               '</div>'+
            '</div>');
            $.each(data,function(key, val){
                 $("#les_name").prepend(renderItemAmis(val.id, val.username_ajout, val.date_ajout, val.user.username));
               });
           },
        error : function(resultat, statut, erreur){
             alert("not ok");
             alert("status"+resultat.status); //affiche le code d erreur
         }
    });
}
//page abonne
function bindPassPageAbonneEvent(username){
     console.log("bindPassPageAmisEvent:"+username);
    $('#mes_abonne').click(function(){
       alert('mes_abonne'+username);
       pageAbonne(username);
       return false;
    });
}

function pageAbonne(username){
    console.log('pageAbonne');
    $.ajax({
        type:'GET',
        url: rootURL +'/user/pageAbonne/'+username,
        dataType: "json",
        success :  function(data, textStatus, jqXHR){
           $('#center_frame').html('<p></p>');
           $('#main_contant').html('      <div class="center_frame">'+
            '<h2>Mes abonnes</h2>'+
                '<div class="box_2">'+ 
                '<div id="List_Abonnement">'+
                    '<div class="text">'+
                         ' <table class="table-striped">'+
                            ' <tr><th>Username</th></tr>'+ 
                            '<div id="les_name"></div>'+
                           ' </table>'+
                        '</div> '+
                 '</div>'+
                 '</div>'+
            '</div>');
            $.each(data,function(key, val){
                 $("#les_name").prepend(renderItemAmis(val.id, val.username_ajout, val.date_ajout, val.user.username));
               });
           },
        error : function(resultat, statut, erreur){
             alert("not ok");
             alert("status"+resultat.status); //affiche le code d erreur
         }
    });
}
//-------------------------------------Tweet------------------------------------------
//deconnection
function bindaddTweetEvent() {
$('#publier_button').click(function(){
    //console.log("idididididiididididididi");
    console.log("addTweet");
    addTweet();
    return false;
 });
}
//fonction addTweet
function addTweet(){
    console.log('addUser');
    console.log('Tweet '+$("#areaTweet").val());
    console.log('username '+$('#usernameonline').val());
    var data = $("#form-addTweet").serializeArray();
    $.ajax({
                type: 'POST',
                //contentType:'application/json',
                url: rootURL + '/Tweet/add',
                //dataType: "json",  // Le type de données à recevoir de service, ici, du json.
                data:data, //FormaddtweetToJSON(),
                success : function(d, textStatus, jqXHR){
                    console.log("tweet ajouté_add tweet");
                    // On ajoute le Tweet dans la page
                    console.log("avantprepend_add tweet");
                    window.location.href="tweety.html";
                },
                error : function(resultat, statut, erreur){
                    console.log("tweet non ajouté");
                    console.log("status"+resultat.status); //affiche le code d erreur
                    
                    
                }
            });
}
//affichage liste de Tweet d'un user
function bindListTweetEvent(username){
    
    $.get(rootURL+"/Tweet/"+username+"/0/5",
                    function(data){
        if(data.length != 0) 
        {
             $.each(data,function(key, val){
             
                $("#List_Tweet").prepend(renderItem(val.id, val.label, val.sujet, val.datepublication, val.Taguser, val.user.username));
             });
             console.log("ajoutfini");  
        }else{
                $("#loadmore").remove();
                showWelcome();
             }
       
    },"json");
    }
    
function binddeleteTweetEvent() {
   // Clic sur le bouton delete pour supprimer un Tweet
    $(".delete").live("click",function(){

        var id = $(this).attr("href");
        console.log(id);

        $.ajax(id,
        {
            type:"DELETE",
            success: function(d){
                $("#Tweet-"+d).slideUp('slow',function(){
                    $(this).remove();
                    });
            }
        });
        
        if(removeLoadMore()){
            $("#loadmore").remove();
        }
        
        return false;

    });
}
function showWelcome(){
        $("#List_Tweet").html('<b>Vous avez pas de Tweets à afficher </b>');
    } 
//suppression du lien loadMore   
//suppression du lien loadMore   

function removeLoadMore(username)
    {
        //var username = document.cookie.split("=")[2];
        $.get(rootURL+"/Tweet/count",function(data){
            var i = $("#List_Tweet").children().length;
            console.log("dans la bd : "+data+" | sur le site : "+i);
            if(data <= 5){
                console.log("true");
                $("#loadmore").remove();
            }else{
               $("<button  id='Loadmore' value='"+username+"'>Loadmore</button>").insertAfter("#main_contant");
                console.log("false");
               bindEventsLoadMore();
            }
                
        });

    }
    
    function bindEventsLoadMore() 
    {
	$('#Loadmore').click(function() {
           username=$(this).val();
           console.log(username);
	    LoadMore(username);
	});
    }

    function LoadMore(username){
	console.log("loadMore");  
        var From = $("#List_Tweet").children().length;
        console.log("From"+From);
        var To = From+1;
        console.log("TO"+To);
    
        $.get(rootURL+"/Tweet/"+username+"/"+From+"/"+To,
    					
			function(data){
				
				if(data.length != 0) 
				{				
					$.each(data, function(key, val) 
						{
						$("#List_Tweet").append(renderItem(val.id, val.label, val.sujet, val.datepublication, val.Taguser, val.user.username));
						});
				}else{
					$("#Loadmore").remove();
					$(".errormessage").append('<b> il n\'y a pas d\'autre Tweet à afficher</b>');
				}
			},"json");
              }
 // creation et ajout d'un article dans la page
 function renderItem(id, label, sujet, date, Taguser,userpropr)
    {
        
        var myDate = new Date( date );
        var strDate = "";
        strDate += myDate.getUTCDate()+"/"+(myDate.getMonth()+1)+"/"+myDate.getFullYear();
        strDate += " A "+myDate.getHours()+":"+myDate.getMinutes();
        HTML = '<div class="box_2"><img src="bootstrap/img/box_2.png" alt=""  class="main_img_2" />'+
                                                 '<div class="text">'+
                                                  ' <h6>'+userpropr+'</h6>'+
                                                   '<h5>'+strDate+'</h5>'+
                                                   '<p>'+label+
                                                   '<input type="hidden" name="sujet" id="sujet" value="'+sujet+'">';
                                           if(sujet != ""){
                                               HTML=HTML+'<a href="sujetTweet.html"> #'+sujet+'</a>';
                                           }
                                           if (!sujet.equals("undefined")){
                                               HTML=HTML+' @'+Taguser;
                                           }
                                           HTML=HTML+'</p>'+
                                                 '</div></div>';      
                                                   
                       return HTML;                             
         
    }
    
     
$().ready(function(){
 bindEventsOnReady();
});

