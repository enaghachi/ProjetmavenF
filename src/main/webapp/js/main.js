// The root URL for the RESTful services
var rootURL = "http://localhost:9000/Tweety/resources";

//inscription d’un user 
function bindEventsOnReady() {

$('#adduser').click(function() {
        adduser();
        return false;
});

//connection
$('#login').click(function(){
  // alert('login');
   connection();
   return false;
});

}


function connection() {
    var usernamelog = $('#usernameconnct').val();
    var passwordlog = $('#passwordconnect').val();  
    if ( usernamelog.length < 1 || passwordlog.length < 1 ){
        
        $('#resultat').html('</br><h4>oups! le username et le mot de passe ne doivent pas être vide</h4> </br>');
        
    }else{
        
        login($('#usernameconnct').val(),$('#passwordconnect').val());
        
    }
    return false;
}

//fonction de connection
function login(username,password){
        console.log('username'+username);
        alert('username'+username+'password'+password);
        $.ajax({
                type: 'GET',
                url: rootURL +'/user/'+ username+'/'+password,
                dataType: "json",
                success: function(data){
                    
                    alert(data.toString());
                        alert('user connected successfully');
                        window.location.href="tweety.html";
                    },
               
                 error: function(jqXHR, textStatus, errorThrown){
                     
                     alert("status&&&&&"+jqXHR.status); //affiche le code d erreur
                     
                        //remplace le contenu de la div  
                        $('#resultat').html('</br><h4>oups! username ou mot de passe incorrecte! Essaye encore une fois </h4> </br>');
           
                 }
        });      
        
}

//fonction inscription
function adduser() {
        console.log('addUser');
        alert('rootURL'+$("#username").val());
        $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: rootURL+'/user/add',
                dataType: "json",
                data: formToJSON(),
                success: function(data, textStatus, jqXHR){
                        alert('user created successfully');
                        $("#inscription")[0].reset(); // vider les champs du formulaire 
                       window.location.href="login.html";
                },
                error: function(jqXHR, textStatus, errorThrown){
                        alert('addUser error: ' + textStatus);
                }
        });      
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
        return JSON.stringify({
                "username": $('#username').val(), 
                "password": $('#password').val(),
                "sexe": $('#sexe').val(),
                "email": $('#email').val(),
                "date_inscription": new Date()
                });
}

 
 
$().ready(function(){
 bindEventsOnReady();
});

