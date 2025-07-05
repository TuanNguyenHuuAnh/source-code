SELECT  cal.ID                  					AS ID
		,cal.WORKING_DAY							AS WORKING_DAY
		,cal.CALENDAR_TYPE_ID						AS CALENDAR_TYPE_ID
		,cal.START_TIME								AS START_TIME
		,cal.END_TIME								AS END_TIME
		,cal.DESCRIPTION							AS DESCRIPTION
FROM 
	SLA_WORKING_DAY cal
WHERE 
	 cal.WORKING_DAY = /*workDay*/
	AND cal.CALENDAR_TYPE_ID = /*calendarTypeId*/

