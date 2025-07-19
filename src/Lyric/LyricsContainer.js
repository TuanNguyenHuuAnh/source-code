import React, { useState, useEffect } from 'react';
import Lyrics from '.';
import './LyricStyle.css';
import { isEmpty } from 'lodash';
import {checkZoomToken} from '../util/APIUtils';
import { getDeviceId } from '../util/common';

const LyricsContainer = (props) => {
  const { chatMessages, lyrics, ProposalID, DOB, leaveMeeting } = props;

  const lastMessage = !isEmpty(chatMessages) && chatMessages[chatMessages?.length - 1];
  const [isLyricsVisible, setIsLyricsVisible] = useState(false);
  const [mode, setMode] = useState(500);
  const [script, setScript] = useState(lyrics);

  const convertMode = (string) => {
    switch (string) {
      case 'Slow':
        return 900;
      case 'Fast':
        return 270;
      default:
        return 450;
    }
  };

  const loadScript = (ProposalID, DOB) => {
    let request = {
        jsonDataInput: {
            Action: "GetScriptDC",
            Project: "mAGP",
            DeviceId: getDeviceId(),
            APIToken: "",
            ProposalID: ProposalID,
            DOB: DOB
        }
    }
    checkZoomToken(request)
        .then(response => {
            if (response.Response.Result === 'true' && response.Response.ErrLog === 'GetScriptDC Valid.' && response?.Response?.Scripts?.JsonScripts) {
              let script = JSON.stringify(JSON.parse(response.Response.Scripts.JsonScripts).filter(item => (item.ModeSpeaker === '0' || item.ModeSpeaker === '')));
              setScript(script);
            } 
        }).catch(error => {
        });
}
  useEffect(() => {
    if (!isEmpty(lastMessage) && lastMessage.includes('StartScript')) {
      loadScript(ProposalID, DOB);
      const splitArray = lastMessage.split('#');
      if (splitArray) {
        setMode(convertMode(splitArray[1]));
      }
      setIsLyricsVisible(true);
    } else if (!isEmpty(lastMessage) && (lastMessage.includes('StopScript') || lastMessage.includes('AgentStopScript'))) {
      setIsLyricsVisible(false);
      setMode(6000);
    }
  }, [lastMessage]);

  return (
    <div>
      {isLyricsVisible && (
        <div className="lyrics-container">
          <div className="white-frame">
            <Lyrics isLyricsVisible={isLyricsVisible} lyrics={script} mode={mode} />
          </div>
        </div>
      )}
      <button className="toggle-button" onClick={leaveMeeting}>
        Thoát
      </button>
    </div>
  );
};

export default LyricsContainer;
