// Scripts for firebase and firebase messaging
importScripts('https://www.gstatic.com/firebasejs/8.2.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.2.0/firebase-messaging.js');

// Initialize the Firebase app in the service worker by passing the generated config
const firebaseConfig = {
    apiKey: "AIzaSyCVDr-BoFtOIMx5uJ8Am02HReW1R76PO2M",
    authDomain: "dconnect-c8928.firebaseapp.com",
    projectId: "dconnect-c8928",
    storageBucket: "dconnect-c8928.appspot.com",
    messagingSenderId: "354805874788",
    appId: "1:354805874788:web:da5b8d880eb97e6ff09f71",
    measurementId: "G-EZ2NQQ01SV"
};

firebase.initializeApp(firebaseConfig);

// Retrieve firebase messaging
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
  // console.log('Received background message ', payload);

  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  self.registration.showNotification(notificationTitle,
    notificationOptions);
});