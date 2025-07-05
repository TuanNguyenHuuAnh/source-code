select count(1)
from
	(select ID
		, EVENT_CODE
		, EVENT_TITLE
		, EVENT_LOCATION
		, EVENT_DATE
		, END_DATE
		, (CASE
				WHEN EVENT_DATE > GETDATE() THEN N'Sắp diễn ra'
				WHEN GETDATE() between EVENT_DATE and END_DATE THEN N'Đang diễn ra'
			ELSE N'Đã diễn ra' END) as STATUS
	from M_EVENTS A1
	where CREATE_BY = /*search.createBy*/'158424'
	and EVENT_TYPE in ('2', '3')
	and EVENT_DATE >= DATEADD(MONTH, -6, getDate())
	and DEL_FLG = 0) A
where 1=1
/*IF search.search != null && search.search != ''*/
/*$search.search*/''
/*END*/