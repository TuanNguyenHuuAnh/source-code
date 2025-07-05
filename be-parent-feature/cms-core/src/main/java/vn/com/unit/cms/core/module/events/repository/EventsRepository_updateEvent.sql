update M_EVENTS
set EVENT_TITLE = /*event.eventTitle*/''
	, LINK_NOTIFY = /*event.linkNotify*/''
	, EVENT_DATE = /*event.eventDate*/''
	, END_DATE = /*event.endDate*/''
	, EVENT_LOCATION = /*event.eventLocation*/''
	, EVENT_TYPE = /*event.eventType*/''
	, GROUP_EVENT_CODE = /*event.groupEventCode*/''
	, ACTIVITY_EVENT_CODE = /*event.activityEventCode*/''
	, NOTES = /*event.note*/''
	, APPLICABLE_OBJECT = /*event.applicableObject*/''
	, TERRITORRY = /*event.territorry*/''
	, AREA = /*event.area*/''
	, REGION = /*event.region*/''
	, OFFICE = /*event.office*/''
	, POSITION = /*event.position*/''
from M_EVENTS
where 1=1
/*IF eventId != null*/
and ID = /*eventId*/'142'
/*END*/