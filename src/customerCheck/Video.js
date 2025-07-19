import React, {useCallback, useEffect, useMemo, useState} from "react";
import ReactDOM from "react-dom";
import LyricsContainer from "../Lyric/LyricsContainer";

import "../app/App.css";
// import {ZoomMtg} from "@zoomus/websdk";
import {isEmpty} from "lodash";
import {API_BASE_URL, FE_BASE_URL, SCREENS, ZOOM_MEETING_SDK_KEY} from '../constants';
import {checkZoomToken} from '../util/APIUtils';
import ThanksGeneralPopup from '../components/ThanksGeneralPopup';

let lyrics = [];
let isVideoEnabled = false;

function Video({MeetingID, Passcode, poName, ProposalID, DOB}) {
    const [chatMessages, setChatMessages] = useState([]);
    const [meetingStarted, setMeetingStarted] = useState(false);
    const [showThanks, setShowThanks] = useState(false);
    const [showInterrupt, setShowInterrupt] = useState(false);
    const [msg, setMsg] = useState();

    console.log("MeetingID", MeetingID, Passcode);
    const authEndpoint = API_BASE_URL + "/v1/partner/sign";
    const sdkKey = ZOOM_MEETING_SDK_KEY;
    const meetingNumber = MeetingID;
    const passWord = Passcode;
    const role = 0;
    const userName = poName;
    const userEmail = "";
    const registrantToken = "";
    const zakToken = "";
    const leaveUrl = FE_BASE_URL;
    const zmmtgRoot = document.getElementById("zmmtg-root");

    const updateChatMessage=(message)=> {
        if (!isEmpty(message)) {
            setChatMessages(message);
        }
    }
    const getSignature = useCallback((ZoomMtg) => {
        fetch(authEndpoint, {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify({
                meetingNumber: meetingNumber, role: role,
            }),
        })
            .then((res) => res.json())
            .then((response) => {
                console.log("Zoom API Response:", response);
                if (response.signature) {
                    // const base64Signature = btoa(response.signature);
                    // startMeeting(base64Signature);
                    // const base64Signature = btoa(unescape(encodeURIComponent(response.signature)));
                    // startMeeting(base64Signature);
                    startMeeting(response.signature, ZoomMtg);
                } else {
                    console.error("Error in Zoom API response:", response);
                }
            })
            .catch((error) => {
                console.error(error);
            });
    }, [authEndpoint, meetingNumber, role]);

    const startMeeting = useCallback((signature, ZoomMtg) => {
        try {
            const zoomMeetingSDK = document.getElementById("zmmtg-root");
            console.log('signature', signature);


            if (zoomMeetingSDK && signature) {
                zoomMeetingSDK.style.display = "block";
                ZoomMtg.init({
                    leaveUrl: leaveUrl, disablePreview: false, // Bật xem trước camera
                    isSupportAV: true, success: async (success) => {
                        try {
                            await ZoomMtg.join({
                                signature,
                                sdkKey: sdkKey,
                                meetingNumber: meetingNumber,
                                passWord: passWord,
                                userName: userName,
                                userEmail: userEmail,
                                tk: registrantToken,
                                zak: zakToken,
                                success: async (success) => {
                                    await ZoomMtg.getAttendeeslist({});

                                    const chatElements = document.querySelectorAll('[feature-type="chat"]');

                                    chatElements.forEach((chatElement) => {
                                        const chatButton = chatElement.querySelector(".footer-chat-button");
                                        const footerButton = chatElement.querySelector(".footer-button-base__button");


                                        if (chatButton && footerButton) {
                                            footerButton.addEventListener("click", () => {
                                                setTimeout(() => {
                                                    const wcContainerLeft = document.getElementById("wc-container-left");
                                                    const wcContainerRight = document.getElementById("wc-container-right");
                                                    if (wcContainerRight) {
                                                        wcContainerRight.style.display = "none";
                                                    }

                                                    if (wcContainerLeft) {
                                                        wcContainerLeft.style.width = "100%";
                                                    }
                                                }, 500);
                                            });

                                            footerButton.click();
                                        }
                                    });

                                    setMeetingStarted(true);

                                    // Bật camera mặc định
                                    try {
                                        ZoomMtg.setVideoStatus({
                                            video: true,
                                        });
                                    } catch(e) {
                                        console.log(e);
                                    }

                                    // setIsVideo(true);
                                    isVideoEnabled = true;
                                },
                                error: (error) => {
                                    console.log(error);
                                },
                            });
                        } catch (joinError) {
                            console.error("Error joining Zoom meeting:", joinError);
                        }
                    }, error: (error) => {
                        console.log(error);
                    },
                });
            }
        } catch (error) {
            console.error("Unexpected error:", error);
        }
    }, [setMeetingStarted, leaveUrl, sdkKey, meetingNumber, passWord, userName, userEmail, registrantToken, zakToken,]);

    const loadScript = (ProposalID, DOB) => {
        let device = window.btoa(navigator.userAgent);
        if (device.length > 50) {
            device = device.substring(0, 49);
        }
        let request = {
            jsonDataInput: {
                Action: "GetScriptDC", Project: "mAGP", DeviceId: device, APIToken: "", ProposalID: ProposalID, DOB: DOB
            }
        }
        checkZoomToken(request)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'GetScriptDC Valid.' && response.Response.Scripts) {
                    if (response?.Response?.Scripts?.JsonScripts) {
                        // console.log(response?.Response?.Scripts?.JsonScripts);
                        lyrics = JSON.stringify(JSON.parse(response?.Response?.Scripts?.JsonScripts).filter(item => (item.ModeSpeaker === '0' || item.ModeSpeaker === '')));
                        // lyrics = response.Response.Scripts.JsonScripts;
                    } else {
                        setShowThanks(true);
                        setMsg('<p>Tư vấn chưa có mặt. Cuộc gọi chỉ được thực hiện khi Tư vấn có mặt. Quý khách vui lòng thực hiện lại từ link đặt hẹn sau khi Tư vấn có mặt.</p>');
                    }
                }
            }).catch(error => {
        });
    }

    const wait = (ms) => {
        const start = Date.now();
        let now = start;
        while (now - start < ms) {
            now = Date.now();
        }
    };

    const initZoomSDK = (ZoomMtg) => {
        ZoomMtg.setZoomJSLib(FE_BASE_URL + "/node_modules/meetingsdk/dist/lib", "/av");
        ZoomMtg.preLoadWasm();
        ZoomMtg.prepareWebSDK();
        ZoomMtg.i18n.load("en-US");

        const zmmtgRoot = document.getElementById('zmmtg-root');

        if (zmmtgRoot) {


            const style = document.createElement("style");
            style.innerHTML = `
                     #preview-video-control-button span {
    display: none; /* Ẩn thẻ span */
}
#preview-video-control-button:after {
    content: "Dừng video"; /* Thay đổi nội dung */
    display: inline-block;
    margin-right: 5px; /* Thêm khoảng cách nếu cần */
}
           #preview-audio-control-button span {
    display: none; /* Ẩn thẻ span */
}

#preview-audio-control-button:after {
    content: "Tắt"; /* Thay đổi nội dung */
    display: inline-block;
    margin-right: 5px; /* Thêm khoảng cách nếu cần */
}

.preview-join-button {
    overflow: hidden;
    width: 400px;
    height: 40px; 
    position: relative;
}

.preview-join-button::before {
    content: "Tham gia";
    display: block;
    color: #ffffff;
    background: linear-gradient(180deg, #ea6544, #d12a1e);
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    padding: 12px;
}

            .mini-layout-body-title {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    position: relative;
}

.mini-layout-body-title::after {
    content: "Xem trước video";
    display: inline-block;
    color: #000000;
    font-size: 18px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: white; /* Màu nền tùy chọn, giống với màu nền của trang */
}

        .preview-username {
            display: none !important;
         } 
         .video-avatar__avatar {
            display: none !important;
         }
        .audio-option-menu {
            display: none !important;
        }
        .video-option-menu {
            display: none !important;
        }
        .footer__btns-container {
          display: none !important;
        }
        .footer__leave-btn-container {
          position: relative;
        }
        .footer-button__wrapper {
          position: absolute;
          bottom: 0px;
          right: 100px;
          .send-video-container__btn {
            margin-right: 10rem;
            z-index: 999999999;
          }
          .join-audio-container__btn {
            z-index: 999999999;
          }
        }
      `;
            zmmtgRoot?.appendChild(style);
        }

    };
    // const MemoizedInitZoomSDK = useMemo(() => initZoomSDK, []);

    useEffect(() => {
        if (meetingStarted) {
            
            const sendVideoBtn = document.querySelector(".send-video-container__btn");

            const joinAudio = document.querySelector('.join-audio-by-voip')?.querySelector('button');
            if (joinAudio) {
                const clickEvent = new MouseEvent('click', {
                    bubbles: true, cancelable: true, view: window
                });
                joinAudio.dispatchEvent(clickEvent);
            }

            if (document.getElementById('fpt_ai_livechat_button')) {
                document.getElementById('fpt_ai_livechat_button').style.display = 'none';
            }
            const container = document.createElement('div');

            if (container) {
                ReactDOM.render(<LyricsContainer chatMessages={chatMessages} lyrics={lyrics} ProposalID={ProposalID}
                                                 DOB={DOB} leaveMeeting={() => {
                    import("@zoom/meetingsdk").then((module) => {
                        const ZoomMtg = module.ZoomMtg;
                        ZoomMtg.leaveMeeting({
                            success: function () {
                                console.log('end meeting success');
                            }, error: function (res) {
                                console.log('end meeting error', res);
                            },
                        });
                    });
                }}/>, container)
            }
            zmmtgRoot?.appendChild(container);
            const lastMessage = !isEmpty(chatMessages) && chatMessages[chatMessages?.length - 1];
            console.log("lastMessage", lastMessage);

            if (lastMessage === 'EndCall') {
                wait(1000);
                setShowThanks(true);
                setMsg('<p>Quá trình ghi âm/ghi hình hồ sơ YCBH chưa hoàn tất, Tư vấn sẽ liên hệ đến Quý khách sau. Cảm ơn sự hỗ trợ của Quý khách</p>');
            } else if (lastMessage === 'IsNotMuteVideo#' || lastMessage === 'IsMuteVideo#') {
                console.log("sendVideoBtn", sendVideoBtn);

                // Cập nhật trạng thái video dựa trên thông điệp
                isVideoEnabled = (lastMessage === 'IsMuteVideo#') ? !isVideoEnabled : isVideoEnabled;

                // Thực hiện hành động tương ứng với trạng thái video
                if (sendVideoBtn) {
                    const actionMessage = isVideoEnabled ? "Tắt" : "Bật";
                    console.log(`${actionMessage} video`);
                    sendVideoBtn.click();
                }
            } else if (lastMessage === 'AgentStopScript') {
                wait(1000);
                setShowInterrupt(true);
                setMsg('<p>Quá trình ghi âm/ghi hình bị gián đoạn, Tư vấn sẽ liên hệ Quý khách để thực hiện lại.</p>');
            } else if (lastMessage === 'RecordSaveEndCall') {
                wait(1000);
                setShowThanks(true);
                setMsg('<p>Cảm ơn Quý khách đã thực hiện xong phần ghi âm/ghi hình cùng Tư vấn. Hồ sơ YCBH của Quý khách sẽ được Tư vấn hoàn tất và nộp về Công ty Dai-ichi Life Việt Nam</p>');
            }


            return () => {
                ReactDOM.unmountComponentAtNode(container);
                zmmtgRoot?.removeChild(container);
            };
        }
    }, [meetingStarted, chatMessages, zmmtgRoot]);


    useEffect(() => {
            const startObserver = () => {
                const targetElement = document.getElementById('chat-list-content');
                
                if (targetElement) {
                    const observer = new MutationObserver(observerCallback);
                    observer.observe(targetElement, {childList: true, subtree: true});
                    targetElement.__mutation_observer__ = observer;
                } else {
                    setTimeout(startObserver, 500);
                }
            };
    
            const observerCallback = (mutationsList, observer) => {
                mutationsList.forEach((mutation) => {
    
                    if (mutation.type === 'childList') {
                        const chatListContent = mutation.target;
    
                        const innerScrollContainer = chatListContent.querySelector('.ReactVirtualized__Grid__innerScrollContainer');
    
                        const chatContainers = innerScrollContainer ? innerScrollContainer.getElementsByClassName('new-chat-message__container') : chatListContent.getElementsByClassName('new-chat-message__container');

    
                        const newChatMessages = Array.from(chatContainers).reduce((acc, container) => {
                            
                            const textContent = container.querySelector('.new-chat-message__text-content');
                            if (textContent) {
                                acc.push(textContent.textContent);
                            }
                            return acc;
                        }, []);

                        updateChatMessage(newChatMessages);
                    }
                });
            };
    
            startObserver();
        
        return () => {
            const targetElement = document.getElementById('chat-list-content');
            const observer = targetElement?.__mutation_observer__;

            if (observer) {
                observer.disconnect();
                delete targetElement.__mutation_observer__;
            }
        };

    }, [chatMessages]);

    // console.log("Danh sách các chat-message__text-content:", chatMessages);

    useEffect(() => {
        const loadZoomAndInitializeMeeting = async () => {
            // Dynamically import ZoomMtg
            const module = await import("@zoom/meetingsdk");
            const ZoomMtg = module.ZoomMtg;

            // Attach leaveMeeting to window.onbeforeunload
            window.onbeforeunload = function (event) {
                ZoomMtg.leaveMeeting({
                    success: function () {
                        console.log('end meeting success');
                    }, error: function (res) {
                        console.log('end meeting error', res);
                    },
                });
                return null;
            };

            return () => ZoomMtg.leaveMeeting({});
        };

        // Call the function to load ZoomMtg and initialize the meeting
        loadZoomAndInitializeMeeting();
    }, []);

    useEffect(() => {
        const loadZoomAndInitializeMeeting = async () => {
            // Dynamically import ZoomMtg
            const module = await import("@zoom/meetingsdk");
            const ZoomMtg = module.ZoomMtg;
            if (lyrics.length <= 0) {
                loadScript(ProposalID, DOB);
            }

            initZoomSDK(ZoomMtg);
            getSignature(ZoomMtg);

            return () => {
                ZoomMtg.leaveMeeting({});
                const zoomMeetingSDK = document.getElementById("zmmtg-root");
                if (zoomMeetingSDK) {
                    zoomMeetingSDK.style.display = "none";
                }
                if (document.getElementById('fpt_ai_livechat_button')) {
                    document.getElementById('fpt_ai_livechat_button').style.display = 'block';
                }
            };
        };

        // Call the function to load ZoomMtg and initialize the meeting
        loadZoomAndInitializeMeeting();
    }, []);

    const closeThanks = () => {
        import("@zoom/meetingsdk").then((module) => {
            const ZoomMtg = module.ZoomMtg;
            setShowThanks(false);
            ZoomMtg.leaveMeeting({
                success: () => {
                    console.log('Left the meeting successfully');
                    setChatMessages([]);
                }, error: (error) => {
                    console.error('Failed to leave the meeting', error);
                },
            });
        });
    }

    return (<div className="App">
        {showThanks && <ThanksGeneralPopup closePopup={() => closeThanks()}
                                           msg={msg ? msg : ''}
                                           screen={SCREENS.HOME}/>}
        {showInterrupt && <ThanksGeneralPopup closePopup={() => setShowInterrupt(false)}
                                              msg={msg ? msg : ''}/>}
    </div>);
}

export default Video;
