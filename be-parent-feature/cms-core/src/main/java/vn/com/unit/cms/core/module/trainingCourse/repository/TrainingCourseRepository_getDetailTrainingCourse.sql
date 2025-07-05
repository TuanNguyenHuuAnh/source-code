SELECT
    A.ID,
    A.COURSE_CODE,
    C.NAME AS COURSE_NAME,
    A.COURSE_NAME_CODE AS COURSE_NAME_CODE,
    A.LOCATION,
    A.START_DATE,
    A.END_DATE,
    A.STATUS,
    case when A.STATUS=1 and FORMAT(A.END_DATE, 'ddMMyyyy') < FORMAT(GETDATE(), 'ddMMyyyy') then N'Đã quá hạn huấn luyện' else S.NAME end AS STATUS_TEXT,
    A.OFFICE,
    A.CONTENTS,
    A.NOTES,
    A.QR_CODE,
    A.CREATED_BY,
    A.CREATED_BY + ' - ' + A.CREATED_NAME as CREATED_NAME,
    A.APPROVED_BY,
    A.APPROVED_BY + ' - ' + A.APPROVED_NAME as APPROVED_NAME,
    A.REJECTED_REASON,
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
    (CASE WHEN A.STATUS=1
    	and FORMAT(A.START_DATE, 'ddMMyyyy') = FORMAT(GETDATE(), 'ddMMyyyy')
    	and D.ATTENDANCE_TIME is null THEN 1 ELSE 0 end) ABLE_ATTENDANCE
FROM M_TRAINING_COURSES A
INNER JOIN
    (SELECT COURSE_ID,
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
LEFT JOIN M_TRAINING_COURSES_DETAIL D
ON D.COURSE_ID = A.ID
and (D.AGENT_CODE = /*search.agentCode*/'' or D.ID_NUMBER = /*search.agentCode*/'')
WHERE A.COURSE_CODE = /*search.courseCode*/'HL2503.0001'
AND ISNULL(A.DELETE_FLG, 0) = 0
/*IF search.status != null && search.status != ''*/
AND A.STATUS = /*search.status*/
/*END*/
/*IF search.agentFlg != null && search.agentFlg != ''*/
AND (A.CREATED_BY = /*search.agentCode*/'131597' or D.COURSE_ID is not null)
/*END*/
/*IF search.offices != null && search.offices.size() > 0*/
AND A.OFFICE IN /*search.offices*/()
/*END*/
