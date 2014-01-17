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

 function bindEventsOnReady(){
     var sujet = $('#sujet').val();
     console.log(sujet);
     bindListTweetSujetEvent(sujet);
 }
 
 function bindListTweetSujetEvent(sujet){
     $.get(rootURL+"/Tweet/"+sujet,
                    function(data){
                        $('#message').html('<h2>la liste des Tweets sur le sujet'+ sujet +' </h2>');
                         $.each(data,function(key, val){
                             $("#les_name").append(val.user.username);
                             $("#tweets").append(val.label);
                            });
                    },"json");
 }

$().ready(function(){
 bindEventsOnReady();
});



