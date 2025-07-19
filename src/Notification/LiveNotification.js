import React, {useState, useEffect} from 'react'
import toast, { Toaster } from 'react-hot-toast';
import { requestForToken, onMessageListener } from './firebase';
import {showMessage} from '../util/common';
import { EXPIRED_MESSAGE } from '../constants';

let locked = false;
const Notification = () => {
  const [notification, setNotification] = useState({title: '', body: ''});
  const notify = () =>  toast(<ToastDisplay/>); 
//   const notify = () =>  toast(<ToastWarning/>,
//   {
//     style: {
//       borderRadius: '10px',
//       background: '#ffffff',
//       color: '#000000',
//     },
//   }
// );

  // const warning = () =>  toast.error(<ToastWarning/>); 
  function ToastDisplay() {
    return (
      <div>
        <p>{notification?.title}</p><br/>
        <p>{notification?.body}</p>
      </div>
    );
  };
  // function ToastWarning() {
  //   return (
  //     <div>
  //       <p>Quý khách đã chặn thông báo đến website Dai-ichi connect.</p>
  //       <p>Để nhận được thông báo vui lòng vào mục cài cặt của trình duyệt để đặt lại quyền</p>
  //   </div>
  //   );
  // };

  useEffect(() => {
    if (notification?.title ){
     notify();
    }
  }, [notification])

  // requestForToken().then(result=>{
  //   if (result < 0 && !locked) {
  //     // warning();
  //     locked = true;
  //   } 
  // }).catch(err=>{
  //   // console.log(err);
  // })


  // onMessageListener()
  //   .then((payload) => {
  //     setNotification({title: payload?.notification?.title, body: payload?.notification?.body});     
  //   })
  //   .catch((err) => {
  //     // console.log('failed: ', err)
  //   } );

  return (
    <Toaster position="top-right" reverseOrder={false}/>
  )
}

export default Notification
