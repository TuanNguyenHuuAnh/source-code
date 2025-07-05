SELECT
    NICK_NAME	as nick_name
FROM M_CLIENT_NICKNAME 
WHERE
	DELETE_BY is null
    /*IF agentCode != null && agentCode != ''*/
        AND AGENT_CODE = /*agentCode*/''
    /*END*/
    /*IF customerNo != null && customerNo != ''*/
         AND CLIENT_ID = /*customerNo*/''
    /*END*/
