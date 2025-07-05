update M_EVENTS_APPLICABLE_DETAIL
set ATTENDANCE_TIME = getdate()
/*IF guest.eventType == '3' || guest.eventType == '1'*/
, ID_NUMBER = /*guest.idNumber*/''
, NAME = /*guest.name*/''
, GENDER = /*guest.gender*/''
, TEL = /*guest.tel*/''
/*END*/
where 1=1
and EVENT_ID = /*guest.eventId*/
/*IF guest.agentCode != null && guest.agentCode != ''*/
and AGENT_CODE = /*guest.agentCode*/'1100599'
-- ELSE and ID_NUMBER = /*guest.idNumber*/'197191838'
/*END*/