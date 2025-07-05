select
	A.ID as EVENT_ID
	, A.EVENT_CODE
	, A.EVENT_TITLE
	, A.EVENT_DATE
	, (CASE
			WHEN DATEADD(HOUR, 24, /*fromDate*/) < A.EVENT_DATE THEN '1' --nhắc lần 1
		ELSE '2' END) as TYPE	--nhắc lần 2
from M_EVENTS A
where A.EVENT_DATE between /*fromDate*/ and /*endDate*/
and A.DEL_FLG = 0
and A.EVENT_TYPE in ('2', '3')
order by A.ID