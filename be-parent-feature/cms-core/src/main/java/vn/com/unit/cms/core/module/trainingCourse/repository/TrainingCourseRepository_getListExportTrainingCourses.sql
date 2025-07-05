SELECT ROW_NUMBER() OVER (ORDER BY A.START_DATE DESC, A.ID DESC) as NO,
    A.ID,
    A.COURSE_CODE,
    C.NAME AS COURSE_NAME,
	A.CONTENTS,
	CONCAT(A.CREATED_BY, ' : ', A.CREATED_NAME) as CREATED_NAME,
    A.LOCATION,
    A.START_DATE,
    A.COMPLETED_DATE as END_DATE,
    case when A.STATUS=1 and A.END_DATE < CURRENT_TIMESTAMP then N'Đã quá hạn huấn luyện' else S.NAME end AS STATUS_TEXT,
    B.QUANTITY,
	B.QUANTITY_ATTENDANCE,
	A.APPROVED_DATE,
	case when A.APPROVED_DATE is null then null
		else CONCAT(A.APPROVED_BY, ' : ', A.APPROVED_NAME) end as APPROVED_NAME,
	A.REJECTED_DATE,
	case when A.REJECTED_DATE is null then null
		else CONCAT(A.REJECTED_BY, ' : ', A.REJECTED_NAME) end as REJECTED_NAME,
	A.REJECTED_REASON,
	A.NOTES
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
ORDER BY A.START_DATE DESC, A.ID DESC