select A.*, A.notes as note, A.EVENT_DATE AS EVENT_DATE_TIME,
/*IF search.sort != null && search.sort != ''*/
ROW_NUMBER() OVER (ORDER BY /*$search.sort*/) AS no
-- ELSE ROW_NUMBER() OVER (ORDER BY A.ID DESC) AS no
/*END*/
from (
select A1.*
from M_EVENTS A1
LEFT JOIN dbo.M_EVENTS_APPLICABLE_DETAIL B ON A1.ID = B.EVENT_ID
where A1.DEL_FLG = 0
AND CREATE_BY != 'AutoEvent'
/*IF agentCode != null && agentCode != ''*/
AND  (CAST(B.AGENT_CODE as varchar) = /*agentCode*/'250215' or B.ID_NUMBER = /*agentCode*/'012649727')
/*END*/
union
select *
from M_EVENTS
where DEL_FLG = 0
/*IF agentCode != null && agentCode != ''*/
AND CREATE_BY = /*agentCode*/'250215'
/*END*/
) A
where 1=1
/*IF eventDate != null*/
and datediff(day, event_date, /*eventDate*/'') = 0
/*END*/
/*IF search.sort != null && search.sort != ''*/
ORDER BY /*$search.sort*/
-- ELSE ORDER BY A.ID DESC
/*END*/
/*IF search.page != null && search.pageSize != null*/
offset /*page*/'' ROWS FETCH NEXT  /*pageSize*/'' ROWS ONLY
/*END*/