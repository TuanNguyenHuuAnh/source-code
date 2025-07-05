select A.ID
/*IF agentCode != null && agentCode != ''*/
    , (CASE WHEN A.STATUS=1
    	and FORMAT(A.START_DATE, 'ddMMyyyy') = FORMAT(GETDATE(), 'ddMMyyyy')
    	and D.ATTENDANCE_TIME is null THEN 1 ELSE 0 end) ABLE_ATTENDANCE
/*END*/
from M_TRAINING_COURSES A
/*IF agentCode != null && agentCode != ''*/
inner join M_TRAINING_COURSES_DETAIL D
on A.ID = D.COURSE_ID
and D.AGENT_CODE = /*agentCode*/'131597'
/*END*/
where 1=1
and A.COURSE_CODE = /*courseCode*/'HL2503.0001'
and A.STATUS=1
and A.DELETE_FLG = 0