select A.ID
	, A.EVENT_TYPE
	, (CASE
			WHEN DATEADD(HOUR, -4, A.EVENT_DATE) > GETDATE() THEN '11' --Hơn 4h nữa mới diễn ra(sắp diễn ra)
			WHEN GETDATE() between DATEADD(HOUR, -4, A.EVENT_DATE) and DATEADD(HOUR, -1, A.EVENT_DATE) THEN '12'	--4h nữa sẽ diễn ra(sắp diễn ra)
			WHEN GETDATE() between DATEADD(HOUR, -1, A.EVENT_DATE) and A.EVENT_DATE THEN '13'	--1h nữa sẽ diễn ra(sắp diễn ra)
			WHEN GETDATE() between A.EVENT_DATE and A.END_DATE THEN '2' --đang diễn ra
		ELSE '3' END) as STATUS	--đã diễn ra
from M_EVENTS A
where 1=1
and A.EVENT_CODE = /*eventCode*/'EVE2303.1898'
and A.DEL_FLG = 0