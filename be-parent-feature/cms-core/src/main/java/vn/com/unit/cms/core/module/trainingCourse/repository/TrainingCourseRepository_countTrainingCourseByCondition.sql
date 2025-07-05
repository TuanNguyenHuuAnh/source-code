SELECT COUNT(1)
FROM M_TRAINING_COURSES A
INNER JOIN M_EVENTS_MASTERDATA C
ON A.COURSE_NAME_CODE = C.CODE AND C.TYPE='TRAINING_ONLINE'
/*IF search.attendance != null && search.attendance != ''*/
INNER JOIN M_TRAINING_COURSES_DETAIL D
ON A.ID = D.COURSE_ID
AND (D.AGENT_CODE = /*search.agentCode*/'' or D.ID_NUMBER = /*search.agentCode*/'')
AND D.ATTENDANCE_TIME /*$search.attendance*/''
/*END*/
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