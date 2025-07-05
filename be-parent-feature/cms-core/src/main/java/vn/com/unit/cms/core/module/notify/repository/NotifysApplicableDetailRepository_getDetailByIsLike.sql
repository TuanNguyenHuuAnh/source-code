select 
	*
from M_NOTIFYS_APPLICABLE_DETAIL notifyDetal
where 
    1=1
    and notifyDetal.AGENT_CODE = /*agentCode*/'100543'
    and notifyDetal.NOTIFY_ID=/*messageId*/'134'
