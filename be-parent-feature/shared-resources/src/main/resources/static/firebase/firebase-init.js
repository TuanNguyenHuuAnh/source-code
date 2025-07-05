$(document).ready(function(){
	//var companyId = $('#companyid-login').val();
	var haveNotify = false;
	
	//console.log("GET FIREBASE CONFIG")
	//console.log(result);
	// Your web app's Firebase configuration
	var firebaseConfig = {
		apiKey: APIKEY,
	    authDomain: AUTHDOMAIN,
	    databaseURL: DATABASEURL,
	    projectId: PROJECTID,
	    storageBucket: STORAGEBUCKET,
	    messagingSenderId: MESSAGINGSENDERID,
	    appId: APPID,
	    measurementId: FIREBASEPUBLICVAPIDKEY
  	};
	
	// Initialize Firebase
    firebase.initializeApp(firebaseConfig);
	
 	// Retrieve Firebase Messaging object.
    const messaging = firebase.messaging();
 	
    var messagingSenderId = MESSAGINGSENDERID;
    //console.log("messagingSenderId " + messagingSenderId);
    navigator.serviceWorker.register(BASE_URL + 'static/firebase/firebase-messaging-sw.js?messagingSenderId=' + messagingSenderId)
    .then( function(registration) {
      
    	messaging.useServiceWorker(registration)
		
    	// Add the public key generated from the console here.
	    messaging.usePublicVapidKey(FIREBASEPUBLICVAPIDKEY);
	 	
	 	// Requesting permission
	    //console.log('Requesting permission...');
	    Notification.requestPermission().then( function (permission) {
	        if (permission === 'granted') {
	          console.log('Notification permission granted.');
	          // TODO(developer): Retrieve an Instance ID token for use with FCM.
	          resetUI();
	          // [END_EXCLUDE]
	        } else {
	          console.log('Unable to get permission to notify.');
	        }
	    });
    });
 	
    //Refresh registration token
    messaging.onTokenRefresh( function() {
        messaging.getToken().then( function (refreshedToken) {
          console.log('Token refreshed.');
          // Indicate that the new Instance ID token has not yet been sent to the
          // app server.
          setTokenSentToServer(false);
          // Send Instance ID token to app server.
          sendTokenToServer(refreshedToken);
          resetUI();
        }).catch(function(err) {
          console.log('Unable to retrieve refreshed token ', err);
          showToken('Unable to retrieve refreshed token ', err);
        });
      });
    
    function resetUI() {
	 	 // [START get_token]
	 	 // Get Instance ID token. Initially this makes a network call, once retrieved
	 	 // subsequent calls to getToken will return from cache.
	 	  messaging.getToken().then( function(currentToken) {
	 	   showToken(currentToken);
	 	   console.log(currentToken);
	 	   if (currentToken) {
	 	      sendTokenToServer(currentToken);
	 	   } else {
	 	   // Show permission request.
	 	      console.log('No Instance ID token available. Request permission to generate one.');
	 	      setTokenSentToServer(false);
	 	   }
	 	   }).catch( function(err) {
	 	      console.log('An error occurred while retrieving token. ', err);
	 	       showToken('Error retrieving Instance ID token. ', err);
	 	       setTokenSentToServer(false);
	 	   });
	 }
	
	function playSound(){
		console.log("sound");
		var filename = $("#sound-resource-path").val();
		var mp3Source = '<source src="' + filename + '" type="audio/mpeg"/>';
		document.getElementById("sound").innerHTML='<audio autoplay="autoplay">' + mp3Source + '</audio>';
	}	
    
    
 	// Handle forground messages
 	 messaging.onMessage( function(payload) {
      console.log('Message received. ', payload);
      var badge = payload.data.badge;
      /*var currentBadge = parseInt($("#notify-count").text());
      var totalBadge = currentBadge + 1;*/
      $("#notify-count").html(badge);
      haveNotify = true;
      playSound();
      // ...
    });
 	
 	//Listen message from service worker
 	var listener = new BroadcastChannel('listener');
	listener.onmessage = function(e) {
  		console.log('Got message from service worker',e);
  		var payload = e.data;
  		var badge = payload.data.badge;
  		/*var currentBadge = parseInt($("#notify-count").text());
  		var totalBadge = currentBadge + 1;*/
        $("#notify-count").html(badge);
        haveNotify = true;
	};
	
	function loadMessWhenReceivingNofity(){
		$(".gr-notifi").hover(function(){
			updateBadge();
			
		})
	}
	
	loadMessWhenReceivingNofity();
	
 	 
 	 function showToken(currentToken) {
      // Show token in console and UI.
      const tokenElement = document.querySelector('#token');
      //tokenElement.textContent = currentToken;
     }
 	 
 	 //SendTokenToServer
 	function sendTokenToServer(currentToken) {
 	  var accountId = $("#accountid-login").val();
      /*if (!isTokenSentToServer()) {
        console.log('Sending token to server...');     
        // TODO(developer): Send the current token to your server.
        $.ajax({
        	type: "POST",
        	url: BASE_URL + "notification/saveDeviceToken",
        	data: {
        		"accountId": accountId,
        		"deviceToken": currentToken
        	},success: function(result){
        		console.log(result);
        	},error: function(error){
        		console.log(error);
        	}
        })
        setTokenSentToServer(true);
      } else {
        console.log('Token already sent to server so won\'t send it again ' +
          'unless it changes');
       $.ajax({
        	type: "POST",
        	url: BASE_URL + "notification/checkLoginAccountIdForUpdate",
        	data: {
        		"loginAccountId": accountId,
        		"deviceToken": currentToken
        	},
        	success: function(result){
        		console.log(result);
        	},
        	error: function(error){
        		console.log(error);
        	}
        });
      }*/
 	  console.log('Sending token to server...');     
 	  $.ajax({
 		  type: "POST",
 		  url: BASE_URL + "notification/saveDeviceToken",
 		  data: {
 			  "accountId": accountId,
 			  "deviceToken": currentToken
 		  },
 		  success: function(result){
 			  console.log(result);
 		  },
 		  error: function(error){
 			  console.log(error);
 		  }
 	  });
 	  setFireBaseToken(currentToken);
    }
 	 
 	 function isTokenSentToServer() {
 	    return window.localStorage.getItem('sentToServer') === '1';
 	 }

 	 function setTokenSentToServer(sent) {
 	    window.localStorage.setItem('sentToServer', sent ? '1' : '0');
 	 } 
 	 
}); 

function getFireBaseToken() {
	return window.localStorage.getItem('firebaseToken');
}

function setFireBaseToken(token) {
    window.localStorage.setItem('firebaseToken', token);
} 

