SELECT  cal.ID                  					AS ID
		,cal.CALENDAR_DATE							AS CALENDAR_DATE
		,cal.CALENDAR_TYPE_ID						AS CALENDAR_TYPE_ID
		,cal.START_TIME								AS START_TIME
		,cal.END_TIME								AS END_TIME
		,cal.DESCRIPTION							AS DESCRIPTION
FROM 
	SLA_CALENDAR cal
WHERE 
	 cal.CALENDAR_DATE = /*date*/
	AND cal.CALENDAR_TYPE_ID = /*calendarTypeId*/

