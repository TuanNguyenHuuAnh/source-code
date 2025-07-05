select
	A.ID
	, A.EVENT_CODE
	, A.EVENT_TITLE
	, A.EVENT_LOCATION
	, A.EVENT_DATE
	, A.END_DATE
	, (CASE WHEN A.EVENT_DATE > GETDATE() and ISNULL(B.ATTENDANCE_QUANTITY, 0) = 0 THEN 1
			ELSE 0 end) ABLE_DELETE
	, A.STATUS
	, ISNULL(B.QUANTITY, 0) as QUANTITY
	, ISNULL(B.ATTENDANCE_QUANTITY, 0) as ATTENDANCE_QUANTITY
	, (case when B.QUANTITY = 0 then 0
		else ROUND(CAST(B.ATTENDANCE_QUANTITY as FLOAT)/CAST(B.QUANTITY as FLOAT), 2)*100 end) ATTENDANCE_RATIO		
	, C.NAME GROUP_EVENT_NAME
	, D.NAME as ACTIVITY_EVENT_NAME
	, A.LINK_NOTIFY
	, A.NOTES
	, A.CREATE_DATE
	, A.UPDATE_DATE,
/*IF search.sort != null && search.sort != ''*/
ROW_NUMBER() OVER (ORDER BY /*$search.sort*/) AS no
-- ELSE ROW_NUMBER() OVER (ORDER BY A.EVENT_DATE DESC, A.ID DESC) AS no
/*END*/
from
	(select *
		, (CASE
				WHEN EVENT_DATE > GETDATE() THEN N'Sắp diễn ra'
				WHEN GETDATE() between EVENT_DATE and END_DATE THEN N'Đang diễn ra'
			ELSE N'Đã diễn ra' END) as STATUS
	from M_EVENTS
	where CREATE_BY = /*search.createBy*/'158424'
	and EVENT_TYPE in ('2', '3')
	and CREATE_DATE >= DATEADD(MONTH, -6, getDate())
	and DEL_FLG = 0) A
	left join (
		select EVENT_ID
		  , count(*) as QUANTITY
		  , sum (case when ATTENDANCE_TIME is not null then 1 else 0 end) as ATTENDANCE_QUANTITY
  		from M_EVENTS_APPLICABLE_DETAIL
		group by EVENT_ID
	) B
	on A.ID = B.EVENT_ID
	left join M_EVENTS_MASTERDATA C
	on A.GROUP_EVENT_CODE = C.CODE
	and C.TYPE = 'GROUP_EVENT'
	left join M_EVENTS_MASTERDATA D
	on A.ACTIVITY_EVENT_CODE = D.CODE
	and D.TYPE = 'ACTIVITY_EVENT'
where 1=1
/*IF search.search != null && search.search != ''*/
/*$search.search*/''
/*END*/
/*IF search.sort != null && search.sort != ''*/
ORDER BY /*$search.sort*/
-- ELSE ORDER BY A.EVENT_DATE DESC, A.ID DESC
/*END*/
/*IF search.page != null && search.pageSize != null*/
offset /*search.offset*/'' ROWS FETCH NEXT  /*search.pageSize*/'' ROWS ONLY
/*END*/	