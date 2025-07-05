// Import and configure the Firebase SDK
// These scripts are made available when the app is served or deployed on Firebase Hosting
// If you do not serve/host your project using Firebase Hosting see https://firebase.google.com/docs/web/setup
//importScripts('https://www.gstatic.com/firebasejs/7.3.0/firebase-app.js');
//importScripts('https://www.gstatic.com/firebasejs/7.3.0/firebase-messaging.js');
//importScripts('https://www.gstatic.com/firebase/init.js');

//const messaging = firebase.messaging();

 //Worker when your app is not hosted on Firebase Hosting.

 // [START initialize_firebase_in_sw]
 // Give the service worker access to Firebase Messaging.
 // Note that you can only use Firebase Messaging here, other Firebase libraries
 // are not available in the service worker.
 importScripts('firebase-app.js');
 importScripts('firebase-messaging.js');
 /*importScripts('static/js/jquery-3.2.1.min.js');*/
 
 var messagingSenderId = new URL(location).searchParams.get('messagingSenderId');

 // Initialize the Firebase app in the service worker by passing in the
 // messagingSenderId.
 firebase.initializeApp({
   'messagingSenderId': messagingSenderId
 });

 // Retrieve an instance of Firebase Messaging so that it can handle background
 // messages.
 const messaging = firebase.messaging();
 // [END initialize_firebase_in_sw]
 


// If you would like to customize notifications that are received in the
// background (Web app is closed or not in browser focus) then you should
// implement this optional method.
// [START background_handler]
messaging.setBackgroundMessageHandler(function(payload) {
  console.log('Please work');
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png'
  };
  
  var listener = new BroadcastChannel('listener');
  listener.postMessage(payload);

  return self.registration.showNotification(notificationTitle,
    notificationOptions);
});
// [END background_handler]
