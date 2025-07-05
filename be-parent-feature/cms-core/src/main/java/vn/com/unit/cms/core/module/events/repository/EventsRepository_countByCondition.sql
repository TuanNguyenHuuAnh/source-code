select count(1)
from (
select A.ID, A.EVENT_DATE
from M_EVENTS A
LEFT JOIN dbo.M_EVENTS_APPLICABLE_DETAIL B ON A.ID = B.EVENT_ID
where A.DEL_FLG = 0
AND CREATE_BY != 'AutoEvent'
/*IF agentCode != null && agentCode != ''*/
AND  (CAST(B.AGENT_CODE as varchar) = /*agentCode*/'250215' or B.ID_NUMBER = /*agentCode*/'012649727')
/*END*/
union
select ID, EVENT_DATE
from M_EVENTS
where DEL_FLG = 0
/*IF agentCode != null && agentCode != ''*/
AND CREATE_BY = /*agentCode*/'250215'
/*END*/
) C
where 1=1
/*IF eventDate != null*/
and datediff(day, event_date, /*eventDate*/'') = 0
/*END*/