select A.EVENT_TITLE
	, sum(case when B.ATTENDANCE_TIME IS null then 0 else 1 end) ATTENDANCE_QUANTITY
	, count(B.EVENT_ID) QUANTITY
from M_EVENTS A
left join M_EVENTS_APPLICABLE_DETAIL B
on A.ID = B.EVENT_ID
where A.EVENT_CODE = /*eventCode*/'EVE2301.0014'
group by A.EVENT_TITLE