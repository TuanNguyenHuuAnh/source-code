SELECT *
FROM M_CLIENT_NICKNAME
WHERE
/*BEGIN*/
    /*IF dto.agentCode != null && dto.agentCode != ''*/
        AGENT_CODE = /*dto.agentCode*/''
    /*END*/
    /*IF dto.customerNo != null && dto.customerNo != ''*/
         AND CLIENT_ID = /*dto.customerNo*/''
    /*END*/
/*END*/