select 
     notifyDetal.id                 as id
    ,notifyDetal.NOTIFY_ID          as NOTIFY_ID
    ,notifyDetal.AGENT_CODE         as AGENT_CODE
    ,notifyDetal.IS_READ_ALREADY    as IS_READ_ALREADY
    ,notifyDetal.IS_LIKE 			as IS_LIKE
from M_NOTIFYS_APPLICABLE_DETAIL notifyDetal
where 
    1=1
    and notifyDetal.id=/*id*/'134'
    and notifyDetal.AGENT_CODE = /*agentCode*/'100543'