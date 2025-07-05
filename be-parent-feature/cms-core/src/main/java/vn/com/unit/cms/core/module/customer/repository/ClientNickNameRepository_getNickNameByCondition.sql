SELECT NICK_NAME 
FROM M_CLIENT_NICKNAME
WHERE
/*BEGIN*/
    /*IF agentCode != null && agentCode != ''*/
        AGENT_CODE = /*agentCode*/''
    /*END*/
    /*IF customerNo != null && customerNo != ''*/
         AND CLIENT_ID = /*customerNo*/''
    /*END*/
/*END*/