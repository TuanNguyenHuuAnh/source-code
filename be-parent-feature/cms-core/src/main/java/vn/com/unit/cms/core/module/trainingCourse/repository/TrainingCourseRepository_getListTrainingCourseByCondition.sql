SELECT
    A.ID,
    A.COURSE_CODE,
    C.NAME AS COURSE_NAME,
    A.LOCATION,
    A.START_DATE,
    A.END_DATE,
    A.STATUS,
    case when A.STATUS=1 and FORMAT(A.END_DATE, 'ddMMyyyy') < FORMAT(GETDATE(), 'ddMMyyyy') then N'Đã quá hạn huấn luyện' else S.NAME end AS STATUS_TEXT,
    B.QUANTITY,
    --Người tạo
    (CASE WHEN A.CREATED_BY = /*search.agentCode*/ and A.STATUS<=2 THEN 1 ELSE 0 end) ABLE_DELETE,
    (CASE WHEN A.CREATED_BY = /*search.agentCode*/ and A.STATUS=1 THEN 1 ELSE 0 end) ABLE_UPDATE,
    (CASE WHEN A.CREATED_BY = /*search.agentCode*/ and A.STATUS=1 and A.START_DATE < CURRENT_TIMESTAMP
    	and FORMAT(A.END_DATE, 'ddMMyyyy') = FORMAT(GETDATE(), 'ddMMyyyy')
    	and B.QUANTITY_ATTENDANCE > 0 THEN 1 ELSE 0 end) ABLE_END,
    --Người duyệt
    (CASE WHEN A.APPROVED_BY = /*search.agentCode*/ and A.STATUS=2
    	and FORMAT(A.START_DATE, 'MMyyyy') = FORMAT(GETDATE(), 'MMyyyy') THEN 1 ELSE 0 end) ABLE_APPROVE,
    --Khách mời
    /*IF search.attendance != null && search.attendance != ''*/
    (CASE WHEN A.STATUS=1
    	and FORMAT(A.START_DATE, 'ddMMyyyy') = FORMAT(GETDATE(), 'ddMMyyyy')
    	and D.ATTENDANCE_TIME is null THEN 1 ELSE 0 end) ABLE_ATTENDANCE
    -- ELSE 0 AS ABLE_ATTENDANCE
	/*END*/
FROM M_TRAINING_COURSES A
/*IF search.attendance != null && search.attendance != ''*/
INNER JOIN M_TRAINING_COURSES_DETAIL D
ON A.ID = D.COURSE_ID
AND (D.AGENT_CODE = /*search.agentCode*/'' or D.ID_NUMBER = /*search.agentCode*/'')
AND D.ATTENDANCE_TIME /*$search.attendance*/''
/*END*/
INNER JOIN
    (SELECT COURSE_ID, COUNT(1) AS QUANTITY,
     SUM(case when AGENT_CODE is not null and ATTENDANCE_TIME is not null then 1
		when AGENT_CODE is null then 1
		else 0 end) AS QUANTITY_ATTENDANCE
     FROM M_TRAINING_COURSES_DETAIL
     GROUP BY COURSE_ID
    ) B
    ON A.ID = B.COURSE_ID
INNER JOIN M_EVENTS_MASTERDATA C
ON A.COURSE_NAME_CODE = C.CODE AND C.TYPE='TRAINING_ONLINE'
INNER JOIN M_EVENTS_MASTERDATA S
ON A.STATUS = S.CODE AND S.TYPE='TRAINING_STATUS'
WHERE FORMAT(A.START_DATE, 'yyyyMMdd') between /*search.fromDate*/ and /*search.toDate*/
AND ISNULL(A.DELETE_FLG, 0) = 0
/*IF search.status != null && search.status != ''*/
AND A.STATUS = /*search.status*/
/*END*/
/*IF search.agentFlg != null && search.agentFlg != '' && search.attendance == null*/
AND A.CREATED_BY = /*search.agentCode*/'131597'
/*END*/
/*IF search.offices != null && search.offices.size() > 0*/
AND A.OFFICE IN /*search.offices*/()
/*END*/
/*IF search.search != null && search.search != ''*/
/*$search.search*/''
/*END*/
/*IF search.sort != null && search.sort != ''*/
ORDER BY /*$search.sort*/
-- ELSE ORDER BY A.START_DATE DESC, A.ID DESC
/*END*/
/*IF search.page != null && search.pageSize != null*/
offset /*search.offset*/'' ROWS FETCH NEXT  /*search.pageSize*/'' ROWS ONLY
/*END*/