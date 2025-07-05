select A.EVENT_DATE
from M_EVENTS A
LEFT JOIN dbo.M_EVENTS_APPLICABLE_DETAIL B ON A.ID = B.EVENT_ID
where 1=1
/*IF agentCode != null && agentCode != ''*/
AND  B.AGENT_CODE = /*agentCode*/'250215'
/*END*/
/*IF eventDate != null*/
and datediff(month, A.event_date, /*eventDate*/'') = 0
/*END*/