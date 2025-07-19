// Firebase Cloud Messaging Configuration File. 
// Read more at https://firebase.google.com/docs/cloud-messaging/js/client && https://firebase.google.com/docs/cloud-messaging/js/receive

import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
import {USER_DEVICE_TOKEN} from '../constants';
import { setSession } from '../util/common';

const firebaseConfig = {
  apiKey: "AIzaSyCVDr-BoFtOIMx5uJ8Am02HReW1R76PO2M",
  authDomain: "dconnect-c8928.firebaseapp.com",
  projectId: "dconnect-c8928",
  storageBucket: "dconnect-c8928.appspot.com",
  messagingSenderId: "354805874788",
  appId: "1:354805874788:web:da5b8d880eb97e6ff09f71",
  measurementId: "G-EZ2NQQ01SV"
};
// const firebaseConfig = {
//   apiKey: "AIzaSyAf7zE2RFooCu2AnwVvuu9B-HPWGQ6sh3Q",
//   authDomain: "my-demo-gcm-dlvn.firebaseapp.com",
//   databaseURL: "https://my-demo-gcm-dlvn.firebaseio.com",
//   projectId: "my-demo-gcm-dlvn",
//   storageBucket: "my-demo-gcm-dlvn.appspot.com",
//   messagingSenderId: "998850959193",
//   appId: "1:998850959193:web:a2e3149d9c05c9a914de48",
//   measurementId: "G-2CGN06YHXH"
//   };
// const app = initializeApp(firebaseConfig);
// const messaging = getMessaging();

// export const requestForToken = () => {
//   return getToken(messaging, { vapidKey: `BCuwf_5w5t_Sn6NronoVVFwMPOgDkqEsE4YbH4VltQecZSHey_I9vYvEuI3L0UM-CA5Tu54AFHxF9MaQZL_9Ua8` })
//     .then((currentToken) => {
//       if (currentToken) {
//         // console.log('current token for client: ', currentToken);
//         setSession(USER_DEVICE_TOKEN, currentToken);
//         return 1;
//         // Perform any other neccessary action with the token
//       } else {
//         // Show permission request UI
//         // console.log('No registration token available. Request permission to generate one.');
//         return 0;
//       }
//     })
//     .catch((err) => {
//       // console.log('An error occurred while retrieving token. ', err);
//       return -1;
//     });
// };

// Handle incoming messages. Called when:
// - a message is received while the app has focus
// - the user clicks on an app notification created by a service worker `messaging.onBackgroundMessage` handler.
// export const onMessageListener = () =>
//   new Promise((resolve) => {    
//     onMessage(messaging, (payload) => {
//       resolve(payload);
//     });
//   });

  
