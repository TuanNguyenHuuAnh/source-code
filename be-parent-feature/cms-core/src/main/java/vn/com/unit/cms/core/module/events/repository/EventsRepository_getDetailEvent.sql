select A.ID
	, A.EVENT_CODE
	, A.EVENT_TITLE
	, A.LINK_NOTIFY
	, A.EVENT_DATE
	, A.END_DATE
	, A.EVENT_LOCATION
	, A.EVENT_TYPE
	, A.GROUP_EVENT_CODE
	, B.NAME as GROUP_EVENT_NAME
	, A.ACTIVITY_EVENT_CODE
	, C.NAME as ACTIVITY_EVENT_NAME
	, A.QR_CODE
	, A.QR_ID
	, A.CONTENTS
	, A.NOTES as NOTE
	, A.APPLICABLE_OBJECT
	, A.TERRITORRY
	, A.AREA
	, A.REGION
	, A.OFFICE
	, A.POSITION
	, A.CREATE_BY
	, ISNULL(D.QUANTITY, 0) as QUANTITY
	, ISNULL(D.ATTENDANCE_QUANTITY, 0) as ATTENDANCE_QUANTITY
	, (case when D.QUANTITY = 0 then 0
		else ROUND(CAST(D.ATTENDANCE_QUANTITY as FLOAT)/CAST(D.QUANTITY as FLOAT), 2)*100 end) ATTENDANCE_RATIO
	, (CASE
			WHEN DATEADD(HOUR, -4, A.EVENT_DATE) > GETDATE() THEN '11' --Hơn 4h nữa mới diễn ra(sắp diễn ra)
			WHEN GETDATE() between DATEADD(HOUR, -4, A.EVENT_DATE) and DATEADD(HOUR, -1, A.EVENT_DATE) THEN '12'	--4h nữa sẽ diễn ra(sắp diễn ra)
			WHEN GETDATE() between DATEADD(HOUR, -1, A.EVENT_DATE) and A.EVENT_DATE THEN '13'	--1h nữa sẽ diễn ra(sắp diễn ra)
			WHEN GETDATE() between A.EVENT_DATE and A.END_DATE THEN '2' --đang diễn ra
		ELSE '3' END) as STATUS	--đã diễn ra
from M_EVENTS A
left join M_EVENTS_MASTERDATA B
on A.GROUP_EVENT_CODE = B.CODE
and B.TYPE = 'GROUP_EVENT'
left join M_EVENTS_MASTERDATA C
on A.ACTIVITY_EVENT_CODE = C.CODE
and C.TYPE = 'ACTIVITY_EVENT'
left join (
	select EVENT_ID
		  , count(*) as QUANTITY
		  , sum (case when ATTENDANCE_TIME is not null then 1 else 0 end) as ATTENDANCE_QUANTITY
  	from M_EVENTS_APPLICABLE_DETAIL
	group by EVENT_ID
) D
on A.ID = D.EVENT_ID
where 1=1
and A.ID = /*eventId*/100
and A.DEL_FLG = 0
/*IF createBy != null && createBy != ''*/
and (A.CREATE_BY = /*createBy*/'158424'
or EXISTS (select 1 from M_EVENTS_APPLICABLE_DETAIL
	where EVENT_ID=A.ID
	and (CAST(AGENT_CODE as varchar) = /*createBy*/'158424' or ID_NUMBER = /*createBy*/'012649727')
	)
)
/*END*/