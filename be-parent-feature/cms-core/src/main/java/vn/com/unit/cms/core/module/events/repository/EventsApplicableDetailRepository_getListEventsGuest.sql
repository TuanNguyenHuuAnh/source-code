select A.EVENT_ID 
	, A.ID_NUMBER
	, A.NAME
	, A.GENDER
	, A.TEL
	, A.AGENT_CODE
	, A.ATTENDANCE_TIME
	, A.POSITION,
/*IF search.sort != null && search.sort != ''*/
ROW_NUMBER() OVER (ORDER BY /*$search.sort*/) AS no
-- ELSE ROW_NUMBER() OVER (ORDER BY A.ID DESC) AS no
/*END*/
from M_EVENTS_APPLICABLE_DETAIL A
where 1=1
and A.EVENT_ID = /*search.eventId*/'148'
/*IF search.search != null && search.search != ''*/
/*$search.search*/''
/*END*/
/*IF search.sort != null && search.sort != ''*/
ORDER BY /*$search.sort*/
-- ELSE ORDER BY A.ATTENDANCE_TIME DESC, A.ID DESC
/*END*/
/*IF search.page != null && search.pageSize != null*/
offset /*search.offset*/'' ROWS FETCH NEXT  /*search.pageSize*/'' ROWS ONLY
/*END*/