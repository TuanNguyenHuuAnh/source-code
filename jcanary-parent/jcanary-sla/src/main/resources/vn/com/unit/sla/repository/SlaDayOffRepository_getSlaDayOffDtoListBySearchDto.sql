SELECT 
	doff.ID						AS ID
	, doff.DAY_OFF				AS DAY_OFF
	, doff.CALENDAR_TYPE_ID		AS CALENDAR_TYPE_ID
	, doff.DAY_OFF_TYPE			AS DAY_OFF_TYPE
	, doff.DESCRIPTION			AS DESCRIPTION	
FROM 
	SLA_DAY_OFF doff
WHERE 
	doff.DELETED_ID = 0
	AND doff.CALENDAR_TYPE_ID = /*search.calendarTypeId*/
	/*IF search.fromDate != null */
	AND /*search.fromDate*/ <= doff.DAY_OFF
	/*END*/
	/*IF search.toDate != null */
	AND /*search.toDate*/ >= doff.DAY_OFF
	/*END*/